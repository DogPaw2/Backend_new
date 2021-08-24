package Dogpaw.service;



import Dogpaw.domain.Idea;
import Dogpaw.domain.IdeaMapping;
import Dogpaw.domain.IdeaBoard;
import Dogpaw.repository.IdeaRepository;
import Dogpaw.repository.IdeaBoardRepository;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class IdeaService {

    @NonNull
    private final IdeaRepository ideaRepository;
    private final IdeaBoardRepository ideaBoardRepository;

    public Long saveIdea (Idea idea) throws ArgumentNullException, InvalidArgumentException {
        if(idea == null){
            throw new ArgumentNullException("Idea can't be null");
        }
        if(idea.getText().isEmpty()){
            throw new InvalidArgumentException("IdeaBoard text is null");

        }
        Idea save = ideaRepository.save(idea);

        return save.getId();
    }

    public void deleteByIdeaId(Long id) throws NotFoundException{
        ideaRepository.deleteById(id);
    }

    public Idea findOne(Long id) throws NotFoundException{
        Idea idea = ideaRepository.findById(id).orElseThrow(() -> new IdeaNotFoundException("Work space with id : " + id + "is not valid"));
        return idea;
    }

    public List<IdeaMapping> getIdeaList(Long id) throws NotFoundException{
        IdeaBoard ideaBoard = ideaBoardRepository.findById(id).orElseThrow(() -> new IdeaBoardService.IdeaBoardNotFoundException("IdeaBoard with id : "+ id + "is not valid"));
        return ideaRepository.findAllByIdeaBoard(ideaBoard);
    }



    // exception

    public static class IdeaNotFoundException extends NotFoundException {
        public IdeaNotFoundException(String msg) {
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