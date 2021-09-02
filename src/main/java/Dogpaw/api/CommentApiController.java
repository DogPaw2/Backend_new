package Dogpaw.api;

import Dogpaw.domain.*;
import Dogpaw.dto.CommentDTO;
import Dogpaw.dto.IdeaCommentDTO;
import Dogpaw.dto.MessageCommentDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.*;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

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
    private final ChatCommentService chatCommentService;
    @NonNull
    private final IdeaCommentService ideaCommentService;
    @NonNull
    private final MessageCommentService messageCommentService;
    @NonNull
    private final UserService userService;


    @PostMapping("/chat/comment")
    public ResponseDTO.Create createChatComment(@RequestBody CommentDTO.Create dto) throws NotFoundException, ChatCommentService.InvalidArgumentException, ChatCommentService.ArgumentNullException {
        User user = userService.findOne(dto.getUserId());
        Chat chat = chatService.findOne(dto.getChatId());

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        ChatComment comment = new ChatComment(user, dto.getText(), date, time, chat);
        Long saveId = chatCommentService.saveComment(comment);

        return new ResponseDTO.Create(saveId, true);
    }

    @PostMapping("/idea/comment")
    public ResponseDTO.Create createIdeaComment(@RequestBody CommentDTO.Create dto) throws NotFoundException, IdeaCommentService.InvalidArgumentException, IdeaCommentService.ArgumentNullException {
        User user = userService.findOne(dto.getUserId());
        Idea idea = ideaService.findOne(dto.getIdeaId());

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        IdeaComment comment = new IdeaComment(user, dto.getText(), date, time, idea);
        Long saveId = ideaCommentService.saveComment(comment);

        return new ResponseDTO.Create(saveId, true);
    }

    @PostMapping("/message/comment")
    public ResponseDTO.Create createMessageComment(@RequestBody CommentDTO.Create dto) throws NotFoundException, MessageCommentService.InvalidArgumentException, MessageCommentService.ArgumentNullException {
        User user = userService.findOne(dto.getUserId());
        Message message = messageService.findOne(dto.getMessageId());

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        MessageComment comment = new MessageComment(user, dto.getText(), date, time, message);
        Long saveId = messageCommentService.saveComment(comment);

        return new ResponseDTO.Create(saveId, true);
    }


    @DeleteMapping("/chat/comment")
    public ResponseDTO.Delete deletChatComment(@RequestBody CommentDTO.Delete dto) throws NotFoundException {
        chatCommentService.deleteByCommentId(dto.getId());
        return new ResponseDTO.Delete(true);
    }

    @DeleteMapping("/idea/comment")
    public ResponseDTO.Delete deleteIdeaComment(@RequestBody CommentDTO.Delete dto) throws NotFoundException {
        ideaCommentService.deleteByCommentId(dto.getId());
        return new ResponseDTO.Delete(true);
    }

    @DeleteMapping("/message/comment")
    public ResponseDTO.Delete deleteMessageComment(@RequestBody CommentDTO.Delete dto) throws NotFoundException {
        messageCommentService.deleteByCommentId(dto.getId());
        return new ResponseDTO.Delete(true);
    }



}
