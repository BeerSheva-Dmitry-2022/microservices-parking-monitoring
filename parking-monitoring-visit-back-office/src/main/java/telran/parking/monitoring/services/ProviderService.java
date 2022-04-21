package telran.parking.monitoring.services;


import telran.parking.monitoring.dto.VisitDto;

public interface ProviderService {

    VisitDto getVisit(VisitDto visitDto);

}
