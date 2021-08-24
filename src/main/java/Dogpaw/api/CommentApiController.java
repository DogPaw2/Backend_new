package Dogpaw.api;

import Dogpaw.domain.Chat;
import Dogpaw.domain.Comment;
import Dogpaw.domain.User;
import Dogpaw.dto.ChatDTO;
import Dogpaw.dto.CommentDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.ChatService;
import Dogpaw.service.CommentService;
import Dogpaw.service.UserService;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/comment")
    public ResponseDTO.Create createComment(@RequestBody CommentDTO.Create dto) throws NotFoundException, CommentService.InvalidArgumentException, CommentService.ArgumentNullException {
        Chat chat = chatService.findOne(dto.getChatId());
        User user = userService.findOne(dto.getUserId());

        Comment comment = new Comment(user, dto.getText(),dto.getDate(), dto.getTime(), chat);

        Long saveId = commentService.saveComment(comment);
        return new ResponseDTO.Create(saveId, true);
    }


    @DeleteMapping("/comment")
    public ResponseDTO.Delete deleteComment(@RequestBody CommentDTO.Delete dto) throws NotFoundException {
        commentService.deleteByCommentId(dto.getId());
        return new ResponseDTO.Delete(true);
    }



}
