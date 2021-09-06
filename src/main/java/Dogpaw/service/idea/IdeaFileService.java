package Dogpaw.service.idea;

import Dogpaw.domain.idea.IdeaFile;
import Dogpaw.repository.idea.IdeaFileRepository;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IdeaFileService {

    @NonNull
    private final IdeaFileRepository fileRepository;

    public Long saveFile (IdeaFile ideaFile) throws exception.ArgumentNullException, exception.InvalidArgumentException {
        if(ideaFile == null){
            throw new exception.ArgumentNullException("file can't be null");
        }
        if(ideaFile.getOriginName().isEmpty() || ideaFile.getFileName().isEmpty() || ideaFile.getContentType().isEmpty() || ideaFile.getPath().isEmpty()){
            throw new exception.InvalidArgumentException("original file name or file name or content type or path is null");
        }
        IdeaFile save = fileRepository.save(ideaFile);

        return save.getId();

    }

    public IdeaFile findOne(Long id) throws exception.DogpawNotFoundException {
        IdeaFile ideaFile = fileRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("file with id : " + id + "is not valid"));
        return ideaFile;
    }


    public void deleteByFileId(Long id) throws exception.DogpawNotFoundException {
        fileRepository.deleteById(id);
    }

    public IdeaFile getFile(Long id){
        IdeaFile ideaFile = fileRepository.getById(id);
        return ideaFile;
    }
}
