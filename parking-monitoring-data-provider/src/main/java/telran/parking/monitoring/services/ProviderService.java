package telran.parking.monitoring.services;


import telran.parking.monitoring.dto.VisitDto;

public interface ProviderService {
    VisitDto addVisit(VisitDto visitDto);
    VisitDto getVisit(int id);
}
