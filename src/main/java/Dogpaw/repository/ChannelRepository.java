package Dogpaw.repository;

import Dogpaw.domain.Channel;
import Dogpaw.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

}
