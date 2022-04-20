package telran.parking.monitoring.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import telran.parking.monitoring.dto.MessageNotificator;

import java.util.function.Consumer;

@Service
@Log4j2
public class ServiceNotifier {

    @Bean
    Consumer<MessageNotificator> driverNotifier(){
        return this::driverNotifierProcessing;
    }

    private void driverNotifierProcessing(MessageNotificator messageNotificator) {
        log.debug("receive {}", messageNotificator);
        //TODO to email
    }

}
