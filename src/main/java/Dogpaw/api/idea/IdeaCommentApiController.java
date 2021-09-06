package Dogpaw.api.idea;

import Dogpaw.domain.User;
import Dogpaw.domain.idea.Idea;
import Dogpaw.domain.idea.IdeaComment;
import Dogpaw.domain.idea.IdeaCommentFile;
import Dogpaw.dto.CommentDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.UserService;
import Dogpaw.service.exception.exception;
import Dogpaw.service.idea.IdeaCommentFileService;
import Dogpaw.service.idea.IdeaCommentService;
import Dogpaw.service.idea.IdeaService;
import Dogpaw.util.MD5Generator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @DeleteMapping("/idea/comment")
    public ResponseDTO.Delete deleteIdeaComment(@RequestParam Long id) throws exception.DogpawNotFoundException {
        ideaCommentService.deleteByCommentId(id);
        return new ResponseDTO.Delete(true);
    }
}
