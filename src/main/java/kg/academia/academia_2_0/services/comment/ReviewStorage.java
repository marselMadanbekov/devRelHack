package kg.academia.academia_2_0.services.comment;

import kg.academia.academia_2_0.model.entities.Event;
import kg.academia.academia_2_0.model.entities.Review;

import java.util.List;

public interface ReviewStorage {
    void save(Review review);

    List<Review> findByEvent(Event event);
}
