package Dogpaw.repository.idea;

import Dogpaw.domain.idea.Idea;
import Dogpaw.domain.idea.IdeaMapping;
import Dogpaw.domain.idea.IdeaBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdeaRepository extends JpaRepository<Idea, Long> {
    List<IdeaMapping> findAllByIdeaBoard(IdeaBoard ideaBoard);
}
