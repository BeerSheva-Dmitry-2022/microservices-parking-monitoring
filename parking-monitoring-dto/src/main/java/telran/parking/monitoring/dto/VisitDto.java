package telran.parking.monitoring.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class VisitDto {
    int id;
    int idSensor;
    String carNumber;
    LocalDateTime startParking;
    LocalDateTime endParking;
    boolean fine;

}
