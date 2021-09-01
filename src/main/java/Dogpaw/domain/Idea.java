package Dogpaw.domain;

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
    @Column(name = "Idea_ID")
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
    private List<UploadFile> fileList = new ArrayList<>();

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdeaBoard_ID")
    private IdeaBoard ideaBoard;

    public Idea(User user, String text, LocalDate date, LocalTime time, List<UploadFile> fileList, IdeaBoard ideaBoard){
        this.user = user;
        this.text = text;
        this.date = date;
        this.time = time;
        for(UploadFile file : fileList){
            this.fileList.add(file);
        }
        this.ideaBoard = ideaBoard;
    }
    /*
    public void add(UploadFile file){
        fileList.add(file);
    }
    */
}

//    @OneToMany
//    List<File> files_ID = new ArrayList<>();
