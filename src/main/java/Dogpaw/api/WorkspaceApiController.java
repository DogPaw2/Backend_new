package Dogpaw.api;


import Dogpaw.domain.Workspace;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.dto.WorkSpaceDTO;
import Dogpaw.service.WorkspaceService;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WorkspaceApiController {

    @NonNull
    private final WorkspaceService workspaceService;

    @PostMapping("/workspace")
    public ResponseDTO.Create createWorkspace(@RequestBody WorkSpaceDTO.Create dto) throws WorkspaceService.ArgumentNullException, WorkspaceService.InvalidArgumentException {
        Workspace workspace = new Workspace(dto.getName(), dto.getUrl());

        Long saveId = workspaceService.saveWorkSpace(workspace);

        return new ResponseDTO.Create(saveId, true);
    }

    @DeleteMapping("/workspace")
    public ResponseDTO.Delete deleteWorkspace(@RequestBody WorkSpaceDTO.Delete dto) throws NotFoundException {
        workspaceService.deleteByWorkSpaceId(dto.getId());
        return new ResponseDTO.Delete(true);
    }
}
