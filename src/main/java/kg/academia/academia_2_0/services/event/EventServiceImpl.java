package kg.academia.academia_2_0.services.event;

import kg.academia.academia_2_0.model.creations.EventCreate;
import kg.academia.academia_2_0.model.entities.Event;
import kg.academia.academia_2_0.model.entities.Review;
import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.services.comment.ReviewStorage;
import kg.academia.academia_2_0.services.notification.NotificationService;
import kg.academia.academia_2_0.services.photo.PhotoService;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.user.UserService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventStorage eventStorage;
    private final ContextService contextService;
    private final UserStorage userStorage;
    private final UserService userService;
    private final PhotoService photoService;

    private final ReviewStorage reviewStorage;
    private final NotificationService notificationService;

    @Autowired
    public EventServiceImpl(EventStorage eventStorage, ContextService contextService, UserStorage userStorage, UserService userService, PhotoService photoService, ReviewStorage reviewStorage, NotificationService notificationService) {
        this.eventStorage = eventStorage;
        this.contextService = contextService;
        this.userStorage = userStorage;
        this.userService = userService;
        this.photoService = photoService;
        this.reviewStorage = reviewStorage;
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

        List<Employee> targetEmployees = userStorage.findAllBySkills(eventCreate.getTargetSkills());

        for (Employee employee : targetEmployees){
            if(employee.getSocialMedias().get("EMAIL") != null){
                userService.sendDistributionsByUser(employee.getId(), "Создано новое мероприятие под названием : " +eventCreate.getName() + " которое может иметь отношение к вам", Collections.singletonList("EMAIL"));
            }
            if(employee.getSocialMedias().get("TELEGRAM") != null){
                userService.sendDistributionsByUser(employee.getId(), "Создано новое мероприятие под названием : " +eventCreate.getName() + " которое может иметь отношение к вам", Collections.singletonList("TELEGRAM"));
            }
        }
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

    @Override
    public void createComment(Long eventId, String message, Integer grade) {
        Event event = eventStorage.getById(eventId);
        Employee employee = contextService.getCurrentEmployee();
        Review review = new Review();
        review.setEvent(event);
        review.setMessage(message);
        review.setGrade(grade);
        review.setAuthor(employee);
        reviewStorage.save(review);
    }

    @Override
    public void changeAttendanceStatusOfUser(Long eventId, Long userId) {
        Event event = eventStorage.getById(eventId);
        Employee employee = userStorage.getEmployeeById(userId);

        event.changeStatusOfParticipant(employee);

        eventStorage.save(event);
    }
}
