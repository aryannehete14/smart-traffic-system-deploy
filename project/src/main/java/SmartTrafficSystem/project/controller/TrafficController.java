package SmartTrafficSystem.project.controller;

import SmartTrafficSystem.project.model.Violation;
import SmartTrafficSystem.project.service.TrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/violations")
@CrossOrigin(origins = "*") // Critical for cloud deployment
public class TrafficController {

    @Autowired
    private TrafficService service;

    // GET: Fetch all records for the Audit Log table
    @GetMapping
    public List<Violation> getHistory() {
        return service.getAll();
    }

    // POST: Receive new vehicle data from the web form
    @PostMapping
    public void addViolation(@RequestBody Violation violation) {
        service.processAndSave(violation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViolation(
            @PathVariable Long id,
            @RequestHeader(value = "X-Admin-Key", required = false) String adminKey) {

        if (adminKey == null || !adminKey.equals("ADMIN123")) {
            return ResponseEntity.status(403).build();
        }

        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}