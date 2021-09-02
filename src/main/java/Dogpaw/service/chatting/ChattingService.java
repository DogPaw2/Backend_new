package Dogpaw.service.chatting;

import Dogpaw.domain.chatting.Chatting;
import Dogpaw.repository.chatting.ChattingRepository;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChattingService {
    @NonNull
    private final ChattingRepository chattingRepository;

    public Long saveChatting (Chatting chatting) throws exception.ArgumentNullException, exception.InvalidArgumentException{
        if(chatting == null){
            throw new exception.ArgumentNullException("Chatting can't be null");
        }
        Chatting save = chattingRepository.save(chatting);


        return save.getId();
    }

    public void deleteByChattingId(Long id) throws exception.DogpawNotFoundException{
        chattingRepository.deleteById(id);
    }

    public Chatting findOne(Long id) throws exception.DogpawNotFoundException{
        Chatting chatting = chattingRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("Chatting with id : "+ id + "is not valid"));
        return chatting;
    }

}