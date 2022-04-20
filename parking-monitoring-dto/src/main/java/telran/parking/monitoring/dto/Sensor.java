package telran.parking.monitoring.dto;

import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@NoArgsConstructor
@Getter
public class Sensor {
    int id;
    String carNumber;
    long timestamp;

    public Sensor(int id, String carNumber) {
        this.id = id;
        this.carNumber = carNumber;
        timestamp = Instant.now().getEpochSecond();
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", car number='" + carNumber + '\'' +
                ", timestamp=" + LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault())  +
                '}';
    }
}
