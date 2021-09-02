package Dogpaw.service.message;

import Dogpaw.domain.message.Message;
import Dogpaw.domain.message.MessageMapping;
import Dogpaw.domain.message.MessageRoom;
import Dogpaw.repository.message.MessageRepository;
import Dogpaw.repository.message.MessageRoomRepository;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    @NonNull
    private final MessageRepository messageRepository;
    private final MessageRoomRepository messageRoomRepository;

    public Long saveMessage(Message message) throws exception.ArgumentNullException, exception.InvalidArgumentException {
        if(message == null) {
            throw new exception.ArgumentNullException("Message can't be null");
        }
        if(message.getText().isEmpty()){
            throw new exception.InvalidArgumentException("Message TEXT is null");
        }
        Message save = messageRepository.save(message);

        return save.getId();
    }

    public Optional<Message> checkMessage(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        return message;
    }

    public Message findOne(Long id) throws exception.DogpawNotFoundException {
        Message message = messageRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("Message with id : " + id + "is not valid"));
        return message;
    }

    public void deleteByMessageId(Long id) throws exception.DogpawNotFoundException {
        messageRepository.deleteById(id);
    }

    // ** 업데이트 추가 **
    public void updateByMessageId(Long id, String text) throws exception.DogpawNotFoundException {
        Message message = findOne(id);
        message.setText(text);
        messageRepository.save(message);
    }

    public List<MessageMapping> getMessageList(Long id) throws exception.DogpawNotFoundException {
        MessageRoom messageRoom = messageRoomRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("MessageRoom with id : "+ id + "is not valid"));
        return messageRepository.findAllByMessageRoom(messageRoom);
    }

}
