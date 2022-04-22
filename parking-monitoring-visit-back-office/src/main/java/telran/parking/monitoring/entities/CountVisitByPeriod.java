package telran.parking.monitoring.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface CountVisitByPeriod {
    LocalDate getDateStart();
    int getCount();
}
