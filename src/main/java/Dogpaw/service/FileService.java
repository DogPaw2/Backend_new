package Dogpaw.service;

import Dogpaw.domain.UploadFile;
import Dogpaw.repository.FileRepository;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

    @NonNull
    private final FileRepository fileRepository;

    public Long saveFile (UploadFile uploadFile) throws exception.ArgumentNullException, exception.InvalidArgumentException {
        if(uploadFile == null){
            throw new exception.ArgumentNullException("file can't be null");
        }
        if(uploadFile.getOriginName().isEmpty() || uploadFile.getFileName().isEmpty() || uploadFile.getContentType().isEmpty() || uploadFile.getPath().isEmpty()){
            throw new exception.InvalidArgumentException("original file name or file name or content type or path is null");
        }
        UploadFile save = fileRepository.save(uploadFile);

        return save.getId();

    }

    public UploadFile findOne(Long id) throws exception.DogpawNotFoundException {
        UploadFile uploadFile = fileRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("file with id : " + id + "is not valid"));
        return uploadFile;
    }


    public void deleteByFileId(Long id) throws exception.DogpawNotFoundException {

        fileRepository.deleteById(id);

    }

    public UploadFile getFile(Long id){
        UploadFile uploadFile = fileRepository.getById(id);
        return uploadFile;
    }
}
