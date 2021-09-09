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

    public void addUser(Long userId, Long messageRoomId) {
        MessageRoom messageRoom = messageRoomRepository.findById(messageRoomId).get();
        User user = userRepository.findById(userId).get();

        userMessageRoomRepository.save(UserMessageRoom.builder()
                .user(user)
                .messageRoom(messageRoom)
                .build());
    }


    public Long saveMessageRoom(MessageRoom messageRoom, Long userId1, Long userId2) throws exception.ArgumentNullException, exception.DogpawNotFoundException {
        if(messageRoom == null) {
            throw new exception.ArgumentNullException("MessageRoom can't be null");
        }
        if(userId1 == null) {
            throw new exception.ArgumentNullException("userId1 can't be null");
        }
        if(userId2 == null) {
            throw new exception.ArgumentNullException("userId2 can't be null");
        }

        MessageRoom save = messageRoomRepository.save(messageRoom);
        addUser(userId1, messageRoom.getId());
        addUser(userId2, messageRoom.getId());

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
