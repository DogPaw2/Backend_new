package Dogpaw.domain.message;

import Dogpaw.domain.UploadFile;
import Dogpaw.domain.message.Message;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class MessageFile extends UploadFile {
    @Getter(AccessLevel.NONE)
    @NonNull
    @ManyToOne
    @JoinColumn(name = "MESSAGE_ID")
    private Message message;

    public MessageFile(String originName, String fileName, String contentType, long fileSize, String path, Message message){
        super(originName, fileName, contentType, fileSize, path);
        this.message = message;
    }
}
