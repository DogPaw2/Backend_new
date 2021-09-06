package Dogpaw.domain.idea;

import Dogpaw.domain.UploadFile;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class IdeaFile extends UploadFile {
    @Getter(AccessLevel.PROTECTED)
    @NonNull
    @ManyToOne
    @JoinColumn(name = "IDEA_ID")
    private Idea idea;

    public IdeaFile(String originName, String fileName, String contentType, long fileSize, String path, Idea idea){
        super(originName, fileName, contentType, fileSize, path);
        this.idea = idea;
    }
}
