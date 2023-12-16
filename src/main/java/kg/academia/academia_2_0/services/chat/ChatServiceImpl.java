package kg.academia.academia_2_0.services.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{
    private final ChatStorage chatStorage;

    @Autowired
    public ChatServiceImpl(ChatStorage chatStorage) {
        this.chatStorage = chatStorage;
    }
}
