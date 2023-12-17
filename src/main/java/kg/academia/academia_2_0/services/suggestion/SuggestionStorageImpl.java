package kg.academia.academia_2_0.services.suggestion;

import kg.academia.academia_2_0.repositories.SuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuggestionStorageImpl implements SuggestionStorage {
    private final SuggestionRepository suggestionRepository;

    @Autowired
    public SuggestionStorageImpl(SuggestionRepository suggestionRepository) {
        this.suggestionRepository = suggestionRepository;
    }
}
