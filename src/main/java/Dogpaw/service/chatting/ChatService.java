package Dogpaw.service.chatting;


import Dogpaw.domain.chatting.Chat;
import Dogpaw.domain.chatting.ChatMapping;
import Dogpaw.domain.chatting.Chatting;
import Dogpaw.domain.idea.Idea;
import Dogpaw.repository.chatting.ChatRepository;
import Dogpaw.repository.chatting.ChattingRepository;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {
    @NonNull
    private final ChatRepository chatRepository;
    private final ChattingRepository chattingRepository;

    public Long saveChat (Chat chat) throws exception.ArgumentNullException, exception.InvalidArgumentException {
        if(chat == null){
            throw new exception.ArgumentNullException("Chat can't be null");
        }
        if(chat.getText().isEmpty()){
            throw new exception.InvalidArgumentException("Chatting text is null");
        }
        Chat save = chatRepository.save(chat);

        return save.getId();
    }

    public void deleteByChatId(Long id) throws exception.DogpawNotFoundException{
        chatRepository.deleteById(id);
    }

    public void updateByChatId(Long id, String text) throws exception.DogpawNotFoundException {
        Chat chat = findOne(id);
        chat.setText(text);
        chatRepository.save(chat);
    }

    public Chat findOne(Long id) throws exception.DogpawNotFoundException{
        Chat chat = chatRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("Chat with id : " + id + "is not valid"));
        return chat;
    }

    public List<ChatMapping> getChatList(Long id) throws exception.DogpawNotFoundException{
        Chatting chatting = chattingRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("Chatting with id : "+ id + "is not valid"));
        return chatRepository.findAllByChatting(chatting);
    }
}


