package Dogpaw.repository.chatting;

import Dogpaw.domain.chatting.ChatCommentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatCommentFileRepository extends JpaRepository<ChatCommentFile, Long> {
}
