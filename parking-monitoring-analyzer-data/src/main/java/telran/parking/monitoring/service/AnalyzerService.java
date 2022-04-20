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
import java.util.function.Consumer;


@Service
@Log4j2
public class AnalyzerService {

    @Value("${app.parking.period.notify:5}")
    private long periodNotify;
    @Value("${app.parking.message.welcome:Welcome to the parking lot. Don't forget to pay for parking}")
    private String msg_welcome;
    @Value("${app.parking.message.welcome:Thanks for parking.}")
    private String msg_bye;


    StreamBridge streamBridge;
    SensorRepository sensorRepository;



    public AnalyzerService(StreamBridge streamBridge, SensorRepository sensorRepository) {
        this.streamBridge = streamBridge;
        this.sensorRepository = sensorRepository;
    }

    @Bean
    Consumer<Sensor> parkingConsumer(){
        return this::dataProcessing;
    }

    private void dataProcessing(Sensor sensor) {
        Duration duration;
        log.debug("receive sensor {}", sensor);
        SensorEntity sensorEntity = sensorRepository.findById(sensor.getId()).orElse(null);

        if(sensorEntity==null){
            sensorEntity = new SensorEntity(sensor.getId());
        }

        if(isNewParking(sensor, sensorEntity)){
            if(sensorEntity.getCarNumber()!=null && !sensorEntity.getCarNumber().equals("")){
                notifyDriver(String.format("%s Time parking: %d", msg_bye,
                        getPeriodDateTime(sensorEntity)), sensorEntity.getId(), sensorEntity.getCarNumber());
            }
            sensorEntity = SensorEntity.getSensorEntity(sensor);

            if(!sensor.getCarNumber().equals("")){
                VisitDto visitDto = VisitDto.getVisit(sensorEntity.getId(), sensorEntity.getCarNumber(), sensorEntity.getTimestampStart());
                streamBridge.send("registration-visit-out-0", visitDto);
                notifyDriver(String.format("%s within %d sec Time current: %s", msg_welcome, periodNotify,
                        getDateTime(sensorEntity)), sensor.getId(), sensor.getCarNumber());
            }
        }
        checkPayment(sensorEntity);

        sensorEntity.setTimestampCurrent(Instant.now().getEpochSecond());
        sensorRepository.save(sensorEntity);



    }

    private void checkPayment(SensorEntity sensorEntity) {
        if(!sensorEntity.isCheckFine() && Instant.ofEpochSecond(sensorEntity.getTimestampCurrent()).getEpochSecond() >= periodNotify){
            //TODO
            sensorEntity.setCheckFine(true);
            log.debug("send to check fine topic {}", sensorEntity);
            VisitDto details = new VisitDto();
            details.setIdSensor(sensorEntity.getId());
            details.setCarNumber(sensorEntity.getCarNumber());
            details.setStartParking(sensorEntity.getTimestampStart());
            streamBridge.send("check-fine-out-0", details);
        }
    }

    private long getPeriodDateTime(SensorEntity sensorEntity) {
        long start = sensorEntity.getTimestampStart();
        long end = Instant.now().getEpochSecond();
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

