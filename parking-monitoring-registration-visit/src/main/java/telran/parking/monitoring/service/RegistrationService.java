package telran.parking.monitoring.service;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import telran.parking.monitoring.api.ApiConstants;
import telran.parking.monitoring.dto.VisitDto;


import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.Collections;
import java.util.function.Consumer;

@Service
@Log4j2
public class RegistrationService {

    @Value("${app.data.provider.service.host:http://localhost:8080}")
    String dataProviderServiceHost;


    RestTemplate restTemplate = new RestTemplate();

    @Bean
    Consumer<VisitDto> registrationConsumer(){
        return this::registrationProcessing;
    }

    private void registrationProcessing(VisitDto visitDto) {
        log.debug("registrationProcessing {}", visitDto);
        HttpEntity<VisitDto> request = new HttpEntity<>(visitDto);
        VisitDto res = restTemplate.postForObject(dataProviderServiceHost+ApiConstants.PARKING_VISIT_ADD_URL, request, VisitDto.class);
        log.debug("succeseful send to db " + res);
    }
}
