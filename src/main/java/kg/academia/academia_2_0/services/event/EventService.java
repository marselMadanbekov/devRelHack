package kg.academia.academia_2_0.services.event;

import kg.academia.academia_2_0.model.creations.EventCreate;
import kg.academia.academia_2_0.model.entities.Event;
import kg.academia.academia_2_0.model.utilities.ChartTuple;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface EventService {
    void createEvent(EventCreate eventCreate);

    Event findEventById(Long eventId);

    Page<Event> eventsByPage(Integer page);

    void registerToEventCurrentUser(Long eventId);

    void createComment(Long eventId, String message, Integer grade);

    void changeAttendanceStatusOfUser(Long eventId, Long userId);

    List<ChartTuple> findTopEventsToChart();
}
