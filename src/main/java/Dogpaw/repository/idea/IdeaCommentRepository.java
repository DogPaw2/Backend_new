package Dogpaw.repository.idea;

import Dogpaw.domain.idea.IdeaComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeaCommentRepository extends JpaRepository<IdeaComment, Long> {
//    List<CommentMapping> findAllByChat(Chat chat);
}
