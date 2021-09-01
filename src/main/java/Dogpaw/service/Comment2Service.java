package Dogpaw.service;


import Dogpaw.domain.Comment;
import Dogpaw.domain.Comment2;
import Dogpaw.repository.ChatRepository;
import Dogpaw.repository.Comment2Repository;
import Dogpaw.repository.CommentRepository;
import Dogpaw.repository.MessageRepository;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class Comment2Service {
    @NonNull
    private final MessageRepository messageRepository;
    @NonNull
    private final Comment2Repository comment2Repository;

    public Long saveComment2 (Comment2 comment2) throws ArgumentNullException, InvalidArgumentException {
        if(comment2 == null){
            throw new ArgumentNullException("comment2 can't be null");
        }
        if(comment2.getText().isEmpty()){
            throw new InvalidArgumentException("comment2 text is null");
        }
        comment2.getMessage().getComments2().add(comment2);
        Comment2 save = comment2Repository.save(comment2);


        return save.getId();
    }

    public void deleteByComment2Id(Long id) throws NotFoundException{
        comment2Repository.deleteById(id);
    }

    public Comment2 findOne(Long id) throws NotFoundException{
        Comment2 comment2 = comment2Repository.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment2 with id : " + id + "is not valid"));
        return comment2;
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


