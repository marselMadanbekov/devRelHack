package kg.academia.academia_2_0.services.suggestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuggestionServiceImpl implements SuggestionService {
    private final SuggestionStorage suggestionStorage;

    @Autowired
    public SuggestionServiceImpl(SuggestionStorage suggestionStorage) {
        this.suggestionStorage = suggestionStorage;
    }
}
