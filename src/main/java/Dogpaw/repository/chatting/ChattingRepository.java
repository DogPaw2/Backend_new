package Dogpaw.repository.chatting;

import Dogpaw.domain.chatting.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRepository extends JpaRepository<Chatting, Long> {
//    List<Chatting> findAllByUserOrderByName(String User);
}
