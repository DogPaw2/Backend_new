package Dogpaw.repository.idea;

import Dogpaw.domain.idea.IdeaFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeaFileRepository extends JpaRepository<IdeaFile, Long> {
}
