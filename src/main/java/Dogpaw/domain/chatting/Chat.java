package Dogpaw.domain.chatting;


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
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @NonNull
    private User user;

    @NonNull
    private String text;

    @NonNull
    private LocalDate date;
    @NonNull
    private LocalTime time;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHATTING_ID")
    private Chatting chatting;

    @OneToMany(mappedBy = "chat",  cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChatComment> comments = new ArrayList<>();
}
