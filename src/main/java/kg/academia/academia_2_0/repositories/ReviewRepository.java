package kg.academia.academia_2_0.repositories;

import kg.academia.academia_2_0.model.entities.Event;
import kg.academia.academia_2_0.model.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByEvent(Event event);
}
