package kg.academia.academia_2_0.services.event;

import kg.academia.academia_2_0.model.creations.EventCreate;
import kg.academia.academia_2_0.model.entities.Event;
import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.services.notification.NotificationService;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    private final EventStorage eventStorage;
    private final ContextService contextService;
    private final UserStorage userStorage;
    private final NotificationService notificationService;

    @Autowired
    public EventServiceImpl(EventStorage eventStorage, ContextService contextService, UserStorage userStorage, NotificationService notificationService) {
        this.eventStorage = eventStorage;
        this.contextService = contextService;
        this.userStorage = userStorage;
        this.notificationService = notificationService;
    }

    @Override
    public void createEvent(EventCreate eventCreate) {

        Employee organizer = contextService.getCurrentEmployee();
        eventStorage.save(
                Event.builder()
                        .startDateTime(eventCreate.getStartDateTime())
                        .endDateTime(eventCreate.getEndDateTime())
                        .description(eventCreate.getDescription())
                        .name(eventCreate.getName())
                        .organizer(organizer)
                        .build());
    }


    @Override
    public Event findEventById(Long eventId) {
        return eventStorage.getById(eventId);
    }

    @Override
    public Page<Event> eventsByPage(Integer page) {
        return null;
    }
}
