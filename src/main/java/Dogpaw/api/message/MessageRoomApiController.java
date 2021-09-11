package Dogpaw.api.message;

import Dogpaw.domain.Workspace;
import Dogpaw.domain.message.MessageMapping;
import Dogpaw.domain.message.MessageRoom;
import Dogpaw.domain.message.UserMessageRoom;
import Dogpaw.dto.message.MessageRoomDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.WorkspaceService;
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
    @NonNull
    private final MessageService messageService;
    @NonNull
    private final WorkspaceService workspaceService;

    @PostMapping("/messageroom")
    public ResponseDTO.Create createMessageRoom (@RequestBody MessageRoomDTO.Create dto) throws exception.ArgumentNullException, exception.DogpawNotFoundException {
        Workspace workspace = workspaceService.findOne(dto.getWorkspaceId());
        MessageRoom messageRoom = new MessageRoom(workspace);

        Long saveId = messageRoomService.saveMessageRoom(messageRoom, dto.getUserId1(), dto.getUserId2());

        workspaceService.addMessageRoom(dto.getWorkspaceId(), messageRoom);

        return new ResponseDTO.Create(saveId, true);
    }

    @DeleteMapping("/messageroom")
    public ResponseDTO.Delete deleteMessageRoom(@RequestParam Long messageRoomId) throws exception.DogpawNotFoundException {
        messageRoomService.deleteByMessageRoomId(messageRoomId);
        return new ResponseDTO.Delete(true);
    }

    @GetMapping("/messageroom")
    public ResponseDTO.MessageRoomResponse getMessageRoom(@RequestParam Long messageRoomId) throws exception.DogpawNotFoundException {
        MessageRoom messageRoom = messageRoomService.findOne(messageRoomId);
        List<MessageMapping> messageList = messageService.getMessageList(messageRoomId);
        return new ResponseDTO.MessageRoomResponse(true, messageList, messageRoom);
    }

    @GetMapping("/messageroomall")
    public ResponseDTO.MessageRoomAllResponse getAllMessageRoom(@RequestParam Long userId) {
        List<UserMessageRoom> userMessageRoomList = messageRoomService.getMessageRoomList(userId);
        return new ResponseDTO.MessageRoomAllResponse(true, userMessageRoomList);
    }
}
