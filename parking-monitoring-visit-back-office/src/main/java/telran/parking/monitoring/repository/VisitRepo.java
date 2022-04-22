package telran.parking.monitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import telran.parking.monitoring.entities.CountVisitByPeriod;
import telran.parking.monitoring.entities.Visit;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepo extends JpaRepository<Visit, Integer> {

    @Query(value = "select * from visits " +
            "where start_parking between :from and :to", nativeQuery = true)
    List<Visit> getVisits(LocalDateTime from, LocalDateTime to);

}
