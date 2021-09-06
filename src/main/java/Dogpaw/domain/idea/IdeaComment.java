package Dogpaw.domain.idea;


import Dogpaw.domain.Comment;
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
public class IdeaComment extends Comment {
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDEA_ID")
    private Idea idea;

    @OneToMany(mappedBy = "ideaComment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<IdeaCommentFile> fileList = new ArrayList<>();

    public IdeaComment(User user, String text, LocalDate date, LocalTime time, Idea idea){
        super(user, text, date, time);
        this.idea = idea;
    }
}
