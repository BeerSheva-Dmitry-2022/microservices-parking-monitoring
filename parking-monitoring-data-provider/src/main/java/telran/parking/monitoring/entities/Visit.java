package telran.parking.monitoring.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Visit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "sensor_id")
    Sensor sensor;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "car_number")
    Driver driver;
    @Column(name = "start_parking")
    LocalDateTime startParking;
    @Column(name = "end_parking")
    LocalDateTime endParking;
    @Column(name = "fine")
    boolean fine;

}
