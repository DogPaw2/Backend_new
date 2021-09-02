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
public class MessageComment extends Comment{
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MESSAGE_ID")
    private Message message;

    public MessageComment(User user, String text, LocalDate date, LocalTime time, Message message){
        super(user, text, date, time);
        this.message = message;

    }
}
