package kg.academia.academia_2_0.services.notification;

import kg.academia.academia_2_0.model.creations.NotificationCreate;
import kg.academia.academia_2_0.model.entities.Notification;
import kg.academia.academia_2_0.model.entities.users.Admin;
import kg.academia.academia_2_0.model.entities.users.Owner;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.entities.users.UserData;
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
    public void createNotification(NotificationCreate notificationCreate) {
        notificationStorage.save(
                Notification.builder()
                        .message(notificationCreate.getMessage())
                        .targetUser(userStorage.getUserDataById(notificationCreate.getUserDataId()))
                        .build()
        );
    }

    @Override
    public void createLowBalanceNotification(Long pupilId) {
        Pupil pupil = userStorage.getPupil(pupilId);
        String title = "Низкий баланс";
        String message = "На вашем балансе " + pupil.getBalance() + pupil.getBranch().getCurrency() +
                ". Пополните баланс или аккаунт будет заблокирован до следующего пополнения. " +
                "Для пополнения баланса обратитесь к администрации вашего филиала!";
        notificationStorage.save(Notification.builder()
                .targetUser(pupil.getUserData())
                .title(title)
                .message(message)
                .viewed(false)
                .build());
    }

    @Override
    public void createNewUserNotificationToOwner(Long ownerId) {
        Owner owner = userStorage.getOwner(ownerId);
        String title = "Новый запрос";
        String message = "Поступил новый запрос на регистрацию. Перейдите в раздел Запросы чтобы его обработать";
        notificationStorage.save(Notification.builder()
                .targetUser(owner.getUserData())
                .title(title)
                .message(message)
                .viewed(false)
                .build());
    }

    @Override
    public void createNewUserNotificationToAdmin(Long adminId) {
        Admin admin = userStorage.getAdmin(adminId);
        String title = "Новый запрос";
        String message = "Поступил новый запрос на регистрацию. Перейдите в раздел Запросы чтобы его обработать";
        notificationStorage.save(Notification.builder()
                .targetUser(admin.getUserData())
                .title(title)
                .message(message)
                .viewed(false)
                .build());
    }

    @Override
    public boolean isCurrentUserHaveNonViewedNotifications() {
        UserData userData = contextService.getCurrentUsersData();
        return notificationStorage.getNonViewedNotificationsByUserData(userData).size() > 0;
    }

    @Override
    public void notificationViewed(Long notificationId) {
        Notification notification = notificationStorage.getNotificationById(notificationId);
        notification.setViewed(true);
        notificationStorage.save(notification);
    }


}
