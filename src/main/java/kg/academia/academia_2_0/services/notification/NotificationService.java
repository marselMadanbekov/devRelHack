package kg.academia.academia_2_0.services.notification;

import kg.academia.academia_2_0.model.creations.NotificationCreate;

public interface NotificationService {
    void createNotification(NotificationCreate notificationCreate);

    void createLowBalanceNotification(Long id);

    void createNewUserNotificationToOwner(Long id);

    void createNewUserNotificationToAdmin(Long id);

    boolean isCurrentUserHaveNonViewedNotifications();

    void notificationViewed(Long notificationId);
}
