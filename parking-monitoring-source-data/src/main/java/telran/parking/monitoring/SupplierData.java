package telran.parking.monitoring;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import telran.parking.monitoring.dto.Sensor;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

@Component
public class SupplierData {

    String[] carNumbers = {"45 543 22", "32 234 11", "12 532 11", "98 323 11", "23 441 12",
                           "44 443 11", "54 123 12", "33 753 33", "98 456 32", "32 512 88"};

    @Bean
    Supplier<Sensor> SensorSupplier(){
        return this::getSensorData;
    }

    private Sensor getSensorData() {
        int id = getRandomNumber(1, 10);
        String value = getRandomNumber(1, 100) > 50 ? carNumbers[getRandomNumber(0, 9)] : "";
        return new Sensor(id, value, LocalDateTime.now());

    }

    private int getRandomNumber(int from, int to) {
        return ThreadLocalRandom.current().nextInt(from, to);
    }




}
