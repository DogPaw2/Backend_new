package Dogpaw.api;

import Dogpaw.domain.User;
import Dogpaw.dto.ChatDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.dto.UserDTO;
import Dogpaw.service.UserService;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {
    @NonNull
    private final UserService userService;

    @PostMapping("/user")
    public ResponseDTO.Create createUser(@RequestBody UserDTO.Create dto) throws UserService.InvalidArgumentException, UserService.ArgumentNullException {
        User user = new User(dto.getName());
        Long saveId = userService.saveUser(user);

        return new ResponseDTO.Create(saveId, true);
    }

    @DeleteMapping("/user")
    public ResponseDTO.Delete createChat(@RequestBody UserDTO.Delete dto) throws NotFoundException {
        userService.deleteByUserId(dto.getId());
        return new ResponseDTO.Delete(true);
    }
}
