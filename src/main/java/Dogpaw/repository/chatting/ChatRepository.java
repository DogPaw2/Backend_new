package Dogpaw.repository.chatting;

import Dogpaw.domain.chatting.Chat;
import Dogpaw.domain.chatting.ChatMapping;
import Dogpaw.domain.chatting.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<ChatMapping> findAllByChatting(Chatting chatting);
}
