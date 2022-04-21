package telran.parking.monitoring.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class VisitDto {
    int id;
    int idSensor;
    String carNumber;
    long startParking;
    long endParking;
    boolean fine;

}
