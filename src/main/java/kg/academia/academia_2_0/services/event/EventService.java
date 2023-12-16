package kg.academia.academia_2_0.services.event;

import kg.academia.academia_2_0.model.creations.EventCreate;
import kg.academia.academia_2_0.model.entities.Event;

public interface EventService {
    void createEvent(EventCreate eventCreate);

    Event findEventById(Long lessonId);

}
