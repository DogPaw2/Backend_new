package Dogpaw.repository;

import Dogpaw.domain.Comment;
import Dogpaw.domain.Comment2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Comment2Repository extends JpaRepository<Comment2, Long> {
//    List<CommentMapping> findAllByChat(Chat chat);
}
