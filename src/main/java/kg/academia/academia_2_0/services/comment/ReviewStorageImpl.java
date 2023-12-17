package kg.academia.academia_2_0.services.comment;

import kg.academia.academia_2_0.model.entities.Event;
import kg.academia.academia_2_0.model.entities.Review;
import kg.academia.academia_2_0.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewStorageImpl implements ReviewStorage {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewStorageImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public List<Review> findByEvent(Event event) {
        return reviewRepository.findByEvent(event);
    }
}
