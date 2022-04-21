package telran.parking.monitoring.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import telran.parking.monitoring.dto.MessageNotificator;
import telran.parking.monitoring.dto.VisitDto;
import telran.parking.monitoring.dto.Sensor;
import telran.parking.monitoring.entity.SensorEntity;
import telran.parking.monitoring.repo.SensorRepository;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;


@Service
@Log4j2
public class AnalyzerService {

    @Value("${app.parking.period.notify:5}")
    private int periodNotify;
    @Value("${app.parking.interval.check.fine:10}")
    private int intervalCheckFine;
    @Value("${app.parking.message.welcome:Welcome to the parking lot. Don't forget to pay for parking}")
    private String msg_welcome;
    @Value("${app.parking.message.bye:Thanks for parking.}")
    private String msg_bye;


    StreamBridge streamBridge;
    SensorRepository sensorRepository;
    CheckFineService checkFineService;


    public AnalyzerService(StreamBridge streamBridge, SensorRepository sensorRepository, CheckFineService checkFineService) {
        this.streamBridge = streamBridge;
        this.sensorRepository = sensorRepository;
        this.checkFineService = checkFineService;
    }

    @Bean
    Consumer<Sensor> parkingConsumer() {
        return this::dataProcessing;
    }

    private void dataProcessing(Sensor sensor) {
        Duration duration;
        log.debug("receive sensor {}", sensor);
        SensorEntity sensorEntity = sensorRepository.findById(sensor.getId()).orElse(null);

        if (sensorEntity == null) {
            sensorEntity = new SensorEntity(sensor.getId());
        }

        if (isNewParking(sensor, sensorEntity)) {
            sensorEntity = getNewSensorEntity(sensor, sensorEntity);
        }

        checkPayment(sensorEntity);

        sensorEntity.setTimestampCurrent(sensor.getTimestamp());
        sensorRepository.save(sensorEntity);


    }

    private SensorEntity getNewSensorEntity(Sensor sensor, SensorEntity sensorEntity) {
        if (sensorEntity.getCarNumber() != null && !sensorEntity.getCarNumber().equals("")) {
            //Если прошлая сессия была у водителя, регистрируем его в бд
            sensorEntity.setTimestampEnd(sensor.getTimestamp());
            registrationVisit(sensorEntity);
            //Отправляем прощание старому водителю
            notifyDriver(String.format("%s Time parking: %d", msg_bye,
                    getPeriodDateTime(sensorEntity)), sensorEntity.getId(), sensorEntity.getCarNumber());
        }
        //Создаем новую сущность
        sensorEntity = SensorEntity.getSensorEntity(sensor);


        if (!sensor.getCarNumber().equals("")) {
            //Если парковку заняла новая машина, приветствуем водителя
            notifyDriver(String.format("%s within %d sec Time current: %s", msg_welcome, periodNotify,
                    getDateTime(sensorEntity)), sensor.getId(), sensor.getCarNumber());
        }
        return sensorEntity;
    }

    private void registrationVisit(SensorEntity sensorEntity) {
        VisitDto visitDto = VisitDto.builder().idSensor(sensorEntity.getId())
                .startParking(sensorEntity.getTimestampStart())
                .endParking(sensorEntity.getTimestampEnd())
                .carNumber(sensorEntity.getCarNumber())
                .fine(sensorEntity.isFine()).build();
        streamBridge.send("registration-visit-out-0", visitDto);
    }

    private void checkPayment(SensorEntity sensorEntity) {
        if (!sensorEntity.isFine() && ChronoUnit.SECONDS.between(Instant.ofEpochSecond(sensorEntity.getTimestampStart()),
                Instant.ofEpochSecond(sensorEntity.getTimestampCurrent())) >= periodNotify + sensorEntity.getTimeCheckFine()) {
            log.debug("send to check fine topic {}", sensorEntity);
            if (!checkFineService.checkFine(sensorEntity.getCarNumber())) {
                log.debug("fine = true");
                sensorEntity.setFine(true);
                //TODO send to topic fine
            } else {
                log.debug("fine = false");
                sensorEntity.setFine(false);
                sensorEntity.setTimeCheckFine(intervalCheckFine + sensorEntity.getTimeCheckFine());
            }

        }
    }

    private long getPeriodDateTime(SensorEntity sensorEntity) {
        long start = sensorEntity.getTimestampStart();
        long end = sensorEntity.getTimestampEnd();
        return end - start;
    }

    private LocalDateTime getDateTime(SensorEntity sensorEntity) {

        return LocalDateTime.ofInstant(Instant.ofEpochSecond(sensorEntity.getTimestampCurrent()), ZoneId.systemDefault());
    }

    private void notifyDriver(String message, int id, String carNumber) {
        MessageNotificator messageNotificator = new MessageNotificator(id, carNumber, message);
        streamBridge.send("driver-notifier-out-0", messageNotificator);

    }

    private boolean isNewParking(Sensor sensor, SensorEntity sensorEntity) {
        return !sensor.getCarNumber().equals(sensorEntity.getCarNumber());
    }
}

