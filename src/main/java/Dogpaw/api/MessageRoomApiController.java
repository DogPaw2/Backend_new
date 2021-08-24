package Dogpaw.api;

import Dogpaw.domain.MessageMapping;
import Dogpaw.domain.MessageRoom;
import Dogpaw.dto.MessageRoomDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.MessageRoomService;
import Dogpaw.service.MessageService;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MessageRoomApiController {

    @NonNull
    private final MessageRoomService messageRoomService;
    private final MessageService messageService;

    @PostMapping("/messageroom")
    public ResponseDTO.Create createMessageRoom (@RequestBody MessageRoomDTO.Create dto) throws MessageRoomService.ArgumentNullException, MessageRoomService.InvalidArgumentException{
        MessageRoom messageRoom = new MessageRoom(dto.getUserId());

        Long saveId = messageRoomService.saveMessageRoom(messageRoom);

        return new ResponseDTO.Create(saveId, true);
    }

    @DeleteMapping("/messageroom")
    public ResponseDTO.Delete deleteMessageRoom(@RequestBody MessageRoomDTO.Delete dto) throws NotFoundException {
        messageRoomService.deleteByMessageRoomId(dto.getId());
        return new ResponseDTO.Delete(true);
    }

    @GetMapping("/messageroom")
    public ResponseDTO.MessageRoomResponse getMessageRoom(@RequestBody MessageRoomDTO.Get dto) throws NotFoundException {
        List<MessageMapping> messageList = messageService.getMessageList(dto.getId());
        MessageRoom messageRoom = messageRoomService.findOne(dto.getId());
        return new ResponseDTO.MessageRoomResponse(true, messageList, messageRoom);
    }

}
