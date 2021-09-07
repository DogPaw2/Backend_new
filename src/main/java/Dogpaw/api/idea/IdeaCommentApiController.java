package Dogpaw.api.idea;

import Dogpaw.domain.User;
import Dogpaw.domain.idea.Idea;
import Dogpaw.domain.idea.IdeaComment;
import Dogpaw.domain.idea.IdeaCommentFile;
import Dogpaw.domain.idea.IdeaFile;
import Dogpaw.dto.CommentDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.dto.idea.IdeaDTO;
import Dogpaw.service.UserService;
import Dogpaw.service.exception.exception;
import Dogpaw.service.idea.IdeaCommentFileService;
import Dogpaw.service.idea.IdeaCommentService;
import Dogpaw.service.idea.IdeaService;
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
public class IdeaCommentApiController {
    @NonNull
    private final IdeaService ideaService;
    @NonNull
    private final IdeaCommentService ideaCommentService;
    @NonNull
    private final UserService userService;
    @NonNull
    private final IdeaCommentFileService fileService;


    @PostMapping("/idea/comment")
    public ResponseDTO.Create createIdeaComment(@RequestPart CommentDTO.Create dto,  @RequestPart(value = "files") MultipartFile[] files) throws exception.DogpawNotFoundException, exception.InvalidArgumentException, exception.ArgumentNullException, IOException, NoSuchAlgorithmException {
        User user = userService.findOne(dto.getUserId());
        Idea idea = ideaService.findOne(dto.getIdeaId());

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        IdeaComment comment = new IdeaComment(user, dto.getText(), date, time, idea);
        Long saveId = ideaCommentService.saveComment(comment);

        if(!files[0].isEmpty()) {
            for(MultipartFile file : files) {
                String originFileName = file.getOriginalFilename();
                String fileName = new MD5Generator(originFileName).toString();
                String contentType = file.getContentType();
                Long fileSize = file.getSize();
                String savePath = System.getProperty("user.dir") + "/ideaCommentFiles";

                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + fileName;
                file.transferTo(new File(filePath));

                IdeaCommentFile ideaCommentFile = new IdeaCommentFile(originFileName, fileName, contentType, fileSize, filePath, comment);
                fileService.saveFile(ideaCommentFile);
            }
        }

        return new ResponseDTO.Create(saveId, true);
    }

    @PutMapping("/idea/comment")
    public ResponseDTO.Update updateIdeaComment(@RequestPart(value = "dto") CommentDTO.Update dto, @RequestPart(value = "files") MultipartFile[] files) throws exception.DogpawNotFoundException, IOException, NoSuchAlgorithmException, exception.InvalidArgumentException, exception.ArgumentNullException {
        ideaCommentService.updateByCommentId(dto.getId(), dto.getText());

        if(!files[0].isEmpty()) {
            for(MultipartFile file : files) {
                String originFileName = file.getOriginalFilename();
                String fileName = new MD5Generator(originFileName).toString();
                String contentType = file.getContentType();
                Long fileSize = file.getSize();
                String savePath = System.getProperty("user.dir") + "/ideaCommentFiles";

                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + fileName;
                file.transferTo(new File(filePath));

                IdeaCommentFile ideaCommentFile = new IdeaCommentFile(originFileName, fileName, contentType, fileSize, filePath, ideaCommentService.findOne(dto.getId()));
                fileService.saveFile(ideaCommentFile);
            }
        }

        return new ResponseDTO.Update(true);
    }

    @GetMapping("/idea/comment/download")
    public ResponseEntity<Resource> fileDownload(@RequestParam Long fileId) throws IOException {
        IdeaCommentFile file = fileService.getFile(fileId);
        Path path = Paths.get(file.getPath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/idea/comment")
    public ResponseDTO.Delete deleteIdeaComment(@RequestParam Long ideaCommentId) throws exception.DogpawNotFoundException {
        ideaCommentService.deleteByCommentId(ideaCommentId);
        return new ResponseDTO.Delete(true);
    }

    @DeleteMapping("/idea/comment/file")
    public ResponseDTO.Delete deleteFile(@RequestParam Long fileId) throws exception.DogpawNotFoundException {
        fileService.deleteByFileId(fileId);
        return new ResponseDTO.Delete(true);
    }
}
