package Dogpaw.api;


import Dogpaw.domain.*;
import Dogpaw.dto.ChannelDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.dto.WorkSpaceDTO;
import Dogpaw.service.*;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class WorkspaceApiController {
    @NonNull
    private final WorkspaceService workspaceService;
    @NonNull
    private final UserService userService;


    @PostMapping("/workspace")
    public ResponseDTO.Create createWorkspace(@RequestBody WorkSpaceDTO.Create dto) throws WorkspaceService.ArgumentNullException, WorkspaceService.InvalidArgumentException, IdeaBoardService.ArgumentNullException, ChattingService.InvalidArgumentException, IdeaBoardService.InvalidArgumentException, ChannelService.InvalidArgumentException, ChattingService.ArgumentNullException, UserService.UserNotFoundException, ChannelService.ArgumentNullException {
        Workspace workspace = new Workspace(dto.getName(), dto.getUrl());
        Long saveId = workspaceService.saveWorkSpace(workspace, dto.getUserId());

        return new ResponseDTO.Create(saveId, true);
    }

    @DeleteMapping("/workspace")
    public ResponseDTO.Delete deleteWorkspace(@RequestBody WorkSpaceDTO.Delete dto) throws NotFoundException {
        workspaceService.deleteByWorkSpaceId(dto.getId());
        return new ResponseDTO.Delete(true);
    }

    @GetMapping("/workspace/all")
    public ResponseDTO.WorkspaceAllResponse getAllWorkspace(@RequestParam Long userId) throws NotFoundException{
        User user = userService.findOne(userId);
        List<UserWorkspace> workspaceList = workspaceService.getWorkspaceList(userId);
        return new ResponseDTO.WorkspaceAllResponse(true, workspaceList);
    }

    @GetMapping("/workspace")
    public ResponseDTO.WorkspaceResponse getWorkspace(@RequestParam Long workspaceId) throws NotFoundException{
        Workspace workspace = workspaceService.findOne(workspaceId);
        return new ResponseDTO.WorkspaceResponse(true, workspace);
    }
}
