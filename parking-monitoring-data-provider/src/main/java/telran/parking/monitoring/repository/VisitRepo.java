package telran.parking.monitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.parking.monitoring.entities.Visit;

public interface VisitRepo extends JpaRepository<Visit, Integer> {
}
