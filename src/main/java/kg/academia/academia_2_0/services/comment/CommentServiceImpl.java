package kg.academia.academia_2_0.services.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentStorage commentStorage;

    @Autowired
    public CommentServiceImpl(CommentStorage commentStorage) {
        this.commentStorage = commentStorage;
    }
}
