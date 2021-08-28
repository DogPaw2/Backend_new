package Dogpaw.api;


import Dogpaw.domain.User;
import Dogpaw.domain.UserChannelMapping;
import Dogpaw.domain.UserWorkspace;
import Dogpaw.domain.Workspace;
import Dogpaw.dto.ChannelDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.dto.WorkSpaceDTO;
import Dogpaw.service.UserService;
import Dogpaw.service.WorkspaceService;
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
    public ResponseDTO.Create createWorkspace(@RequestBody WorkSpaceDTO.Create dto) throws WorkspaceService.ArgumentNullException, WorkspaceService.InvalidArgumentException {
        Workspace workspace = new Workspace(dto.getName(), dto.getUrl());

        Long saveId = workspaceService.saveWorkSpace(workspace, dto.getUserId());

        return new ResponseDTO.Create(saveId, true);
    }

    @DeleteMapping("/workspace")
    public ResponseDTO.Delete deleteWorkspace(@RequestBody WorkSpaceDTO.Delete dto) throws NotFoundException {
        workspaceService.deleteByWorkSpaceId(dto.getId());
        return new ResponseDTO.Delete(true);
    }

    @GetMapping("/workspace")
    public ResponseDTO.WorkspaceResponse getWorkspace(@RequestBody WorkSpaceDTO.Get dto) throws NotFoundException{
        User user = userService.findOne(dto.getUserId());
        List<UserWorkspace> workspaceList = workspaceService.getWorkspaceList(dto.getUserId());
        return new ResponseDTO.WorkspaceResponse(true, workspaceList);

    }
}
