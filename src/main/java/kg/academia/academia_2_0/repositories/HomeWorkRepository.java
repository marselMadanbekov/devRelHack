package kg.academia.academia_2_0.repositories;

import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.homeWork.MentalExercise;
import kg.academia.academia_2_0.model.entities.homeWork.MentalHomeWork;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface HomeWorkRepository extends JpaRepository<MentalHomeWork, Long> {
    List<MentalHomeWork> findByGroupAndCreateDate(Group group, Date date);

    Page<MentalHomeWork> findByGroup(Group group, Pageable pageable);

    List<MentalHomeWork> findByPupil(Pupil pupil);

    Optional<MentalHomeWork> findByMentalExercises(MentalExercise exercise);
}
