package kg.academia.academia_2_0.repositories;

import kg.academia.academia_2_0.model.entities.Event;
import kg.academia.academia_2_0.model.entities.users.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStartDateTime(LocalDateTime date);
    List<Event> findByEndDateTime(LocalDateTime date);

    List<Event> findByOrganizer(Employee organizer);
}
