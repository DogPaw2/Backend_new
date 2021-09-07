package Dogpaw.api.message;

import Dogpaw.domain.message.Message;
import Dogpaw.domain.message.MessageFile;
import Dogpaw.domain.message.MessageRoom;
import Dogpaw.dto.message.MessageDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.exception.exception;
import Dogpaw.service.message.MessageFileService;
import Dogpaw.service.message.MessageRoomService;
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


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MessageApiController {

    @NonNull
    private final MessageService messageService;
    private final MessageRoomService messageRoomService;
    private final MessageFileService fileService;

    @PostMapping("/message")
    public ResponseDTO.Create createMessage(@RequestPart MessageDTO.Create dto,  @RequestPart(value = "files") MultipartFile[] files) throws exception.ArgumentNullException, exception.InvalidArgumentException, exception.DogpawNotFoundException, IOException, NoSuchAlgorithmException {
        MessageRoom messageRoom = messageRoomService.findOne(dto.getMessageRoomId());
        Message message = new Message(dto.getSendBy(), dto.getText(), messageRoom);
        Long saveId = messageService.saveMessage(message);

        if(!files[0].isEmpty()) {
            for(MultipartFile file : files) {
                String originFileName = file.getOriginalFilename();
                String fileName = new MD5Generator(originFileName).toString();
                String contentType = file.getContentType();
                Long fileSize = file.getSize();
                String savePath = System.getProperty("user.dir") + "/messageFiles";

                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + fileName;
                file.transferTo(new File(filePath));

                MessageFile messageFile = new MessageFile(originFileName, fileName, contentType, fileSize, filePath, message);
                fileService.saveFile(messageFile);
            }
        }

        return new ResponseDTO.Create(saveId, true);
    }

    // ** 업데이트 추가 **
    @PutMapping("/message")
    public ResponseDTO.Update updateMessage(@RequestPart(value = "dto") MessageDTO.Update dto, @RequestPart(value = "files") MultipartFile[] files) throws exception.DogpawNotFoundException, IOException, NoSuchAlgorithmException, exception.InvalidArgumentException, exception.ArgumentNullException {
        messageService.updateByMessageId(dto.getId(), dto.getText());

        if(!files[0].isEmpty()) {
            for(MultipartFile file : files) {
                String originFileName = file.getOriginalFilename();
                String fileName = new MD5Generator(originFileName).toString();
                String contentType = file.getContentType();
                Long fileSize = file.getSize();
                String savePath = System.getProperty("user.dir") + "/messageFiles";

                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + fileName;
                file.transferTo(new File(filePath));

                MessageFile messageFile = new MessageFile(originFileName, fileName, contentType, fileSize, filePath, messageService.findOne(dto.getId()));
                fileService.saveFile(messageFile);
            }
        }

        return new ResponseDTO.Update(true);
    }

    @GetMapping("/message/download")
    public ResponseEntity<Resource> fileDownload(@RequestParam Long fileId) throws IOException {
        MessageFile file = fileService.getFile(fileId);
        Path path = Paths.get(file.getPath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/message")
    public ResponseDTO.Delete deleteMessage(@RequestBody MessageDTO.Delete dto) throws exception.DogpawNotFoundException {
        messageService.deleteByMessageId(dto.getId());
        return new ResponseDTO.Delete(true);
    }

    @DeleteMapping("/message/file")
    public ResponseDTO.Delete deleteFile(@RequestParam Long fileId) throws exception.DogpawNotFoundException {
        fileService.deleteByFileId(fileId);
        return new ResponseDTO.Delete(true);
    }
}