package Dogpaw.service.message;


import Dogpaw.domain.message.MessageComment;
import Dogpaw.repository.message.MessageRepository;
import Dogpaw.repository.message.MessageCommentRepository;
import Dogpaw.service.exception.exception;
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

    public Long saveComment (MessageComment comment) throws exception.ArgumentNullException, exception.InvalidArgumentException {
        if(comment == null){
            throw new exception.ArgumentNullException("Comment can't be null");
        }
        if(comment.getText().isEmpty()){
            throw new exception.InvalidArgumentException("Comment text is null");
        }
        comment.getMessage().getComments().add(comment);
        MessageComment save = commentRepository.save(comment);

        return save.getId();
    }

    public void deleteByCommentId(Long id) throws exception.DogpawNotFoundException{
        commentRepository.deleteById(id);
    }

    public void updateByCommentId(Long id, String text) throws exception.DogpawNotFoundException {
        MessageComment messageComment = findOne(id);
        messageComment.setText(text);
        commentRepository.save(messageComment);
    }

    public MessageComment findOne(Long id) throws exception.DogpawNotFoundException{
        MessageComment comment = commentRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("Comment with id : " + id + "is not valid"));
        return comment;
    }

}


