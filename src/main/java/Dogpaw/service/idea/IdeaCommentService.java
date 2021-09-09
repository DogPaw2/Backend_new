package Dogpaw.service.idea;


import Dogpaw.domain.idea.IdeaComment;
import Dogpaw.repository.idea.IdeaRepository;
import Dogpaw.repository.idea.IdeaCommentRepository;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IdeaCommentService {
    @NonNull
    private final IdeaRepository ideaRepository;
    @NonNull
    private final IdeaCommentRepository commentRepository;

    public Long saveComment (IdeaComment comment) throws exception.ArgumentNullException, exception.InvalidArgumentException {
        if(comment == null){
            throw new exception.ArgumentNullException("Comment can't be null");
        }
        if(comment.getText().isEmpty()){
            throw new exception.InvalidArgumentException("Comment text is null");
        }
        comment.getIdea().getComments().add(comment);
        IdeaComment save = commentRepository.save(comment);

        return save.getId();
    }

    public void deleteByCommentId(Long id) throws exception.DogpawNotFoundException{
        commentRepository.deleteById(id);
    }

    public void updateByCommentId(Long id, String text) throws exception.DogpawNotFoundException {
        IdeaComment ideaComment = findOne(id);
        ideaComment.setText(text);
        commentRepository.save(ideaComment);
    }

    public IdeaComment findOne(Long id) throws exception.DogpawNotFoundException{
        IdeaComment comment = commentRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("Comment with id : " + id + "is not valid"));
        return comment;
    }

}


