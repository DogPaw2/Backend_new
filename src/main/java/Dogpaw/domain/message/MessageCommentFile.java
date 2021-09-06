package Dogpaw.domain.message;

import Dogpaw.domain.UploadFile;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class MessageCommentFile extends UploadFile {
    @Getter(AccessLevel.NONE)
    @NonNull
    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private MessageComment messageComment;

    public MessageCommentFile(String originName, String fileName, String contentType, long fileSize, String path, MessageComment messageComment){
        super(originName, fileName, contentType, fileSize, path);
        this.messageComment = messageComment;
    }
}
