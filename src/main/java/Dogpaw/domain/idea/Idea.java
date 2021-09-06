package Dogpaw.domain.idea;

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
@AllArgsConstructor
public class Idea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDEA_ID")
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

    @OneToMany(mappedBy = "idea", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<IdeaFile> fileList = new ArrayList<>();

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDEABOARD_ID")
    private IdeaBoard ideaBoard;

    @OneToMany(mappedBy = "idea",  cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<IdeaComment> comments = new ArrayList<>();
}