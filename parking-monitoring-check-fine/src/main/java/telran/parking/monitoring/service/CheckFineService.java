package telran.parking.monitoring.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import telran.parking.monitoring.dto.VisitDto;

import java.util.function.Consumer;

@Service
@Log4j2
public class CheckFineService {


    @Bean
    Consumer<VisitDto> checkConsumer(){
        return this::checkProcessing;
    }

    private void checkProcessing(VisitDto details) {
        log.debug("CheckFineService recive {}", details);
        //TODO
    }
}
