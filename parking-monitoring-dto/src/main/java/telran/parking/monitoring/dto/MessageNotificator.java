package telran.parking.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor
@Data
public class MessageNotificator {
    int id;
    String catNumber;
    String message;
}
