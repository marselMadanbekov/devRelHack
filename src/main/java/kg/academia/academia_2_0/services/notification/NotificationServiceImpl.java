package kg.academia.academia_2_0.services.notification;

import kg.academia.academia_2_0.model.entities.Notification;
import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationStorage notificationStorage;
    private final UserStorage userStorage;
    private final ContextService contextService;

    @Autowired
    public NotificationServiceImpl(NotificationStorage notificationStorage, UserStorage userStorage, ContextService contextService) {
        this.notificationStorage = notificationStorage;
        this.userStorage = userStorage;
        this.contextService = contextService;
    }


    @Override
    public void createNewUserNotificationToEmployee(Long id) {

    }

    @Override
    public boolean isCurrentUserHaveNonViewedNotifications() {
        Employee employee = contextService.getCurrentEmployee();
        return notificationStorage.getNonViewedNotificationsByUserData(employee).size() > 0;
    }

    @Override
    public void notificationViewed(Long notificationId) {
        Notification notification = notificationStorage.getNotificationById(notificationId);
        notification.setViewed(true);
        notificationStorage.save(notification);
    }


}
