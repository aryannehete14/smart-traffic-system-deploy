package SmartTrafficSystem.project.service;

import SmartTrafficSystem.project.model.Violation;
import SmartTrafficSystem.project.repository.ViolationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrafficService {

    @Autowired
    private ViolationRepository repository;

    /**
     * Processes traffic data based on speed and emergency status,
     * then saves the record to the database.
     */
    public void processAndSave(Violation v) {
        if (v.isEmergency()) {
            v.setFine(0);
            v.setStatus("NO VIOLATION (Emergency)");
        } else if (v.getSpeed() > 80) {
            int fine = (v.getSpeed() > 120) ? 5000 : 2000;
            v.setFine(fine);
            v.setStatus("VIOLATION DETECTED");
        } else {
            v.setFine(0);
            v.setStatus("NO VIOLATION (Within Limits)");
        }
        repository.save(v);
    }

    /**
     * Retrieves all traffic records.
     */
    public List<Violation> getAll() {
        return repository.findAll();
    }

    /**
     * Deletes a record by ID.
     */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}