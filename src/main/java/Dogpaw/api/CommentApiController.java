package Dogpaw.api;

import Dogpaw.domain.*;
import Dogpaw.dto.ChatDTO;
import Dogpaw.dto.CommentDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.*;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CommentApiController {
    @NonNull
    private final ChatService chatService;
    @NonNull
    private final IdeaService ideaService;
    @NonNull
    private final MessageService messageService;
    @NonNull
    private final CommentService commentService;
    @NonNull
    private final UserService userService;


    @PostMapping("/comment")
    public ResponseDTO.Create createComment(@RequestBody CommentDTO.Create dto) throws NotFoundException, CommentService.InvalidArgumentException, CommentService.ArgumentNullException {
        User user = userService.findOne(dto.getUserId());
        //Optional<Chat> chat = chatService.checkChat(dto.getChatId());
        //Optional<Idea> idea = ideaService.checkIdea(dto.getIdeaId());
        //Optional<Message> message = messageService.checkMessage(dto.getMessageId());
        Long saveId = null;

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        //Comment comment = new Comment(user, dto.getText(),date, time, chat, idea, message);
        //saveId = commentService.saveComment(comment);

        return new ResponseDTO.Create(saveId, true);
    }


    @DeleteMapping("/comment")
    public ResponseDTO.Delete deleteComment(@RequestBody CommentDTO.Delete dto) throws NotFoundException {
        commentService.deleteByCommentId(dto.getId());
        return new ResponseDTO.Delete(true);
    }
}
