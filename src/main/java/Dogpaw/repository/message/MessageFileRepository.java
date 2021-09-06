package Dogpaw.repository.message;

import Dogpaw.domain.message.MessageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageFileRepository extends JpaRepository<MessageFile, Long> {
}
