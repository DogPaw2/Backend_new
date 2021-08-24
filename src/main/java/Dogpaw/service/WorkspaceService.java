package Dogpaw.service;

import Dogpaw.domain.Workspace;
import Dogpaw.repository.WorkspaceRepository;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkspaceService {

    @NonNull
    private final WorkspaceRepository workspaceRepository;

    public Long saveWorkSpace (Workspace workspace) throws ArgumentNullException, InvalidArgumentException {
        //fail fast pattern
        //if Argument is invalid, dont do any logic
        if(workspace == null){
            throw new ArgumentNullException("WorkSpace can't be null");
        }
        if(workspace.getName().isEmpty() || workspace.getUrl().isEmpty()){
            throw new InvalidArgumentException("Work Space Id or URl is null");
        }
        Workspace save = workspaceRepository.save(workspace);

        return save.getId();

    }

    public Workspace findOne(Long id) throws NotFoundException {
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(() -> new WorkSpaceNotFoundException("Work space with id : " + id + "is not valid"));
        return workspace;
    }


    public void deleteByWorkSpaceId(Long id) throws NotFoundException {

        workspaceRepository.deleteById(id);

    }


    public static class WorkSpaceNotFoundException extends NotFoundException {
        public WorkSpaceNotFoundException(String msg) {
            super(msg);
        }
    }

    public static class ArgumentNullException extends Throwable {
        public ArgumentNullException(String s) {
        }
    }

    public static class InvalidArgumentException extends Throwable {
        public InvalidArgumentException(String s) {
        }
    }
}
