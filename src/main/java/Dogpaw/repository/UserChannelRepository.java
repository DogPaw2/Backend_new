package Dogpaw.repository;

import Dogpaw.domain.Channel;
import Dogpaw.domain.User;
import Dogpaw.domain.UserChannel;
import Dogpaw.domain.UserChannelMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserChannelRepository extends JpaRepository<UserChannel, Long> {
    List<UserChannelMapping> findAllByUser(User User);
}
