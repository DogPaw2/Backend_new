package Dogpaw.repository.idea;

import Dogpaw.domain.idea.IdeaCommentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeaCommentFileRepository extends JpaRepository<IdeaCommentFile, Long> {
}
