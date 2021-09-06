package Dogpaw.api.chatting;

import Dogpaw.domain.chatting.Chat;
import Dogpaw.domain.chatting.Chatting;
import Dogpaw.domain.User;
import Dogpaw.dto.chatting.ChatDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.UserService;
import Dogpaw.service.chatting.ChatService;
import Dogpaw.service.chatting.ChattingService;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChatApiController {
    @NonNull
    private final ChatService chatService;
    private final ChattingService chattingService;
    private final UserService userService;


    @PostMapping("/chat")
    public ResponseDTO.Create createChat(@RequestBody ChatDTO.Create dto) throws exception.ArgumentNullException, exception.InvalidArgumentException, exception.DogpawNotFoundException {
        Chatting chatting = chattingService.findOne(dto.getChattingId());
        User user = userService.findOne(dto.getUserId());
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        Chat chat = new Chat(user, dto.getText(),date, time, chatting);

        Long saveId = chatService.saveChat(chat);
        return new ResponseDTO.Create(saveId, true);
    }


    @DeleteMapping("/chat")
    public ResponseDTO.Delete createChat(@RequestBody ChatDTO.Delete dto) throws exception.DogpawNotFoundException {
        chatService.deleteByChatId(dto.getId());
        return new ResponseDTO.Delete(true);
    }
}
