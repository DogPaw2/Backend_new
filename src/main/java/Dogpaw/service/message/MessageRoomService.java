package Dogpaw.service.message;

import Dogpaw.domain.User;
import Dogpaw.domain.message.MessageRoom;
import Dogpaw.domain.message.UserMessageRoom;
import Dogpaw.repository.UserRepository;
import Dogpaw.repository.message.MessageRoomRepository;
import Dogpaw.repository.message.UserMessageRoomRepository;
import Dogpaw.service.UserService;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageRoomService {

    @NonNull
    @Autowired
    private final MessageRoomRepository messageRoomRepository;
    @NonNull
    private final UserRepository userRepository;
    @NonNull
    private final UserMessageRoomRepository userMessageRoomRepository;

    public Long saveMessageRoom(MessageRoom messageRoom, Long userId) throws exception.ArgumentNullException, exception.DogpawNotFoundException {
        if(messageRoom == null) {
            throw new exception.ArgumentNullException("MessageRoom can't be null");
        }
        if(userId == null) {
            throw new exception.ArgumentNullException("userId can't be null");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new exception.DogpawNotFoundException("User with id : " + userId + "is not valid"));
        UserMessageRoom userMessageRoom = new UserMessageRoom(user, messageRoom);
        MessageRoom save = messageRoomRepository.save(messageRoom);

        return save.getId();
    }


    public MessageRoom findOne(Long messageRoomId) throws exception.DogpawNotFoundException {
        MessageRoom messageRoom = messageRoomRepository.findById(messageRoomId).orElseThrow(() -> new exception.DogpawNotFoundException("MessageRoom with id : " + messageRoomId + "is not valid"));
        return messageRoom;
    }

    public void deleteByMessageRoomId(Long messageRoomId) throws exception.DogpawNotFoundException {
        messageRoomRepository.deleteById(messageRoomId);
    }

    public List<UserMessageRoom> getMessageRoomList(Long userId) {
        User user = userRepository.findById(userId).get();
        return userMessageRoomRepository.findAllByUser(user);
    }
}
