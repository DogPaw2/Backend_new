package Dogpaw.api;

import Dogpaw.domain.chatting.ChatMapping;
import Dogpaw.domain.chatting.Chatting;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.chatting.ChatService;
import Dogpaw.service.chatting.ChattingService;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChattingApiController {
    @NonNull
    private final ChattingService chattingService;
    private final ChatService chatService;


    @GetMapping("/chatting")
    public ResponseDTO.ChattingResponse getChatting(@RequestParam Long chattingId) throws exception.DogpawNotFoundException{
        Chatting chatting = chattingService.findOne(chattingId);
        List<ChatMapping> chatList = chatService.getChatList(chattingId);
        return new ResponseDTO.ChattingResponse(true, chatting, chatList);
    }

}
