package kg.academia.academia_2_0.services.payment;

import kg.academia.academia_2_0.model.entities.Payment;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    private final PaymentStorage paymentStorage;
    private final UserStorage userStorage;

    @Autowired
    public PaymentServiceImpl(PaymentStorage paymentStorage, UserStorage userStorage) {
        this.paymentStorage = paymentStorage;
        this.userStorage = userStorage;
    }

    @Override
    public void createPayment(Long pupilId, Integer amount) {
        Pupil pupil = userStorage.getPupil(pupilId);
        paymentStorage.save(
                Payment.builder()
                        .pupil(pupil)
                        .amount(amount)
                        .currency(pupil.getBranch().getCurrency())
                        .build()
        );
        pupil.balanceUp(amount);
        userStorage.savePupil(pupil);
    }
}
