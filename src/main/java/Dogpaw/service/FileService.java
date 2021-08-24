package Dogpaw.service;

import Dogpaw.domain.File;
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

    public Long saveFile (File file) throws ArgumentNullException, InvalidArgumentException {
        //fail fast pattern
        //if Argument is invalid, dont do any logic
        if(file == null){
            throw new ArgumentNullException("file can't be null");
        }
        if(file.getFileName().isEmpty() || file.getContentType().isEmpty()){
            throw new InvalidArgumentException("file name or content type is null");
        }
        File save = fileRepository.save(file);

        return save.getId();

    }

    public File findOne(Long id) throws NotFoundException {
        File file = fileRepository.findById(id).orElseThrow(() -> new fileNotFoundException("file with id : " + id + "is not valid"));
        return file;
    }


    public void deleteByFileId(Long id) throws NotFoundException {

        fileRepository.deleteById(id);

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
