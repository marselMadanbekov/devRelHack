package kg.academia.academia_2_0.services.event;

import kg.academia.academia_2_0.model.entities.Event;
import kg.academia.academia_2_0.model.entities.users.Employee;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface EventStorage {
    void save(Event event);
    Event getById(Long id);



    List<Event> findByStartDateTime(LocalDateTime date);

    List<Event> findByOrganizer(Employee organizer);

    void deleteById(Long id);

    Page<Event> findEventsByPage(Integer page);

    List<Event> findTop5EventsByRating();
}
