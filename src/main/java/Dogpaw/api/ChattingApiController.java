package Dogpaw.api;

import Dogpaw.domain.ChatMapping;
import Dogpaw.domain.Chatting;
import Dogpaw.dto.ChattingDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.ChatService;
import Dogpaw.service.ChattingService;
import javassist.NotFoundException;
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
    public ResponseDTO.ChattingResponse getChatting(@RequestParam Long chattingId) throws NotFoundException{
        Chatting chatting = chattingService.findOne(chattingId);
        List<ChatMapping> chatList = chatService.getChatList(chattingId);
        return new ResponseDTO.ChattingResponse(true, chatting, chatList);
    }

}
