package kg.academia.academia_2_0.repositories;

import kg.academia.academia_2_0.model.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {
}
