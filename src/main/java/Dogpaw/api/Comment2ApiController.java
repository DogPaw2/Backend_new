package Dogpaw.api;

import Dogpaw.domain.*;
import Dogpaw.dto.Comment2DTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.*;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class Comment2ApiController {
    @NonNull
    private final MessageService messageService;
    @NonNull
    private final Comment2Service comment2Service;
    @NonNull
    private final UserService userService;


    @PostMapping("/comment2")
    public ResponseDTO.Create createComment(@RequestBody Comment2DTO.Create dto) throws NotFoundException, Comment2Service.InvalidArgumentException, Comment2Service.ArgumentNullException {
        Message message = messageService.findOne(dto.getMessageId());
        User user = userService.findOne(dto.getUserId());

        Comment2 comment2 = new Comment2(user, dto.getText(), message);

        Long saveId = comment2Service.saveComment2(comment2);
        return new ResponseDTO.Create(saveId, true);
    }


    @DeleteMapping("/comment2")
    public ResponseDTO.Delete deleteComment2(@RequestBody Comment2DTO.Delete dto) throws NotFoundException {
        comment2Service.deleteByComment2Id(dto.getId());
        return new ResponseDTO.Delete(true);
    }



}
