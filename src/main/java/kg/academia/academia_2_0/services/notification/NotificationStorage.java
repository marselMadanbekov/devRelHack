package kg.academia.academia_2_0.services.notification;

import kg.academia.academia_2_0.model.entities.Notification;
import kg.academia.academia_2_0.model.entities.users.Employee;

import java.util.List;

public interface NotificationStorage {
    Notification save(Notification notification);
    Notification getNotificationById(Long id);
    void deleteById(Long id);

    List<Notification> getNonViewedNotificationsByUserData(Employee employee);
}
