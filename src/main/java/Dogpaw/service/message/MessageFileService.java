package Dogpaw.service.message;

import Dogpaw.domain.message.MessageFile;
import Dogpaw.repository.message.MessageFileRepository;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageFileService {

    @NonNull
    private final MessageFileRepository fileRepository;

    public Long saveFile (MessageFile messageFile) throws exception.ArgumentNullException, exception.InvalidArgumentException {
        if(messageFile == null){
            throw new exception.ArgumentNullException("file can't be null");
        }
        if(messageFile.getOriginName().isEmpty() || messageFile.getFileName().isEmpty() || messageFile.getContentType().isEmpty() || messageFile.getPath().isEmpty()){
            throw new exception.InvalidArgumentException("original file name or file name or content type or path is null");
        }
        MessageFile save = fileRepository.save(messageFile);

        return save.getId();

    }

    public MessageFile findOne(Long id) throws exception.DogpawNotFoundException {
        MessageFile messageFile = fileRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("file with id : " + id + "is not valid"));
        return messageFile;
    }


    public void deleteByFileId(Long id) throws exception.DogpawNotFoundException {
        fileRepository.deleteById(id);
    }

    public MessageFile getFile(Long id){
        MessageFile messageFile = fileRepository.getById(id);
        return messageFile;
    }
}
