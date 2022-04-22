package telran.parking.monitoring.services;

import org.springframework.stereotype.Service;
import telran.parking.monitoring.dto.VisitDto;
import telran.parking.monitoring.entities.*;
import telran.parking.monitoring.repository.*;

import java.time.*;

@Service
public class ProviderServiceImpl implements ProviderService{

    DriverRepo driverRepo;
    SensorRepo sensorRepo;
    VisitRepo visitRepo;

    public ProviderServiceImpl(DriverRepo driverRepo, SensorRepo sensorRepo, VisitRepo visitRepo) {
        this.driverRepo = driverRepo;
        this.sensorRepo = sensorRepo;
        this.visitRepo = visitRepo;
    }

    @Override
    public VisitDto addVisit(VisitDto visitDto) {
        Sensor sensor = sensorRepo.findById(visitDto.getIdSensor()).orElse(null);
        if(sensor == null){
            throw new IllegalArgumentException(String
                    .format("sensor with id %d not found",  visitDto.getIdSensor()));
        };

        Driver driver = driverRepo.findById(visitDto.getCarNumber()).orElse(null);
        if(driver == null){
            throw new IllegalArgumentException(String
                    .format("driver with id %s not found",  visitDto.getCarNumber()));
        };

        Visit visit = Visit.builder()
                .driver(driver)
                .sensor(sensor)
                .startParking(visitDto.getStartParking())
                .endParking(visitDto.getEndParking())
                .fine(visitDto.isFine())
                .build();
        Visit res = visitRepo.save(visit);
        visitDto.setId(res.getId());
        return visitDto;
    }

}
