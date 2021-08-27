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
@AllArgsConstructor
public class Idea {
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

    private Long fileId;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdeaBoard_ID")
    private IdeaBoard ideaBoard;

    public Idea(User user, String text, LocalDate date, LocalTime time, Long fileId, IdeaBoard ideaBoard){
        this.user = user;
        this.text = text;
        this.date = date;
        this.time = time;
        this.fileId = fileId;
        this.ideaBoard = ideaBoard;
    }
}

//    @OneToMany
//    List<File> files_ID = new ArrayList<>();
