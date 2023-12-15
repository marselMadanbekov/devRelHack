package kg.academia.academia_2_0.repositories.userRepos;

import kg.academia.academia_2_0.model.entities.users.SuperAdmin;
import kg.academia.academia_2_0.model.entities.users.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {
    Optional<SuperAdmin> findByUserData(UserData userData);
}
