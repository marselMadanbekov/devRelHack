package kg.academia.academia_2_0.services.event;

import kg.academia.academia_2_0.model.entities.Event;
import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventStorageImpl implements EventStorage {

    private final EventRepository eventRepository;

    @Autowired
    public EventStorageImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void save(Event event) {
        eventRepository.save(event);
    }

    @Override
    public Event getById(Long id) {
        return eventRepository.findById(id).orElseThrow();
        //:TODO add correct exception
    }


    @Override
    public List<Event> findByStartDateTime(LocalDateTime date) {
        return eventRepository.findByStartDateTime(date);
    }

    @Override
    public List<Event> findByOrganizer(Employee organizer) {
        return eventRepository.findByOrganizer(organizer);
    }

    @Override
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Page<Event> findEventsByPage(Integer page) {
        Pageable pageable = PageRequest.of(page,15);
        return eventRepository.findAll(pageable);
    }

}
