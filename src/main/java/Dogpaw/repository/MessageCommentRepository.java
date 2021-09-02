package Dogpaw.repository;

import Dogpaw.domain.MessageComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageCommentRepository extends JpaRepository<MessageComment, Long> {
//    List<CommentMapping> findAllByChat(Chat chat);
}
