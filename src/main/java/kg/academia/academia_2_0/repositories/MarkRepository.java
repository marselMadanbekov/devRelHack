package kg.academia.academia_2_0.repositories;

import kg.academia.academia_2_0.model.entities.Mark;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.enums.MarkType;
import kg.academia.academia_2_0.model.utilities.MarkDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    @Query("SELECT NEW kg.academia.academia_2_0.model.utilities.MarkDTO(SUM(m.earnedPoints), SUM(m.totalPoints - m.earnedPoints), m.date)" +
            "FROM Mark m WHERE m.date >= :startDate AND m.date <= :endDate " +
            "AND m.type = :markType " +
            "AND m.pupil = :pupil " +
            "AND m.subject = :subject " +
            "GROUP BY m.date")
    List<MarkDTO> getByDateRangeAndMarkTypePupilAndSubject(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("pupil") Pupil pupil,
            @Param("subject") Subject subject,
            @Param("markType") MarkType markType
    );

    Optional<Mark> findLastBySubjectAndPupilAndTopicAndType(Subject subject, Pupil currentPupil, String topic, MarkType homeWork);
}
