package Dogpaw.domain.chatting;

import Dogpaw.domain.UploadFile;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class ChatCommentFile extends UploadFile {
    @Getter(AccessLevel.NONE)
    @NonNull
    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private ChatComment chatComment;

    public ChatCommentFile(String originName, String fileName, String contentType, long fileSize, String path, ChatComment chatComment){
        super(originName, fileName, contentType, fileSize, path);
        this.chatComment = chatComment;
    }
}
