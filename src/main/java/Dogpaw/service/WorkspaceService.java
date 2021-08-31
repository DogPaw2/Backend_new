package Dogpaw.service;

import Dogpaw.domain.*;
import Dogpaw.repository.UserChannelRepository;
import Dogpaw.repository.UserRepository;
import Dogpaw.repository.UserWorkspaceRepository;
import Dogpaw.repository.WorkspaceRepository;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkspaceService {

    @NonNull
    private final WorkspaceRepository workspaceRepository;
    @NonNull
    private final UserRepository userRepository;
    @NonNull
    private final UserWorkspaceRepository userWorkspaceRepository;
    @NonNull
    private final ChattingService chattingService;
    @NonNull
    private final IdeaBoardService ideaBoardService;
    @NonNull
    private final ChannelService channelService;

    public void addUser(Long userId, Long channelId){
        Workspace workspace = workspaceRepository.findById(channelId).get();
        User user = userRepository.findById(userId).get();

        userWorkspaceRepository.save(UserWorkspace.builder()
                .user(user)
                .workspace(workspace)
                .build());

    }

    public Long saveWorkSpace (Workspace workspace, Long userId) throws ArgumentNullException, InvalidArgumentException, ChattingService.InvalidArgumentException, ChattingService.ArgumentNullException, IdeaBoardService.InvalidArgumentException, IdeaBoardService.ArgumentNullException, UserService.UserNotFoundException, ChannelService.ArgumentNullException, ChannelService.InvalidArgumentException {
        //fail fast pattern
        //if Argument is invalid, dont do any logic
        if(workspace == null){
            throw new ArgumentNullException("WorkSpace can't be null");
        }
        if(workspace.getName().isEmpty() || workspace.getUrl().isEmpty()){
            throw new InvalidArgumentException("Work Space Id or URl is null");
        }
        Chatting chatting = new Chatting();
        IdeaBoard ideaBoard = new IdeaBoard();
        chattingService.saveChatting(chatting);
        ideaBoardService.saveIdeaBoard(ideaBoard);
        Channel channel = new Channel("General", "-", chatting, ideaBoard);
        channelService.saveChannel(channel, userId);
        workspace.getChannels().add(channel);

        Workspace save = workspaceRepository.save(workspace);
        addUser(userId, workspace.getId());

        return save.getId();

    }

    public Workspace findOne(Long id) throws NotFoundException {
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(() -> new WorkSpaceNotFoundException("Work space with id : " + id + "is not valid"));
        return workspace;
    }

    public boolean findByUrl(String url) throws NotFoundException {
        Workspace workspace = workspaceRepository.findByUrl(url);
        return workspace != null;
    }



    public void deleteByWorkSpaceId(Long id) throws NotFoundException {

        workspaceRepository.deleteById(id);

    }

    public List<UserWorkspace> getWorkspaceList(Long id) throws NotFoundException{
        User user = userRepository.findById(id).get();
        return userWorkspaceRepository.findAllByUser(user);
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
