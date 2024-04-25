package com.example.demo2.comment;

import com.example.demo2.DataNotFoundException;
import com.example.demo2.question.Question;
import com.example.demo2.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment create (Question question, SiteUser author, String content) {
        Comment comment = new Comment();
        comment.setQuestion(question);
        comment.setAuthor(author);
        comment.setCreateDate(LocalDateTime.now());
        comment.setContent(content);
        return this.commentRepository.save(comment);
    }
    public Comment getComment (Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        }
        else {
            throw new DataNotFoundException("comment not found");
        }
    }
    public Comment modify (Comment comment, String content) {
        comment.setModifyDate(LocalDateTime.now());
        comment.setContent(content);
        return this.commentRepository.save(comment);
    }
    public void delete (Comment comment) {
        this.commentRepository.delete(comment);
    }
}
