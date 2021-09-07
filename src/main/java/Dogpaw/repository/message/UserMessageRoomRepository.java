package Dogpaw.repository.message;

import Dogpaw.domain.User;
import Dogpaw.domain.message.UserMessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMessageRoomRepository extends JpaRepository<UserMessageRoom, Long> {
    List<UserMessageRoom> findAllByUser(User User);

}
