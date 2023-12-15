package kg.academia.academia_2_0.repositories.userRepos;

import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.users.TempUser;
import kg.academia.academia_2_0.model.entities.users.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TempUserRepository extends JpaRepository<TempUser, Long> {
    Optional<TempUser> findByUserData(UserData userData);

    List<TempUser> findByBranch(Branch branch);
}