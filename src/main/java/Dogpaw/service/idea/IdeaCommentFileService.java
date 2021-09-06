package Dogpaw.service.idea;

import Dogpaw.domain.idea.IdeaCommentFile;
import Dogpaw.repository.idea.IdeaCommentFileRepository;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IdeaCommentFileService {

    @NonNull
    private final IdeaCommentFileRepository fileRepository;

    public Long saveFile (IdeaCommentFile ideaCommentFile) throws exception.ArgumentNullException, exception.InvalidArgumentException {
        if(ideaCommentFile == null){
            throw new exception.ArgumentNullException("file can't be null");
        }
        if(ideaCommentFile.getOriginName().isEmpty() || ideaCommentFile.getFileName().isEmpty() || ideaCommentFile.getContentType().isEmpty() || ideaCommentFile.getPath().isEmpty()){
            throw new exception.InvalidArgumentException("original file name or file name or content type or path is null");
        }
        IdeaCommentFile save = fileRepository.save(ideaCommentFile);

        return save.getId();

    }

    public IdeaCommentFile findOne(Long id) throws exception.DogpawNotFoundException {
        IdeaCommentFile ideaCommentFile = fileRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("file with id : " + id + "is not valid"));
        return ideaCommentFile;
    }


    public void deleteByFileId(Long id) throws exception.DogpawNotFoundException {
        fileRepository.deleteById(id);
    }

    public IdeaCommentFile getFile(Long id){
        IdeaCommentFile ideaCommentFile = fileRepository.getById(id);
        return ideaCommentFile;
    }
}
