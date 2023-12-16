package kg.academia.academia_2_0.repositories;

import kg.academia.academia_2_0.model.entities.Notification;
import kg.academia.academia_2_0.model.entities.users.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByTargetUserAndViewed(Employee employee, boolean b);
}
