package Dogpaw.api.idea;

import Dogpaw.domain.idea.Idea;
import Dogpaw.domain.idea.IdeaBoard;
import Dogpaw.domain.User;
import Dogpaw.domain.idea.IdeaFile;
import Dogpaw.dto.idea.IdeaDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.idea.IdeaFileService;
import Dogpaw.service.exception.exception;
import Dogpaw.service.idea.IdeaService;
import Dogpaw.service.idea.IdeaBoardService;
import Dogpaw.service.UserService;
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
public class IdeaApiController {
    @NonNull
    private final IdeaService IdeaService;
    private final IdeaBoardService IdeaBoardService;
    private final UserService userService;
    private final IdeaFileService fileService;


    @PostMapping("/idea")
    public ResponseDTO.Create createIdea(@RequestPart(value = "dto") IdeaDTO.Create dto, @RequestPart(value = "files") MultipartFile[] files) throws IOException, NoSuchAlgorithmException, exception.DogpawNotFoundException, exception.InvalidArgumentException, exception.ArgumentNullException {
        IdeaBoard ideaBoard = IdeaBoardService.findOne(dto.getIdeaBoardId());
        User user = userService.findOne(dto.getUserId());
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        Idea idea = new Idea(user, dto.getText(), date, time, ideaBoard);

        Long saveId = IdeaService.saveIdea(idea);

        if(!files[0].isEmpty()) {
            for(MultipartFile file : files) {
                String originFileName = file.getOriginalFilename();
                String fileName = new MD5Generator(originFileName).toString();
                String contentType = file.getContentType();
                Long fileSize = file.getSize();
                String savePath = System.getProperty("user.dir") + "/ideaFiles";

                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + fileName;
                file.transferTo(new File(filePath));

                IdeaFile ideaFile = new IdeaFile(originFileName, fileName, contentType, fileSize, filePath, idea);
                fileService.saveFile(ideaFile);
            }
        }
        return new ResponseDTO.Create(saveId, true);
    }

    @PutMapping("/idea")
    public ResponseDTO.Update updateIdea(@RequestBody IdeaDTO.Update dto) throws exception.DogpawNotFoundException {
        IdeaService.updateByIdeaId(dto.getId(), dto.getText());
        return new ResponseDTO.Update(true);
    }

    @GetMapping("/idea/download")
    public ResponseEntity<Resource> fileDownload(@RequestParam Long fileId) throws IOException {
        IdeaFile file = fileService.getFile(fileId);
        Path path = Paths.get(file.getPath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/idea")
    public ResponseDTO.Delete createIdea(@RequestParam Long id) throws exception.DogpawNotFoundException {
        IdeaService.deleteByIdeaId(id);
        return new ResponseDTO.Delete(true);
    }
}
