package Dogpaw.domain;

import Dogpaw.domain.idea.Idea;
import lombok.*;

import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class UploadFile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter(AccessLevel.NONE)
    @NonNull
    @ManyToOne
    @JoinColumn(name = "IDEA_ID")
    private Idea idea;

    @NonNull
    private String originName;

    @NonNull
    private String fileName;

    @NonNull
    private String contentType;

    @NonNull
    private String path;
}
