package Dogpaw.api;

import Dogpaw.domain.Mail;
import Dogpaw.domain.User;
import Dogpaw.domain.UserWorkspace;
import Dogpaw.domain.Workspace;
import Dogpaw.dto.MailDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.repository.WorkspaceRepository;
import Dogpaw.service.MailService;
import Dogpaw.service.UserService;
import Dogpaw.service.WorkspaceService;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MailApiController {

    @NonNull
    private final MailService mailService;

    @NonNull
    private final WorkspaceService workspaceService;

    @NonNull
    private final UserService userService;

    @PostMapping("/mail")
    public ResponseDTO.Create sendMail(@RequestBody MailDTO.Create dto) throws exception.InvalidArgumentException, exception.ArgumentNullException {
        Mail mail = new Mail(dto.getAddress(), dto.getTitle(), dto.getMessage());
        Long saveId = mailService.saveMail(mail);
        mailService.mailSend(dto);

        return new ResponseDTO.Create(saveId, true);
    }

    @PostMapping("/mail/user")
    public ResponseDTO.Update addUser(@RequestParam(value="workspaceId") Long workspaceId, @RequestParam(value="userId") Long userId) throws exception.DogpawNotFoundException {
        User user = userService.findOne(userId);
        List<UserWorkspace> userWorkspaceList = workspaceService.getWorkspaceList(userId);

        for(UserWorkspace userWorkspace : userWorkspaceList) {
            if(userWorkspace.getUser() == user) {
                return new ResponseDTO.Update(false);
            }
        }
        workspaceService.addUser(userId, workspaceId);

        return new ResponseDTO.Update(true);
    }
}