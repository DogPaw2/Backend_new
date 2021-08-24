package Dogpaw.repository;

import Dogpaw.domain.Idea;
import Dogpaw.domain.IdeaMapping;
import Dogpaw.domain.IdeaBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdeaRepository extends JpaRepository<Idea, Long> {
    List<IdeaMapping> findAllByIdeaBoard(IdeaBoard ideaBoard);
}
