package Dogpaw.domain.chatting;

import Dogpaw.domain.UploadFile;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class ChatFile extends UploadFile {
    @Getter(AccessLevel.NONE)
    @NonNull
    @ManyToOne
    @JoinColumn(name = "CHAT_ID")
    private Chat chat;

    public ChatFile(String originName, String fileName, String contentType, long fileSize, String path, Chat chat){
        super(originName, fileName, contentType, fileSize, path);
        this.chat = chat;
    }
}
