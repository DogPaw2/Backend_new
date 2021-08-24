package Dogpaw.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ChatMapping {
    User getUser();
    String getText();
    LocalDate getDate();
    LocalTime getTime();
    List<CommentMapping> getComments();
}
