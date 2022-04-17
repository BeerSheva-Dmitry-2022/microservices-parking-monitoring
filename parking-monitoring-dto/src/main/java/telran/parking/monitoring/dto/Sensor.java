package telran.parking.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@AllArgsConstructor
public class Sensor {
    long id;
    String value;
    LocalDateTime timestamp;
}
