package telran.parking.monitoring.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import telran.parking.monitoring.dto.VisitDto;

import java.util.function.Consumer;

@Service
@Log4j2
public class RegistrationService {

    @Bean
    Consumer<VisitDto> registrationConsumer(){
        return this::registrationProcessing;
    }

    private void registrationProcessing(VisitDto visitDto) {
        log.debug("registrationProcessing {}", visitDto);
        //TODO
    }
}
