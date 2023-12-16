package kg.academia.academia_2_0.repositories;

import kg.academia.academia_2_0.model.entities.ConfirmationCode;
import kg.academia.academia_2_0.model.entities.users.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode,Long> {
    Optional<ConfirmationCode> findByEmployee(Employee employee);
}
