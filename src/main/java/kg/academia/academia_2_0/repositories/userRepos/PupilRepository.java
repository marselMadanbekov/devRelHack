package kg.academia.academia_2_0.repositories.userRepos;

import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.entities.users.Teacher;
import kg.academia.academia_2_0.model.entities.users.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PupilRepository extends JpaRepository<Pupil, Long> {
    Optional<Pupil> findByUserData(UserData userData);

    List<Pupil> findByBranch(Branch branch);

    Optional<Integer> countByUserDataCreateDateBetweenAndBranch(Date startDate, Date endDate, Branch branch);
    @Query("SELECT COUNT(pupil) FROM Pupil pupil " +
            "WHERE pupil.branch = :branch " +
            "AND (pupil.userData.createDate >= :startDate) " +
            "AND (pupil.userData.createDate <= :endDate)")
    Optional<Integer> getNewPupilsCountBetweenDatesByBranch(@Param("startDate") Date startDate,
                                                            @Param("endDate") Date endDate,
                                                            @Param("branch") Branch branch);

    @Query("SELECT pupil FROM Pupil pupil " +
            "WHERE pupil.branch = :branch " +
            "AND (pupil.userData.createDate >= :startDate) " +
            "AND (pupil.userData.createDate < :endDate)")
    List<Pupil> getNewPupilsCountBetweenDatesByBranch1(@Param("startDate") Date startDate,
                                                            @Param("endDate") Date endDate,
                                                            @Param("branch") Branch branch);
}
