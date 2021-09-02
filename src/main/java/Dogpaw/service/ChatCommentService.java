package Dogpaw.service;


import Dogpaw.domain.ChatComment;
import Dogpaw.repository.ChatRepository;
import Dogpaw.repository.ChatCommentRepository;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatCommentService {
    @NonNull
    private final ChatRepository chatRepository;
    @NonNull
    private final ChatCommentRepository commentRepository;

    public Long saveComment (ChatComment comment) throws ArgumentNullException, InvalidArgumentException {
        if(comment == null){
            throw new ArgumentNullException("Chat can't be null");
        }
        if(comment.getText().isEmpty()){
            throw new InvalidArgumentException("Chatting text is null");
        }
        comment.getChat().getComments().add(comment);
        ChatComment save = commentRepository.save(comment);

        return save.getId();
    }

    public void deleteByCommentId(Long id) throws NotFoundException{
        commentRepository.deleteById(id);
    }

    public ChatComment findOne(Long id) throws NotFoundException{
        ChatComment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment with id : " + id + "is not valid"));
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


