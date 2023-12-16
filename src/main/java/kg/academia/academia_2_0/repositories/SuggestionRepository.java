package kg.academia.academia_2_0.repositories;

import kg.academia.academia_2_0.model.entities.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}
