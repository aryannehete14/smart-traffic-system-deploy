package SmartTrafficSystem.project.repository;

import SmartTrafficSystem.project.model.Violation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViolationRepository extends JpaRepository<Violation, Long> {
    // JpaRepository provides save(), findAll(), findById(), etc.
}