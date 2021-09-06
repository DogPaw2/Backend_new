package Dogpaw.domain.chatting;

import Dogpaw.domain.CommentMapping;
import Dogpaw.domain.UploadFile;
import Dogpaw.domain.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ChatMapping {
    User getUser();
    String getText();
    LocalDate getDate();
    LocalTime getTime();
    List<UploadFile> getFileList();
    List<CommentMapping> getComments();
}
