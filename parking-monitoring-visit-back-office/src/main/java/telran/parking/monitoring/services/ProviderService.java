package telran.parking.monitoring.services;


import telran.parking.monitoring.dto.VisitDto;
import telran.parking.monitoring.entities.CountVisitByPeriod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ProviderService {

    List<VisitDto> getVisits(LocalDateTime from, LocalDateTime to);

    Map<LocalDate, Long> getStatisticVisitByPeriod(LocalDateTime from, LocalDateTime to);
}
