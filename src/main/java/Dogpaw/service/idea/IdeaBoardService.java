package Dogpaw.service.idea;

import Dogpaw.domain.idea.IdeaBoard;
import Dogpaw.repository.idea.IdeaBoardRepository;
import Dogpaw.service.exception.exception;
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

    public Long saveIdeaBoard (IdeaBoard ideaBoard) throws exception.ArgumentNullException, exception.InvalidArgumentException{
        if(ideaBoard == null){
            throw new exception.ArgumentNullException("IdeaBoard can't be null");
        }
        IdeaBoard save = ideaBoardRepository.save(ideaBoard);

        return save.getId();
    }

    public void deleteByIdeaBoardId(Long id) throws exception.DogpawNotFoundException{
        ideaBoardRepository.deleteById(id);
    }

    public IdeaBoard findOne(Long id) throws exception.DogpawNotFoundException{
        IdeaBoard IdeaBoard = ideaBoardRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("IdeaBoard with id : "+ id + "is not valid"));
        return IdeaBoard;
    }

}