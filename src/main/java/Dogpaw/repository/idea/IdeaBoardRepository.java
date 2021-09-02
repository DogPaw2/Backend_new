package Dogpaw.repository.idea;

import Dogpaw.domain.idea.IdeaBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeaBoardRepository extends JpaRepository<IdeaBoard, Long> {
//    List<IdeaBoard> findAllByUserOrderByName(String User);
}
