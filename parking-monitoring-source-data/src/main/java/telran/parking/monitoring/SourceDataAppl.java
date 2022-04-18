package telran.parking.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import telran.parking.monitoring.dto.Sensor;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import lombok.extern.log4j.Log4j2;


@SpringBootApplication
@Log4j2
public class SourceDataAppl {

    public static void main(String[] args) {

        SpringApplication.run(SourceDataAppl.class, args);

    }




}

