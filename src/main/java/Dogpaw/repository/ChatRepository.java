package Dogpaw.repository;

import Dogpaw.domain.Chat;
import Dogpaw.domain.ChatMapping;
import Dogpaw.domain.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<ChatMapping> findAllByChatting(Chatting chatting);
}
