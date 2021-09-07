package Dogpaw.domain.message;

import Dogpaw.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserMessageRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Getter
    @ManyToOne
    @JoinColumn(name = "MESSAGEROOM_ID")
    private MessageRoom messageRoom;

    @Builder
    public UserMessageRoom(User user, MessageRoom messageRoom) {
        this.user = user;
        this.messageRoom = messageRoom;
    }
}
