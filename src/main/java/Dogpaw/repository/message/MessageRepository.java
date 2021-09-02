package Dogpaw.repository.message;

import Dogpaw.domain.message.Message;
import Dogpaw.domain.message.MessageMapping;
import Dogpaw.domain.message.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<MessageMapping> findAllByMessageRoom(MessageRoom messageRoom);
}
