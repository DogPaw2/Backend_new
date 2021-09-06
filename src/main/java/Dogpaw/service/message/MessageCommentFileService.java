package Dogpaw.service.message;

import Dogpaw.domain.message.MessageCommentFile;
import Dogpaw.repository.message.MessageCommentFileRepository;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageCommentFileService {

    @NonNull
    private final MessageCommentFileRepository fileRepository;

    public Long saveFile (MessageCommentFile messageCommentFile) throws exception.ArgumentNullException, exception.InvalidArgumentException {
        if(messageCommentFile == null){
            throw new exception.ArgumentNullException("file can't be null");
        }
        if(messageCommentFile.getOriginName().isEmpty() || messageCommentFile.getFileName().isEmpty() || messageCommentFile.getContentType().isEmpty() || messageCommentFile.getPath().isEmpty()){
            throw new exception.InvalidArgumentException("original file name or file name or content type or path is null");
        }
        MessageCommentFile save = fileRepository.save(messageCommentFile);

        return save.getId();

    }

    public MessageCommentFile findOne(Long id) throws exception.DogpawNotFoundException {
        MessageCommentFile messageCommentFile = fileRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("file with id : " + id + "is not valid"));
        return messageCommentFile;
    }


    public void deleteByFileId(Long id) throws exception.DogpawNotFoundException {
        fileRepository.deleteById(id);
    }

    public MessageCommentFile getFile(Long id){
        MessageCommentFile messageCommentFile = fileRepository.getById(id);
        return messageCommentFile;
    }
}
