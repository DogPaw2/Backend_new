package Dogpaw.service;

import Dogpaw.domain.MessageRoom;
import Dogpaw.repository.MessageRoomRepository;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageRoomService {

    @NonNull
    @Autowired
    private final MessageRoomRepository messageRoomRepository;

    public Long saveMessageRoom(MessageRoom messageRoom) throws ArgumentNullException {
        if(messageRoom == null) {
            throw new ArgumentNullException("MessageAll can't be null");
        }
        MessageRoom save = messageRoomRepository.save(messageRoom);

        return save.getId();
    }


    public MessageRoom findOne(Long id) throws NotFoundException {
        MessageRoom messageRoom = messageRoomRepository.findById(id).orElseThrow(() -> new MessageAllNotFoundException("MessageAll with id : " + id + "is not valid"));
        return messageRoom;
    }

    public void deleteByMessageRoomId(Long id) throws NotFoundException {
        messageRoomRepository.deleteById(id);
    }


    // exception

    public static class MessageAllNotFoundException extends NotFoundException {
        public MessageAllNotFoundException(String msg) {
            super(msg);
        }
    }

    public static class ArgumentNullException extends Throwable {
        public ArgumentNullException(String s) {}
    }

    public static class InvalidArgumentException extends Throwable {
        public InvalidArgumentException(String s) {}
    }

}
