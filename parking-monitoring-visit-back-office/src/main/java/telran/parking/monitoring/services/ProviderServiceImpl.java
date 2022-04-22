package telran.parking.monitoring.services;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import telran.parking.monitoring.dto.VisitDto;
import telran.parking.monitoring.entities.*;
import telran.parking.monitoring.repository.*;

import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<VisitDto> getVisits(LocalDateTime from, LocalDateTime to) {
       List<Visit> res =  visitRepo.getVisits(from, to);
       return  res.stream().map(p -> VisitDto.builder()
                .id(p.getId())
                .carNumber(p.getDriver().getCarNumber())
                .idSensor(p.getSensor().getId())
                .startParking(p.getStartParking())
                .endParking(p.getEndParking())
                .fine(p.isFine())
                .build()).toList();
    }

    @Override
    public Map<LocalDate, Long>  getStatisticVisitByPeriod(LocalDateTime from, LocalDateTime to) {
        List<Visit> response =  visitRepo.getVisits(from, to);
        Map<LocalDate, Long> res =  response.stream().map(p->p.getStartParking().toLocalDate())
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        return res;
    }
}
