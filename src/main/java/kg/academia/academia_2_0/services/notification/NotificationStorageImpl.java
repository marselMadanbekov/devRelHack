package kg.academia.academia_2_0.services.notification;

import kg.academia.academia_2_0.model.entities.Notification;
import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationStorageImpl implements NotificationStorage{
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationStorageImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElseThrow();
        //:TODO add correct exception;
    }

    @Override
    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public List<Notification> getNonViewedNotificationsByUserData(Employee employee) {
        return notificationRepository.findByTargetUserAndViewed(employee,false);
    }
}
