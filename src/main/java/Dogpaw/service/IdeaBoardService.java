package Dogpaw.service;

import Dogpaw.domain.IdeaBoard;
import Dogpaw.repository.IdeaBoardRepository;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IdeaBoardService {
    @NonNull
    private final IdeaBoardRepository ideaBoardRepository;

    public Long saveIdeaBoard (IdeaBoard ideaBoard) throws ArgumentNullException, InvalidArgumentException{
        if(ideaBoard == null){
            throw new ArgumentNullException("IdeaBoard can't be null");
        }
        IdeaBoard save = ideaBoardRepository.save(ideaBoard);

        return save.getId();
    }

    public void deleteByIdeaBoardId(Long id) throws NotFoundException{
        ideaBoardRepository.deleteById(id);
    }

    public IdeaBoard findOne(Long id) throws NotFoundException{
        IdeaBoard IdeaBoard = ideaBoardRepository.findById(id).orElseThrow(() -> new IdeaBoardNotFoundException("IdeaBoard with id : "+ id + "is not valid"));
        return IdeaBoard;
    }


// exception

    public static class IdeaBoardNotFoundException extends NotFoundException {
        public IdeaBoardNotFoundException(String msg) {
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