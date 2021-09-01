package Dogpaw.repository;

import Dogpaw.domain.User;
import Dogpaw.domain.UserChannelMapping;
import Dogpaw.domain.UserWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWorkspaceRepository extends JpaRepository<UserWorkspace, Long> {
    List<UserWorkspace> findAllByUser(User User);

}
