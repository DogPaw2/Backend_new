package Dogpaw.api;

import Dogpaw.domain.Idea;
import Dogpaw.domain.IdeaBoard;
import Dogpaw.domain.User;
import Dogpaw.dto.IdeaDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.IdeaService;
import Dogpaw.service.IdeaBoardService;
import Dogpaw.service.UserService;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class IdeaApiController {
    @NonNull
    private final IdeaService IdeaService;
    private final IdeaBoardService IdeaBoardService;
    private final UserService userService;


    @PostMapping("/idea")
    public ResponseDTO.Create createIdea(@RequestBody IdeaDTO.Create dto) throws IdeaService.ArgumentNullException, IdeaService.InvalidArgumentException, NotFoundException {
        IdeaBoard ideaBoard = IdeaBoardService.findOne(dto.getIdeaBoardId());
        User user = userService.findOne(dto.getUserId());

        Idea Idea = new Idea(user, dto.getText(),dto.getDate(), dto.getTime(), ideaBoard);

        Long saveId = IdeaService.saveIdea(Idea);
        return new ResponseDTO.Create(saveId, true);
    }


    @DeleteMapping("/idea")
    public ResponseDTO.Delete createIdea(@RequestBody IdeaDTO.Delete dto) throws NotFoundException {
        IdeaService.deleteByIdeaId(dto.getId());
        return new ResponseDTO.Delete(true);
    }
}
