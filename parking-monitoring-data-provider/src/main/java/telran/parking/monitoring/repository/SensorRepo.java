package telran.parking.monitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.parking.monitoring.entities.Sensor;

public interface SensorRepo extends JpaRepository<Sensor, Integer> {
}
