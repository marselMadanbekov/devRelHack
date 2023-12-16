package kg.academia.academia_2_0.services.comment;

import kg.academia.academia_2_0.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentStorageImpl implements CommentStorage {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentStorageImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
