package telran.parking.monitoring.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import telran.parking.monitoring.dto.Sensor;

import java.time.Instant;

@RedisHash
@Getter
@Setter
@ToString
public class SensorEntity {

    @Id
    int id;
    String carNumber;
    long timestampStart;
    long timestampCurrent;
    long timestampEnd;
    int timeCheckFine;
    boolean isFine;


    public SensorEntity() {
    }

    public SensorEntity(int id) {
        this.id = id;
    }

    public static SensorEntity getSensorEntity(Sensor sensor) {
        SensorEntity sensorEntity = new SensorEntity();
        sensorEntity.id = sensor.getId();
        sensorEntity.carNumber = sensor.getCarNumber();
        sensorEntity.timestampStart = sensor.getTimestamp();
        sensorEntity.timestampCurrent = sensorEntity.timestampStart;
        sensorEntity.isFine = false;
        return sensorEntity;
    }


}
