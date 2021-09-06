package Dogpaw.api.message;

import Dogpaw.domain.message.Message;
import Dogpaw.domain.message.MessageRoom;
import Dogpaw.dto.message.MessageDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.exception.exception;
import Dogpaw.service.message.MessageRoomService;
import Dogpaw.service.message.MessageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MessageApiController {

    @NonNull
    private final MessageService messageService;
    private final MessageRoomService messageRoomService;

    @PostMapping("/message")
    public ResponseDTO.Create createMessage(@RequestBody MessageDTO.Create dto) throws exception.ArgumentNullException, exception.InvalidArgumentException, exception.DogpawNotFoundException {
        MessageRoom messageRoom = messageRoomService.findOne(dto.getMessageRoomId());

        Message message = new Message(dto.getSendBy(), dto.getText(), messageRoom);

        Long saveId = messageService.saveMessage(message);
        return new ResponseDTO.Create(saveId, true);
    }

    // ** 업데이트 추가 **
    @PutMapping("/message")
    public ResponseDTO.Update updateMessage(@RequestBody MessageDTO.Update dto) throws exception.DogpawNotFoundException {
        messageService.updateByMessageId(dto.getId(), dto.getText());
        return new ResponseDTO.Update(true);
    }

    @DeleteMapping("/message")
    public ResponseDTO.Delete deleteMessage(@RequestParam Long messageId) throws exception.DogpawNotFoundException {
        messageService.deleteByMessageId(messageId);
        return new ResponseDTO.Delete(true);
    }
}
