package telran.parking.monitoring.controller;

import telran.parking.monitoring.services.ProviderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import telran.parking.monitoring.dto.VisitDto;


@RestController
@Log4j2
@RequestMapping("/parking")
public class Controller {

    ProviderService provider;

    public Controller(ProviderService provider) {
        this.provider = provider;
    }

    @PostMapping("visits")
    VisitDto getVisit(@RequestBody VisitDto visitDto){
        log.debug("add visit " + visitDto);
        return provider.getVisit(visitDto);
    }
}
