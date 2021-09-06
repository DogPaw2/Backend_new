package Dogpaw.service.chatting;

import Dogpaw.domain.chatting.ChatFile;
import Dogpaw.repository.chatting.ChatFileRepository;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatFileService {

    @NonNull
    private final ChatFileRepository fileRepository;

    public Long saveFile (ChatFile chatFile) throws exception.ArgumentNullException, exception.InvalidArgumentException {
        if(chatFile == null){
            throw new exception.ArgumentNullException("file can't be null");
        }
        if(chatFile.getOriginName().isEmpty() || chatFile.getFileName().isEmpty() || chatFile.getContentType().isEmpty() || chatFile.getPath().isEmpty()){
            throw new exception.InvalidArgumentException("original file name or file name or content type or path is null");
        }
        ChatFile save = fileRepository.save(chatFile);

        return save.getId();

    }

    public ChatFile findOne(Long id) throws exception.DogpawNotFoundException {
        ChatFile chatFile = fileRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("file with id : " + id + "is not valid"));
        return chatFile;
    }


    public void deleteByFileId(Long id) throws exception.DogpawNotFoundException {
        fileRepository.deleteById(id);
    }

    public ChatFile getFile(Long id){
        ChatFile chatFile = fileRepository.getById(id);
        return chatFile;
    }
}
