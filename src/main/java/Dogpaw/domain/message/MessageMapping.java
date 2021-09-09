package Dogpaw.domain.message;


import Dogpaw.domain.CommentMapping;
import Dogpaw.domain.UploadFile;
import Dogpaw.domain.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MessageMapping {
    User getUser();
    String getText();
    LocalDate getDate();
    LocalTime getTime();
    List<UploadFile> getFileList();
    List<CommentMapping> getComments();
}
