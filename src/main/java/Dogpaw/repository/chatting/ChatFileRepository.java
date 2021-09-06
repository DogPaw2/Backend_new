package Dogpaw.repository.chatting;

import Dogpaw.domain.chatting.ChatFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatFileRepository extends JpaRepository<ChatFile, Long> {
}
