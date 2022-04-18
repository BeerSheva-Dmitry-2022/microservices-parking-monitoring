package telran.parking.monitoring.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import telran.parking.monitoring.dto.Sensor;

import java.util.function.Consumer;


@Service
@Log4j2
public class AnalyzerService {
    @Autowired
    StreamBridge streamBridge;

    @Bean
    Consumer<Sensor> parkingConsumer(){
        return this::dataProcessing;
    }

    private void dataProcessing(Sensor sensor) {
        log.debug("receive sensor {}", sensor);
    }
}

