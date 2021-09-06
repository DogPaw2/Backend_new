package Dogpaw.service.chatting;

import Dogpaw.domain.chatting.ChatCommentFile;
import Dogpaw.domain.chatting.ChatFile;
import Dogpaw.repository.chatting.ChatCommentFileRepository;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatCommentFileService {

    @NonNull
    private final ChatCommentFileRepository fileRepository;

    public Long saveFile (ChatCommentFile chatCommentFile) throws exception.ArgumentNullException, exception.InvalidArgumentException {
        if(chatCommentFile == null){
            throw new exception.ArgumentNullException("file can't be null");
        }
        if(chatCommentFile.getOriginName().isEmpty() || chatCommentFile.getFileName().isEmpty() || chatCommentFile.getContentType().isEmpty() || chatCommentFile.getPath().isEmpty()){
            throw new exception.InvalidArgumentException("original file name or file name or content type or path is null");
        }
        ChatCommentFile save = fileRepository.save(chatCommentFile);

        return save.getId();

    }

    public ChatCommentFile findOne(Long id) throws exception.DogpawNotFoundException {
        ChatCommentFile chatCommentFile = fileRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("file with id : " + id + "is not valid"));
        return chatCommentFile;
    }


    public void deleteByFileId(Long id) throws exception.DogpawNotFoundException {
        fileRepository.deleteById(id);
    }

    public ChatCommentFile getFile(Long id){
        ChatCommentFile chatCommentFile = fileRepository.getById(id);
        return chatCommentFile;
    }
}
