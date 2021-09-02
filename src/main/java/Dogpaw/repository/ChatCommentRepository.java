package Dogpaw.repository;

import Dogpaw.domain.ChatComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatCommentRepository extends JpaRepository<ChatComment, Long> {
//    List<CommentMapping> findAllByChat(Chat chat);
}
