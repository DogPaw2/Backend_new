package Dogpaw.api.chatting;

import Dogpaw.domain.chatting.Chat;
import Dogpaw.domain.chatting.ChatFile;
import Dogpaw.domain.chatting.Chatting;
import Dogpaw.domain.User;
import Dogpaw.dto.chatting.ChatDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.UserService;
import Dogpaw.service.chatting.ChatFileService;
import Dogpaw.service.chatting.ChatService;
import Dogpaw.service.chatting.ChattingService;
import Dogpaw.service.exception.exception;
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
public class ChatApiController {
    @NonNull
    private final ChatService chatService;
    private final ChattingService chattingService;
    private final UserService userService;
    private final ChatFileService fileService;


    @PostMapping("/chat")
    public ResponseDTO.Create createChat(@RequestPart ChatDTO.Create dto, @RequestPart(value = "files") MultipartFile[] files) throws exception.ArgumentNullException, exception.InvalidArgumentException, exception.DogpawNotFoundException, IOException, NoSuchAlgorithmException {
        Chatting chatting = chattingService.findOne(dto.getChattingId());
        User user = userService.findOne(dto.getUserId());
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        Chat chat = new Chat(user, dto.getText(),date, time, chatting);
        Long saveId = chatService.saveChat(chat);

        if(!files[0].isEmpty()) {
            for(MultipartFile file : files) {
                String originFileName = file.getOriginalFilename();
                String fileName = new MD5Generator(originFileName).toString();
                String contentType = file.getContentType();
                Long fileSize = file.getSize();
                String savePath = System.getProperty("user.dir") + "/chatFiles";

                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + fileName;
                file.transferTo(new File(filePath));

                ChatFile chatFile = new ChatFile(originFileName, fileName, contentType, fileSize, filePath, chat);
                fileService.saveFile(chatFile);
            }
        }

        return new ResponseDTO.Create(saveId, true);
    }

    @PutMapping("/chat")
    public ResponseDTO.Update updatechat(@RequestPart(value = "dto") ChatDTO.Update dto, @RequestPart(value = "files") MultipartFile[] files) throws exception.DogpawNotFoundException, IOException, NoSuchAlgorithmException, exception.InvalidArgumentException, exception.ArgumentNullException {
        chatService.updateByChatId(dto.getId(), dto.getText());

        if (!files[0].isEmpty()) {
            for (MultipartFile file : files) {
                String originFileName = file.getOriginalFilename();
                String fileName = new MD5Generator(originFileName).toString();
                String contentType = file.getContentType();
                Long fileSize = file.getSize();
                String savePath = System.getProperty("user.dir") + "/chatFiles";

                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + fileName;
                file.transferTo(new File(filePath));

                ChatFile chatFile = new ChatFile(originFileName, fileName, contentType, fileSize, filePath, chatService.findOne(dto.getId()));
                fileService.saveFile(chatFile);
            }
        }

        return new ResponseDTO.Update(true);
    }
    
    @GetMapping("/chat/download")
    public ResponseEntity<Resource> fileDownload(@RequestParam Long fileId) throws IOException {
        ChatFile file = fileService.getFile(fileId);
        Path path = Paths.get(file.getPath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/chat")
    public ResponseDTO.Delete deleteChat(@RequestParam Long chatId) throws exception.DogpawNotFoundException {
        chatService.deleteByChatId(chatId);
        return new ResponseDTO.Delete(true);
    }

    @DeleteMapping("/chat/file")
    public ResponseDTO.Delete deleteFile(@RequestParam Long fileId) throws exception.DogpawNotFoundException {
        fileService.deleteByFileId(fileId);
        return new ResponseDTO.Delete(true);
    }
}