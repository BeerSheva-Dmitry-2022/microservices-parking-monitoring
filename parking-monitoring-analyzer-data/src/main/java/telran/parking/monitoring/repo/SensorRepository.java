package telran.parking.monitoring.repo;

import org.springframework.data.repository.CrudRepository;
import telran.parking.monitoring.entity.SensorEntity;

public interface SensorRepository extends CrudRepository<SensorEntity, Integer> {
}
