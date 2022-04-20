package telran.parking.monitoring.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    int id;
    @Column(name = "address")
    String address;

}
