package Dogpaw.repository;

import Dogpaw.domain.Chat;
import Dogpaw.domain.ChatMapping;
import Dogpaw.domain.Comment;
import Dogpaw.domain.CommentMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    List<CommentMapping> findAllByChat(Chat chat);
}
