package Dogpaw.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class Channel {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String purpose;

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "channel")
    private List<UserChannel> users = new ArrayList<>();

    @NonNull
    @OneToOne
    @JoinColumn(name = "CHATTING_ID")
    private Chatting chatting;

    @NonNull
    @OneToOne
    @JoinColumn(name = "IdeaBoard_ID")
    private IdeaBoard ideaBoard;

}
