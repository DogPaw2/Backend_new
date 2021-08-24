package Dogpaw.api;

import Dogpaw.domain.Chat;
import Dogpaw.domain.Chatting;
import Dogpaw.domain.User;
import Dogpaw.dto.ChatDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.ChatService;
import Dogpaw.service.ChattingService;
import Dogpaw.service.UserService;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatApiController {
    @NonNull
    private final ChatService chatService;
    private final ChattingService chattingService;
    private final UserService userService;


    @PostMapping("/chat")
    public ResponseDTO.Create createChat(@RequestBody ChatDTO.Create dto) throws ChatService.ArgumentNullException, ChatService.InvalidArgumentException, NotFoundException {
        Chatting chatting = chattingService.findOne(dto.getChattingId());
        User user = userService.findOne(dto.getUserId());

        Chat chat = new Chat(user, dto.getText(),dto.getDate(), dto.getTime(), chatting);

        Long saveId = chatService.saveChat(chat);
        return new ResponseDTO.Create(saveId, true);
    }


    @DeleteMapping("/chat")
    public ResponseDTO.Delete createChat(@RequestBody ChatDTO.Delete dto) throws NotFoundException {
        chatService.deleteByChatId(dto.getId());
        return new ResponseDTO.Delete(true);
    }
}
