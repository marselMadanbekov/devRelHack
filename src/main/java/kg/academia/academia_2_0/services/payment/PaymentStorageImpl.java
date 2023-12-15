package kg.academia.academia_2_0.services.payment;

import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Payment;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentStorageImpl implements PaymentStorage{
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentStorageImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> findByPupil(Pupil pupil) {
        return paymentRepository.findByPupil(pupil);
    }

    @Override
    public Optional<Integer> getPaymentsSumBetweenDatesByBranch(Branch branch, Date startDate, Date endDate) {
        return paymentRepository.getPaymentsSumBetweenDatesByBranch(branch, startDate, endDate);
    }
}
