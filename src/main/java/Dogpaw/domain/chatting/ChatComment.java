package Dogpaw.domain.chatting;


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
public class ChatComment extends Comment {
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ID")
    private Chat chat;

    @OneToMany(mappedBy = "chatComment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChatCommentFile> fileList = new ArrayList<>();

    public ChatComment(User user, String text, LocalDate date, LocalTime time, Chat chat){
        super(user, text, date, time);
        this.chat = chat;
    }
}
