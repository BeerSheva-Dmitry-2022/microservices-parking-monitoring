package telran.parking.monitoring.controller;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import telran.parking.monitoring.dto.VisitDto;
import telran.parking.monitoring.services.ProviderService;

@RestController
@Log4j2
@RequestMapping("/parking")
public class Controller {

    ProviderService provider;

    public Controller(ProviderService provider) {
        this.provider = provider;
    }

    @PostMapping("visits")
    VisitDto addVisit(@RequestBody VisitDto visitDto){
        log.debug("add visit " + visitDto);
        return provider.addVisit(visitDto);
    }
}
