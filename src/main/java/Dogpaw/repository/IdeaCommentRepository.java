package Dogpaw.repository;

import Dogpaw.domain.IdeaComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeaCommentRepository extends JpaRepository<IdeaComment, Long> {
//    List<CommentMapping> findAllByChat(Chat chat);
}
