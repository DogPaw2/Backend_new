package Dogpaw.service;

import Dogpaw.domain.UploadFile;
import Dogpaw.repository.FileRepository;
import javassist.NotFoundException;
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

    public Long saveFile (UploadFile uploadFile) throws ArgumentNullException, InvalidArgumentException {
        if(uploadFile == null){
            throw new ArgumentNullException("file can't be null");
        }
        if(uploadFile.getOriginName().isEmpty() || uploadFile.getFileName().isEmpty() || uploadFile.getContentType().isEmpty() || uploadFile.getPath().isEmpty()){
            throw new InvalidArgumentException("original file name or file name or content type or path is null");
        }
        UploadFile save = fileRepository.save(uploadFile);

        return save.getId();

    }

    public UploadFile findOne(Long id) throws NotFoundException {
        UploadFile uploadFile = fileRepository.findById(id).orElseThrow(() -> new fileNotFoundException("file with id : " + id + "is not valid"));
        return uploadFile;
    }


    public void deleteByFileId(Long id) throws NotFoundException {

        fileRepository.deleteById(id);

    }

    public UploadFile getFile(Long id){
        UploadFile uploadFile = fileRepository.getById(id);
        return uploadFile;
    }


    public static class fileNotFoundException extends NotFoundException {
        public fileNotFoundException(String msg) {
            super(msg);
        }
    }

    public static class ArgumentNullException extends Throwable {
        public ArgumentNullException(String s) {
        }
    }

    public static class InvalidArgumentException extends Throwable {
        public InvalidArgumentException(String s) {
        }
    }
}
