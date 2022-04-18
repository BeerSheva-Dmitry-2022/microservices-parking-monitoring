package telran.parking.monitoring.dto;

import lombok.*;
import org.apache.logging.log4j.CloseableThreadContext;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@NoArgsConstructor
@Getter
public class Sensor {
    long id;
    String value;
    long timestamp;

    public Sensor(long id, String value) {
        this.id = id;
        this.value = value;
        timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", timestamp=" + LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())  +
                '}';
    }
}
