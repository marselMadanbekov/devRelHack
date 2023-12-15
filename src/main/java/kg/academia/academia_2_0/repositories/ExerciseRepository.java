package kg.academia.academia_2_0.repositories;

import kg.academia.academia_2_0.model.entities.homeWork.MentalExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<MentalExercise, Long> {
}
