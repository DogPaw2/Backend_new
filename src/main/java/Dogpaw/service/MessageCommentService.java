package Dogpaw.service;


import Dogpaw.domain.Message;
import Dogpaw.domain.MessageComment;
import Dogpaw.repository.MessageRepository;
import Dogpaw.repository.MessageCommentRepository;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageCommentService {
    @NonNull
    private final MessageRepository messageRepository;
    @NonNull
    private final MessageCommentRepository commentRepository;

    public Long saveComment (MessageComment comment) throws ArgumentNullException, InvalidArgumentException {
        if(comment == null){
            throw new ArgumentNullException("Comment can't be null");
        }
        if(comment.getText().isEmpty()){
            throw new InvalidArgumentException("Comment text is null");
        }
        comment.getMessage().getComments().add(comment);
        MessageComment save = commentRepository.save(comment);

        return save.getId();
    }

    public void deleteByCommentId(Long id) throws NotFoundException{
        commentRepository.deleteById(id);
    }

    public MessageComment findOne(Long id) throws NotFoundException{
        MessageComment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment with id : " + id + "is not valid"));
        return comment;
    }


    // exception

    public static class CommentNotFoundException extends NotFoundException {
        public CommentNotFoundException(String msg) {
            super(msg);
        }
    }

    public static class ArgumentNullException extends Throwable {
        public ArgumentNullException(String s) {
        }
    }

    public static class InvalidArgumentException extends Throwable {
        public InvalidArgumentException(String s) {
        }
    }
}


