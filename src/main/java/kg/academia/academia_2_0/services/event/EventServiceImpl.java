package kg.academia.academia_2_0.services.event;

import kg.academia.academia_2_0.model.creations.EventCreate;
import kg.academia.academia_2_0.model.entities.Event;
import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.services.notification.NotificationService;
import kg.academia.academia_2_0.services.photo.PhotoService;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EventServiceImpl implements EventService {

    private final EventStorage eventStorage;
    private final ContextService contextService;
    private final UserStorage userStorage;
    private final PhotoService photoService;
    private final NotificationService notificationService;

    @Autowired
    public EventServiceImpl(EventStorage eventStorage, ContextService contextService, UserStorage userStorage, PhotoService photoService, NotificationService notificationService) {
        this.eventStorage = eventStorage;
        this.contextService = contextService;
        this.userStorage = userStorage;
        this.photoService = photoService;
        this.notificationService = notificationService;
    }

    @Override
    public void createEvent(EventCreate eventCreate) {
        String firstPhoto;
        try {
            firstPhoto = photoService.saveProductPhoto(eventCreate.getPhoto());

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении фото : " + e.getMessage());
        }catch (NullPointerException e){
            throw new RuntimeException("Пустое фото. Фото обязательное поле!");
        }
        Employee organizer = contextService.getCurrentEmployee();
        eventStorage.save(
                Event.builder()
                        .startDateTime(eventCreate.getStartDateTime())
                        .endDateTime(eventCreate.getEndDateTime())
                        .description(eventCreate.getDescription())
                        .name(eventCreate.getName())
                        .photo(firstPhoto)
                        .targetSkills(eventCreate.getTargetSkills())
                        .organizer(organizer)
                        .build());
    }


    @Override
    public Event findEventById(Long eventId) {
        return eventStorage.getById(eventId);
    }

    @Override
    public Page<Event> eventsByPage(Integer page) {
        return eventStorage.findEventsByPage(page);
    }

    @Override
    public void registerToEventCurrentUser(Long eventId) {
        Employee employee = contextService.getCurrentEmployee();
        Event event = eventStorage.getById(eventId);
        event.addParticipants(employee);
        eventStorage.save(event);
    }
}
