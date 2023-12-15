package kg.academia.academia_2_0.repositories;

import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Payment;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByPupil(Pupil pupil);

    @Query("SELECT SUM(payment.amount) " +
            "FROM Payment payment " +
            "JOIN payment.pupil pupil " +
            "WHERE pupil.branch = :branch " +
            "AND payment.createDate >= :startDate " +
            "AND payment.createDate <= :endDate ")
    Optional<Integer> getPaymentsSumBetweenDatesByBranch(Branch branch, Date startDate, Date endDate);
}
