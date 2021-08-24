package Dogpaw.api;

import Dogpaw.domain.Message;
import Dogpaw.domain.MessageRoom;
import Dogpaw.dto.MessageDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.MessageRoomService;
import Dogpaw.service.MessageService;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MessageApiController {

    @NonNull
    private final MessageService messageService;
    private final MessageRoomService messageRoomService;

    @PostMapping("/message")
    public ResponseDTO.Create createMessage(@RequestBody MessageDTO.Create dto) throws MessageService.ArgumentNullException, MessageService.InvalidArgumentException, NotFoundException {
        MessageRoom messageRoom = messageRoomService.findOne(dto.getMessageRoomId());

        Message message = new Message(dto.getSendBy(), dto.getText(), messageRoom);

        Long saveId = messageService.saveMessage(message);
        return new ResponseDTO.Create(saveId, true);
    }

    // ** 업데이트 추가 **
    @PutMapping("/message")
    public ResponseDTO.Update updateMessage(@RequestBody MessageDTO.Update dto) throws NotFoundException {
        messageService.updateByMessageId(dto.getId(), dto.getText());
        return new ResponseDTO.Update(true);
    }

    @DeleteMapping("/message")
    public ResponseDTO.Delete deleteMessage(@RequestBody MessageDTO.Delete dto) throws NotFoundException {
        messageService.deleteByMessageId(dto.getId());
        return new ResponseDTO.Delete(true);
    }
}
