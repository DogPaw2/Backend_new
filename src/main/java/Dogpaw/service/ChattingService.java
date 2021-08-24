package Dogpaw.service;

import Dogpaw.domain.Chat;
import Dogpaw.domain.Chatting;
import Dogpaw.repository.ChattingRepository;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChattingService {
    @NonNull
    private final ChattingRepository chattingRepository;

    public Long saveChatting (Chatting chatting) throws ArgumentNullException, InvalidArgumentException{
        if(chatting == null){
            throw new ArgumentNullException("Chatting can't be null");
        }
        Chatting save = chattingRepository.save(chatting);


        return save.getId();
    }

    public void deleteByChattingId(Long id) throws NotFoundException{
        chattingRepository.deleteById(id);
    }

    public Chatting findOne(Long id) throws NotFoundException{
        Chatting chatting = chattingRepository.findById(id).orElseThrow(() -> new ChattingNotFoundException("Chatting with id : "+ id + "is not valid"));
        return chatting;
    }



// exception

    public static class ChattingNotFoundException extends NotFoundException {
        public ChattingNotFoundException(String msg) {
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