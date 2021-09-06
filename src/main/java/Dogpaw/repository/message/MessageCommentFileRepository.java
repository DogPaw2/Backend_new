package Dogpaw.repository.message;

import Dogpaw.domain.message.MessageCommentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageCommentFileRepository extends JpaRepository<MessageCommentFile, Long> {
}
