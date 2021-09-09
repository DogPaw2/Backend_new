package Dogpaw.api.message;

import Dogpaw.domain.User;
import Dogpaw.domain.message.Message;
import Dogpaw.domain.message.MessageComment;
import Dogpaw.domain.message.MessageCommentFile;
import Dogpaw.dto.CommentDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.UserService;
import Dogpaw.service.exception.exception;
import Dogpaw.service.message.MessageCommentFileService;
import Dogpaw.service.message.MessageCommentService;
import Dogpaw.service.message.MessageService;
import Dogpaw.util.MD5Generator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MessageCommentApiController {
    @NonNull
    private final MessageService messageService;
    @NonNull
    private final MessageCommentService messageCommentService;
    @NonNull
    private final UserService userService;
    @NonNull
    private final MessageCommentFileService fileService;


    @PostMapping("/message/comment")
    public ResponseDTO.Create createMessageComment(@RequestPart CommentDTO.Create dto,  @RequestPart(value = "files") MultipartFile[] files) throws exception.DogpawNotFoundException, exception.InvalidArgumentException, exception.ArgumentNullException, IOException, NoSuchAlgorithmException {
        User user = userService.findOne(dto.getUserId());
        Message message = messageService.findOne(dto.getMessageId());

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        MessageComment comment = new MessageComment(user, dto.getText(), date, time, message);
        Long saveId = messageCommentService.saveComment(comment);

        if(!files[0].isEmpty()) {
            for(MultipartFile file : files) {
                String originFileName = file.getOriginalFilename();
                String fileName = new MD5Generator(originFileName).toString();
                String contentType = file.getContentType();
                Long fileSize = file.getSize();
                String savePath = System.getProperty("user.dir") + "/messageCommentFiles";

                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + fileName;
                file.transferTo(new File(filePath));

                MessageCommentFile messageCommentFile = new MessageCommentFile(originFileName, fileName, contentType, fileSize, filePath, comment);
                fileService.saveFile(messageCommentFile);
            }
        }


        return new ResponseDTO.Create(saveId, true);
    }

    @PutMapping("/message/comment")
    public ResponseDTO.Update updateMessageComment(@RequestPart(value = "dto") CommentDTO.Update dto, @RequestPart(value = "files") MultipartFile[] files) throws exception.DogpawNotFoundException, IOException, NoSuchAlgorithmException, exception.InvalidArgumentException, exception.ArgumentNullException {
        messageCommentService.updateByCommentId(dto.getId(), dto.getText());

        if(!files[0].isEmpty()) {
            for(MultipartFile file : files) {
                String originFileName = file.getOriginalFilename();
                String fileName = new MD5Generator(originFileName).toString();
                String contentType = file.getContentType();
                Long fileSize = file.getSize();
                String savePath = System.getProperty("user.dir") + "/messageCommentFiles";

                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + fileName;
                file.transferTo(new File(filePath));

                MessageCommentFile messageCommentFile = new MessageCommentFile(originFileName, fileName, contentType, fileSize, filePath, messageCommentService.findOne(dto.getId()));
                fileService.saveFile(messageCommentFile);
            }
        }

        return new ResponseDTO.Update(true);
    }

    @GetMapping("/message/comment/download")
    public ResponseEntity<Resource> fileDownload(@RequestParam Long fileId) throws IOException {
        MessageCommentFile file = fileService.getFile(fileId);
        Path path = Paths.get(file.getPath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginName() + "\"")
                .body(resource);
    }
    
    @DeleteMapping("/message/comment")
    public ResponseDTO.Delete deleteMessageComment(@RequestParam Long id) throws exception.DogpawNotFoundException {
        messageCommentService.deleteByCommentId(id);
        return new ResponseDTO.Delete(true);
    }

    @DeleteMapping("/message/comment/file")
    public ResponseDTO.Delete deleteFile(@RequestParam Long fileId) throws exception.DogpawNotFoundException {
        fileService.deleteByFileId(fileId);
        return new ResponseDTO.Delete(true);
    }
}
