package Dogpaw.api;

import Dogpaw.domain.Idea;
import Dogpaw.domain.IdeaBoard;
import Dogpaw.domain.User;
import Dogpaw.domain.UploadFile;
import Dogpaw.dto.IdeaDTO;
import Dogpaw.dto.ResponseDTO;
import Dogpaw.service.FileService;
import Dogpaw.service.IdeaService;
import Dogpaw.service.IdeaBoardService;
import Dogpaw.service.UserService;
import Dogpaw.util.MD5Generator;
import javassist.NotFoundException;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class IdeaApiController {
    @NonNull
    private final IdeaService IdeaService;
    private final IdeaBoardService IdeaBoardService;
    private final UserService userService;
    private final FileService fileService;


    @PostMapping("/idea")
    public ResponseDTO.Create createIdea(@RequestPart(value = "dto") IdeaDTO.Create dto, @RequestPart(value = "files") MultipartFile[] files) throws IdeaService.ArgumentNullException, IdeaService.InvalidArgumentException, NotFoundException, FileService.ArgumentNullException, FileService.InvalidArgumentException, IOException, NoSuchAlgorithmException {
        IdeaBoard ideaBoard = IdeaBoardService.findOne(dto.getIdeaBoardId());
        User user = userService.findOne(dto.getUserId());
        Idea idea = new Idea(user, dto.getText(), dto.getDate(), dto.getTime(), ideaBoard);
        Long saveId = IdeaService.saveIdea(idea);

        if(!files[0].isEmpty()) {
            for(MultipartFile file : files) {
                String originFileName = file.getOriginalFilename();
                String fileName = new MD5Generator(originFileName).toString();
                String contentType = file.getContentType();
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

                UploadFile uploadFile = new UploadFile(idea, originFileName, fileName, contentType, filePath);
                fileService.saveFile(uploadFile);
            }
        }
        return new ResponseDTO.Create(saveId, true);
    }

    @GetMapping("/idea/download/{fileId}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Long fileId) throws IOException {
        UploadFile file = fileService.getFile(fileId);
        Path path = Paths.get(file.getPath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginName() + "\"")
                .body(resource);
    }


    @DeleteMapping("/idea")
    public ResponseDTO.Delete createIdea(@RequestBody IdeaDTO.Delete dto) throws NotFoundException {
        IdeaService.deleteByIdeaId(dto.getId());
        return new ResponseDTO.Delete(true);
    }
}
