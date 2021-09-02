package Dogpaw.service;


import Dogpaw.domain.Idea;
import Dogpaw.domain.IdeaComment;
import Dogpaw.repository.IdeaRepository;
import Dogpaw.repository.IdeaCommentRepository;
import javassist.NotFoundException;
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

    public Long saveComment (IdeaComment comment) throws ArgumentNullException, InvalidArgumentException {
        if(comment == null){
            throw new ArgumentNullException("Comment can't be null");
        }
        if(comment.getText().isEmpty()){
            throw new InvalidArgumentException("Comment text is null");
        }
        comment.getIdea().getComments().add(comment);
        IdeaComment save = commentRepository.save(comment);

        return save.getId();
    }

    public void deleteByCommentId(Long id) throws NotFoundException{
        commentRepository.deleteById(id);
    }

    public IdeaComment findOne(Long id) throws NotFoundException{
        IdeaComment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment with id : " + id + "is not valid"));
        return comment;
    }


    // exception

    public static class CommentNotFoundException extends NotFoundException {
        public CommentNotFoundException(String msg) {
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


