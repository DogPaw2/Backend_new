package Dogpaw.api.chatting;

import Dogpaw.domain.*;
import Dogpaw.domain.chatting.Chat;
import Dogpaw.domain.chatting.ChatComment;
import Dogpaw.domain.chatting.ChatCommentFile;
import Dogpaw.domain.idea.IdeaFile;
import Dogpaw.dto.CommentDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.dto.idea.IdeaDTO;
import Dogpaw.service.*;
import Dogpaw.service.chatting.ChatCommentFileService;
import Dogpaw.service.chatting.ChatCommentService;
import Dogpaw.service.chatting.ChatService;
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
public class ChatCommentApiController {
    @NonNull
    private final ChatService chatService;
    @NonNull
    private final ChatCommentService chatCommentService;
    @NonNull
    private final UserService userService;
    @NonNull
    private final ChatCommentFileService fileService;


    @PostMapping("/chat/comment")
    public ResponseDTO.Create createChatComment(@RequestPart CommentDTO.Create dto,  @RequestPart(value = "files") MultipartFile[] files) throws exception.DogpawNotFoundException, exception.InvalidArgumentException, exception.ArgumentNullException, IOException, NoSuchAlgorithmException {
        User user = userService.findOne(dto.getUserId());
        Chat chat = chatService.findOne(dto.getChatId());

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        ChatComment comment = new ChatComment(user, dto.getText(), date, time, chat);
        Long saveId = chatCommentService.saveComment(comment);

        if(!files[0].isEmpty()) {
            for(MultipartFile file : files) {
                String originFileName = file.getOriginalFilename();
                String fileName = new MD5Generator(originFileName).toString();
                String contentType = file.getContentType();
                Long fileSize = file.getSize();
                String savePath = System.getProperty("user.dir") + "/chatCommentFiles";

                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + fileName;
                file.transferTo(new File(filePath));

                ChatCommentFile chatCommentFile = new ChatCommentFile(originFileName, fileName, contentType, fileSize, filePath, comment);
                fileService.saveFile(chatCommentFile);
            }
        }


        return new ResponseDTO.Create(saveId, true);
    }

    @PutMapping("/chat/comment")
    public ResponseDTO.Update updateIdea(@RequestPart(value = "dto") IdeaDTO.Update dto, @RequestPart(value = "files") MultipartFile[] files) throws exception.DogpawNotFoundException, IOException, NoSuchAlgorithmException, exception.InvalidArgumentException, exception.ArgumentNullException {
        chatCommentService.updateByCommentId(dto.getId(), dto.getText());

        if(!files[0].isEmpty()) {
            for(MultipartFile file : files) {
                String originFileName = file.getOriginalFilename();
                String fileName = new MD5Generator(originFileName).toString();
                String contentType = file.getContentType();
                Long fileSize = file.getSize();
                String savePath = System.getProperty("user.dir") + "/chatCommentFiles";

                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + fileName;
                file.transferTo(new File(filePath));

                ChatCommentFile chatCommentFile = new ChatCommentFile(originFileName, fileName, contentType, fileSize, filePath, chatCommentService.findOne(dto.getId()));
                fileService.saveFile(chatCommentFile);
            }
        }

        return new ResponseDTO.Update(true);
    }

    @GetMapping("/chat/comment/download")
    public ResponseEntity<Resource> fileDownload(@RequestParam Long fileId) throws IOException {
        ChatCommentFile file = fileService.getFile(fileId);
        Path path = Paths.get(file.getPath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/chat/comment")
    public ResponseDTO.Delete deleteChatComment(@RequestParam Long chatCommentId) throws exception.DogpawNotFoundException {
        chatCommentService.deleteByCommentId(chatCommentId);
        return new ResponseDTO.Delete(true);
    }

    @DeleteMapping("/chat/comment/file")
    public ResponseDTO.Delete deleteFile(@RequestParam Long fileId) throws exception.DogpawNotFoundException {
        fileService.deleteByFileId(fileId);
        return new ResponseDTO.Delete(true);
    }
}
