package kg.academia.academia_2_0.services.payment;


import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Payment;
import kg.academia.academia_2_0.model.entities.users.Pupil;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface PaymentStorage {
    Payment save(Payment payment);
    List<Payment> findByPupil(Pupil pupil);

    Optional<Integer> getPaymentsSumBetweenDatesByBranch(Branch branch, Date valueOf, Date valueOf1);

}
