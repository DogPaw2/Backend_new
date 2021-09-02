package Dogpaw.service.message;

import Dogpaw.domain.message.MessageRoom;
import Dogpaw.repository.message.MessageRoomRepository;
import Dogpaw.service.exception.exception;
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

    public Long saveMessageRoom(MessageRoom messageRoom) throws exception.ArgumentNullException {
        if(messageRoom == null) {
            throw new exception.ArgumentNullException("MessageAll can't be null");
        }
        MessageRoom save = messageRoomRepository.save(messageRoom);

        return save.getId();
    }


    public MessageRoom findOne(Long id) throws exception.DogpawNotFoundException {
        MessageRoom messageRoom = messageRoomRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("MessageAll with id : " + id + "is not valid"));
        return messageRoom;
    }

    public void deleteByMessageRoomId(Long id) throws exception.DogpawNotFoundException {
        messageRoomRepository.deleteById(id);
    }
}
