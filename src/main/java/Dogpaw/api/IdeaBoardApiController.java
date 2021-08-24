package Dogpaw.api;

import Dogpaw.domain.IdeaMapping;
import Dogpaw.domain.IdeaBoard;
import Dogpaw.dto.IdeaBoardDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.IdeaService;
import Dogpaw.service.IdeaBoardService;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class IdeaBoardApiController {
    @NonNull
    private final IdeaBoardService IdeaBoardService;
    private final IdeaService IdeaService;


    @GetMapping("/ideaBoard")
    public ResponseDTO.IdeaBoardResponse getIdeaBoard(@RequestBody IdeaBoardDTO.Get dto) throws NotFoundException{
        IdeaBoard IdeaBoard = IdeaBoardService.findOne(dto.getId());
        List<IdeaMapping> IdeaList = IdeaService.getIdeaList(dto.getId());
        return new ResponseDTO.IdeaBoardResponse(true, IdeaBoard, IdeaList);

    }

}
