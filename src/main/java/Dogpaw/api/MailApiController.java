package Dogpaw.api;

import Dogpaw.domain.Mail;
import Dogpaw.dto.MailDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.MailService;
import Dogpaw.service.WorkspaceService;
import Dogpaw.service.exception.exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MailApiController {

    @NonNull
    private final MailService mailService;

    @NonNull
    private final WorkspaceService workspaceService;

    @PostMapping("/mail")
    public ResponseDTO.Create sendMail(@RequestBody MailDTO.Create dto) throws exception.InvalidArgumentException, exception.ArgumentNullException {
        Mail mail = new Mail(dto.getAddress(), dto.getTitle(), dto.getMessage());
        Long saveId = mailService.saveMail(mail);
        mailService.mailSend(dto);

        return new ResponseDTO.Create(saveId, true);
    }

    @PostMapping("/mail/user")
    public ResponseDTO.Update addUser(@RequestBody Long userId, Long channelId) throws exception.DogpawNotFoundException {
        workspaceService.addUser(userId, channelId);
        return new ResponseDTO.Update(true);
    }

    @PutMapping("/mail")
    public ResponseDTO.Delete checkValidation(@RequestBody MailDTO.Check dto) throws exception.DogpawNotFoundException {
        mailService.checkValidation(dto.getId());
        return new ResponseDTO.Delete(true);
    }
}