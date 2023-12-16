package kg.academia.academia_2_0.services.chat;

import kg.academia.academia_2_0.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatStorageImpl implements ChatStorage{
    private final ChatRepository chatRepository;

    @Autowired
    public ChatStorageImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }
}
