package Dogpaw.repository;

import Dogpaw.domain.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChattingRepository extends JpaRepository<Chatting, Long> {
//    List<Chatting> findAllByUserOrderByName(String User);
}
