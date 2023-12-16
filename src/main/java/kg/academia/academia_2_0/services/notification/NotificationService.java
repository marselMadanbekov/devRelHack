package kg.academia.academia_2_0.services.notification;

public interface NotificationService {
    void createNewUserNotificationToEmployee(Long id);

    boolean isCurrentUserHaveNonViewedNotifications();

    void notificationViewed(Long notificationId);
}
