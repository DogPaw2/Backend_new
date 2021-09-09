package Dogpaw.domain.message;

import Dogpaw.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @NonNull
    private String text;

    @NonNull
    private LocalDate date;

    @NonNull
    private LocalTime time;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MessageFile> fileList = new ArrayList<>();

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MESSEAGE_ID")
    private MessageRoom messageRoom;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MessageComment> comments = new ArrayList<>();

    // 위크스페이스와 연결
}
