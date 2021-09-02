package Dogpaw.service.idea;



import Dogpaw.domain.idea.Idea;
import Dogpaw.domain.idea.IdeaMapping;
import Dogpaw.domain.idea.IdeaBoard;
import Dogpaw.repository.idea.IdeaRepository;
import Dogpaw.repository.idea.IdeaBoardRepository;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class IdeaService {

    @NonNull
    private final IdeaRepository ideaRepository;
    private final IdeaBoardRepository ideaBoardRepository;

    public Long saveIdea (Idea idea) throws exception.ArgumentNullException, exception.InvalidArgumentException {
        if(idea == null){
            throw new exception.ArgumentNullException("Idea can't be null");
        }
        if(idea.getText().isEmpty()){
            throw new exception.InvalidArgumentException("IdeaBoard text is null");

        }
        Idea save = ideaRepository.save(idea);

        return save.getId();
    }

    public void deleteByIdeaId(Long id) throws exception.DogpawNotFoundException{
        ideaRepository.deleteById(id);
    }

    public Optional<Idea> checkIdea(Long id) {
        Optional<Idea> idea = ideaRepository.findById(id);
        return idea;
    }

    public Idea findOne(Long id) throws exception.DogpawNotFoundException{
        Idea idea = ideaRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("Work space with id : " + id + "is not valid"));
        return idea;
    }

    public List<IdeaMapping> getIdeaList(Long id) throws exception.DogpawNotFoundException{
        IdeaBoard ideaBoard = ideaBoardRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("IdeaBoard with id : "+ id + "is not valid"));
        return ideaRepository.findAllByIdeaBoard(ideaBoard);
    }

}