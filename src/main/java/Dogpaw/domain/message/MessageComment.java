package Dogpaw.domain.message;

import Dogpaw.domain.Comment;
import Dogpaw.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class MessageComment extends Comment {
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MESSAGE_ID")
    private Message message;

    @OneToMany(mappedBy = "messageComment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MessageCommentFile> fileList = new ArrayList<>();

    public MessageComment(User user, String text, LocalDate date, LocalTime time, Message message){
        super(user, text, date, time);
        this.message = message;

    }
}
