package Dogpaw.service.chatting;


import Dogpaw.domain.chatting.ChatComment;
import Dogpaw.repository.chatting.ChatRepository;
import Dogpaw.repository.chatting.ChatCommentRepository;
import Dogpaw.service.exception.exception;
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

    public Long saveComment (ChatComment comment) throws exception.ArgumentNullException, exception.InvalidArgumentException {
        if(comment == null){
            throw new exception.ArgumentNullException("Chat can't be null");
        }
        if(comment.getText().isEmpty()){
            throw new exception.InvalidArgumentException("Chatting text is null");
        }
        comment.getChat().getComments().add(comment);
        ChatComment save = commentRepository.save(comment);

        return save.getId();
    }

    public void deleteByCommentId(Long id) throws exception.DogpawNotFoundException{
        commentRepository.deleteById(id);
    }

    public ChatComment findOne(Long id) throws exception.DogpawNotFoundException{
        ChatComment comment = commentRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("Comment with id : " + id + "is not valid"));
        return comment;
    }

}


