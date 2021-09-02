package Dogpaw.repository.message;

import Dogpaw.domain.message.MessageComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageCommentRepository extends JpaRepository<MessageComment, Long> {
//    List<CommentMapping> findAllByChat(Chat chat);
}
