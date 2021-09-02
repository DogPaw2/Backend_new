package Dogpaw.service;

import Dogpaw.domain.Message;
import Dogpaw.domain.MessageMapping;
import Dogpaw.domain.MessageRoom;
import Dogpaw.repository.MessageRepository;
import Dogpaw.repository.MessageRoomRepository;
import javassist.NotFoundException;
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

    public Long saveMessage(Message message) throws ArgumentNullException, InvalidArgumentException {
        if(message == null) {
            throw new ArgumentNullException("Message can't be null");
        }
        if(message.getText().isEmpty()){
            throw new InvalidArgumentException("Message TEXT is null");
        }
        Message save = messageRepository.save(message);

        return save.getId();
    }

    public Optional<Message> checkMessage(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        return message;
    }

    public Message findOne(Long id) throws NotFoundException {
        Message message = messageRepository.findById(id).orElseThrow(() -> new MessageRoomNotFoundException("Message with id : " + id + "is not valid"));
        return message;
    }

    public void deleteByMessageId(Long id) throws NotFoundException {
        messageRepository.deleteById(id);
    }

    // ** 업데이트 추가 **
    public void updateByMessageId(Long id, String text) throws NotFoundException {
        Message message = findOne(id);
        message.setText(text);
        messageRepository.save(message);
    }

    public List<MessageMapping> getMessageList(Long id) throws NotFoundException {
        MessageRoom messageRoom = messageRoomRepository.findById(id).orElseThrow(() -> new MessageRoomNotFoundException("MessageRoom with id : "+ id + "is not valid"));
        return messageRepository.findAllByMessageRoom(messageRoom);
    }


    // exception

    public static class MessageRoomNotFoundException extends NotFoundException {
        public MessageRoomNotFoundException(String msg) { super(msg); }
    }

    public static class ArgumentNullException extends Throwable {
        public ArgumentNullException(String s) {}
    }

    public static class InvalidArgumentException extends Throwable {
        public InvalidArgumentException(String s) {}
    }
}
