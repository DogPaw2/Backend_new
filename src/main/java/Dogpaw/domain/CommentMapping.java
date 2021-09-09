package Dogpaw.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CommentMapping {
    Long getId();
    User getUser();
    String getText();
    LocalDate getDate();
    LocalTime getTime();
    List<UploadFile> getFileList();
}
