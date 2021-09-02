package Dogpaw.api;

import Dogpaw.domain.message.MessageMapping;
import Dogpaw.domain.message.MessageRoom;
import Dogpaw.dto.message.MessageRoomDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.exception.exception;
import Dogpaw.service.message.MessageRoomService;
import Dogpaw.service.message.MessageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MessageRoomApiController {

    @NonNull
    private final MessageRoomService messageRoomService;
    private final MessageService messageService;

    @PostMapping("/messageroom")
    public ResponseDTO.Create createMessageRoom (@RequestBody MessageRoomDTO.Create dto) throws exception.ArgumentNullException, exception.InvalidArgumentException{
        MessageRoom messageRoom = new MessageRoom(dto.getUserId());

        Long saveId = messageRoomService.saveMessageRoom(messageRoom);

        return new ResponseDTO.Create(saveId, true);
    }

    @DeleteMapping("/messageroom")
    public ResponseDTO.Delete deleteMessageRoom(@RequestBody MessageRoomDTO.Delete dto) throws exception.DogpawNotFoundException {
        messageRoomService.deleteByMessageRoomId(dto.getId());
        return new ResponseDTO.Delete(true);
    }

    @GetMapping("/messageroom")
    public ResponseDTO.MessageRoomResponse getMessageRoom(@RequestParam Long messageRoomId) throws exception.DogpawNotFoundException {
        MessageRoom messageRoom = messageRoomService.findOne(messageRoomId);
        List<MessageMapping> messageList = messageService.getMessageList(messageRoomId);
        return new ResponseDTO.MessageRoomResponse(true, messageList, messageRoom);
    }

}
