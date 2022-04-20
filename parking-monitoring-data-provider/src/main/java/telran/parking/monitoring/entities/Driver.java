package telran.parking.monitoring.entities;

import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @Column(name = "car_number")
    String carNumber;
    @Column(name = "name")
    String name;
    @Column(name = "year_birth")
    int yearBirth;

}
