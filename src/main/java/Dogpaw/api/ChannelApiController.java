package Dogpaw.api;


import Dogpaw.domain.*;
import Dogpaw.domain.chatting.Chatting;
import Dogpaw.domain.idea.IdeaBoard;
import Dogpaw.dto.ChannelDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.*;
import Dogpaw.service.chatting.ChattingService;
import Dogpaw.service.exception.exception;
import Dogpaw.service.idea.IdeaBoardService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ChannelApiController {
    @NonNull
    private final ChannelService channelService;
    @NonNull
    private final ChattingService chattingService;
    @NonNull
    private final IdeaBoardService ideaBoardService;
    @NonNull
    private final WorkspaceService workspaceService;

    @PostMapping("/channel")
    public ResponseDTO.Create createChannel(@RequestBody ChannelDTO.Create dto) throws exception.InvalidArgumentException, exception.ArgumentNullException, exception.DogpawNotFoundException {

        Chatting chatting = new Chatting();
        IdeaBoard ideaBoard = new IdeaBoard();
        Long saveId2 = chattingService.saveChatting(chatting);
        Long saveId3 = ideaBoardService.saveIdeaBoard(ideaBoard);
        Workspace workspace = workspaceService.findOne(dto.getWorkspaceId());

        Channel channel = new Channel(dto.getName(), dto.getPurpose(),chatting, ideaBoard, workspace);

        Long saveId = channelService.saveChannel(channel, dto.getUserId());

        workspaceService.addChannel(dto.getWorkspaceId(), channel);

        return new ResponseDTO.Create(saveId, true);
    }

    @DeleteMapping("/channel")
    public ResponseDTO.Delete deleteChannel(@RequestParam Long id) throws exception.DogpawNotFoundException {
        channelService.deleteByChannelId(id);
        chattingService.deleteByChattingId(id);
        ideaBoardService.deleteByIdeaBoardId(id);

        return new ResponseDTO.Delete(true);
    }

    @GetMapping("/channel")
    public ResponseDTO.ChannelResponse getChannel(@RequestParam Long channelId) throws exception.DogpawNotFoundException{
        Channel channel = channelService.findOne(channelId);
        return new ResponseDTO.ChannelResponse(true, channel);

    }
}
