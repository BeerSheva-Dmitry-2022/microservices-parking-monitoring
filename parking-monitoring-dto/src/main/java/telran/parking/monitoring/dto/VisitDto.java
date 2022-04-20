package telran.parking.monitoring.dto;

import lombok.*;

@Data
@AllArgsConstructor @NoArgsConstructor
public class VisitDto {
    int id;
    int idSensor;
    String carNumber;
    long startParking;
    long endParking;

    static public VisitDto getVisit(int idSensor, String carNumber, long startParking){
        VisitDto visitDto = new VisitDto();
        visitDto.setIdSensor(idSensor);
        visitDto.setCarNumber(carNumber);
        visitDto.setStartParking(startParking);
        return visitDto;
    }
}
