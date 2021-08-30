package Dogpaw.api;

import Dogpaw.domain.Chat;
import Dogpaw.domain.Comment;
import Dogpaw.domain.Message;
import Dogpaw.domain.User;
import Dogpaw.dto.CommentDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.ChatService;
import Dogpaw.service.CommentService;
import Dogpaw.service.MessageService;
import Dogpaw.service.UserService;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentApiController {
    @NonNull
    private final ChatService chatService;
    @NonNull
    private final CommentService commentService;
    @NonNull
    private final UserService userService;
    @NonNull
    private final MessageService messageService;

    @PostMapping("/comment")
    public ResponseDTO.Create createComment(@RequestBody CommentDTO.Create dto) throws NotFoundException, CommentService.InvalidArgumentException, CommentService.ArgumentNullException {
        User user = userService.findOne(dto.getUserId());
        Optional<Chat> chat = chatService.checkChat(dto.getChatId());
        Optional<Message> message = messageService.checkMessage(dto.getMessageId());
        Long saveId = null;

        Comment comment = new Comment(user, dto.getText(),dto.getDate(), dto.getTime(), chat, message);
        saveId = commentService.saveComment(comment);

        return new ResponseDTO.Create(saveId, true);
    }


    @DeleteMapping("/comment")
    public ResponseDTO.Delete deleteComment(@RequestBody CommentDTO.Delete dto) throws NotFoundException {
        commentService.deleteByCommentId(dto.getId());
        return new ResponseDTO.Delete(true);
    }



}
