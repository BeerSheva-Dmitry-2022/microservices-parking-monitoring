package telran.parking.monitoring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class CheckFineService {

    @Value("${app.parking.fine.paid.probability:85}")
    private int probability;

    boolean checkFine(String carNumber){
        //TODO
        return probability > ThreadLocalRandom.current().nextInt(1,100);
    }

}
