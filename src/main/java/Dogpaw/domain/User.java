package Dogpaw.domain;

import Dogpaw.domain.message.MessageRoom;
import Dogpaw.domain.message.UserMessageRoom;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user")
    private List<UserChannel> userChannels = new ArrayList<>();

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user")
    private List<UserWorkspace> userWorkspaces = new ArrayList<>();

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user")
    private List<UserMessageRoom> userMessageRooms = new ArrayList<>();
}
