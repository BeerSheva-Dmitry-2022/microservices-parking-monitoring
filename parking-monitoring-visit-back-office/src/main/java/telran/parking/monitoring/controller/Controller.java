package telran.parking.monitoring.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import telran.parking.monitoring.entities.CountVisitByPeriod;
import telran.parking.monitoring.services.ProviderService;
import lombok.extern.log4j.Log4j2;
import telran.parking.monitoring.dto.VisitDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@RestController
@Log4j2
@RequestMapping("/parking")
public class Controller {

    ProviderService provider;

    public Controller(ProviderService provider) {
        this.provider = provider;
    }

    @GetMapping("visits")
    List<VisitDto> getVisit(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to){
        log.debug("get visits from {} to {}", from, to);
        return provider.getVisits(from, to);
    }

    @GetMapping("/visits/statistic")
    Map<LocalDate, Long> getStatisticVisitByPeriod(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to){
        log.debug("get statistic from {} to {}", from, to);
        return provider.getStatisticVisitByPeriod(from, to);
    }
}
