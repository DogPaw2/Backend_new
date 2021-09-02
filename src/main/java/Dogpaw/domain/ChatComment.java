package Dogpaw.domain;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class ChatComment extends Comment{
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ID")
    private Chat chat;

    public ChatComment(User user, String text, LocalDate date, LocalTime time, Chat chat){
        super(user, text, date, time);
        this.chat = chat;
    }
}
