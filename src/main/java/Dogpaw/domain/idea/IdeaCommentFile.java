package Dogpaw.domain.idea;

import Dogpaw.domain.UploadFile;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class IdeaCommentFile extends UploadFile {
    @Getter(AccessLevel.NONE)
    @NonNull
    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private IdeaComment ideaComment;

    public IdeaCommentFile(String originName, String fileName, String contentType, long fileSize, String path, IdeaComment ideaComment){
        super(originName, fileName, contentType, fileSize, path);
        this.ideaComment = ideaComment;
    }
}
