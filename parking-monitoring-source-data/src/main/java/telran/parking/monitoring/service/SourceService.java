package telran.parking.monitoring.service;


import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import telran.parking.monitoring.dto.Sensor;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

@Service
@Log4j2
public class SourceService {

    String[] carNumbers = {"45 543 22", "32 234 11", "12 532 11", "98 323 11", "23 441 12",
            "44 443 11", "54 123 12", "33 753 33", "98 456 32", "32 512 88",""};

    String lastCarNumber = "";

    @Bean
    Supplier<Sensor> sensorSupplier(){
        return this::getSensorData;
    }

    private Sensor getSensorData() {
        int id = 1;
        String value = getRandomNumber(1, 100) > 90 ? carNumbers[getRandomNumber(0, 9)] : lastCarNumber;
        lastCarNumber = value;
        Sensor sensor = new Sensor(id, value);
        log.debug("send data {}", sensor);
        return sensor;

    }

    private int getRandomNumber(int from, int to) {
        return ThreadLocalRandom.current().nextInt(from, to);
    }
}
