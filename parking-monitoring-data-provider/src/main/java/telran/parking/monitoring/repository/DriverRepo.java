package telran.parking.monitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.parking.monitoring.entities.Driver;

public interface DriverRepo extends JpaRepository<Driver, String> {
}
