package telran.parking.monitoring.entities;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "drivers")
@Getter
public class Driver {

    @Id
    @Column(name = "car_number")
    String carNumber;
    @Column(name = "id")
    int id;
    @Column(name = "name")
    String name;
    @Column(name = "email")
    String email;
    @Column(name = "year_birth")
    int yearBirth;

}
