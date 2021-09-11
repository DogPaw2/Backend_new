package Dogpaw.service;

import Dogpaw.domain.*;
import Dogpaw.domain.chatting.Chatting;
import Dogpaw.domain.idea.IdeaBoard;
import Dogpaw.domain.message.MessageRoom;
import Dogpaw.repository.ChannelRepository;
import Dogpaw.repository.UserRepository;
import Dogpaw.repository.UserWorkspaceRepository;
import Dogpaw.repository.WorkspaceRepository;
import Dogpaw.service.chatting.ChattingService;
import Dogpaw.service.exception.exception;
import Dogpaw.service.idea.IdeaBoardService;
import Dogpaw.service.message.MessageRoomService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.event.ChangeEvent;
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
    @NonNull
    private final MessageRoomService messageRoomService;

    public void addUser(Long userId, Long channelId) throws exception.DogpawNotFoundException {
        Workspace workspace = workspaceRepository.findById(channelId).orElseThrow(() -> new exception.DogpawNotFoundException("Work space with id : " + channelId + "is not valid"));
        User user = userRepository.findById(userId).orElseThrow(() -> new exception.DogpawNotFoundException("User with id : " + userId + "is not valid"));

        userWorkspaceRepository.save(UserWorkspace.builder()
                .user(user)
                .workspace(workspace)
                .build());

    }


    public void addChannel(Long workspaceId, Channel channel) throws exception.DogpawNotFoundException {
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() -> new exception.DogpawNotFoundException("Work space with id : " + workspaceId + "is not valid"));
        workspace.getChannels().add(channel);
        workspaceRepository.save(workspace);
    }

    public void addMessageRoom(Long workspaceId, MessageRoom messageRoom) throws exception.DogpawNotFoundException {
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() -> new exception.DogpawNotFoundException("Work space with id : " + workspaceId + "is not valid"));
        workspace.getMessageRooms().add(messageRoom);
        workspaceRepository.save(workspace);
    }

    public Long saveWorkSpace (Workspace workspace, Long userId) throws exception.ArgumentNullException, exception.InvalidArgumentException, exception.DogpawNotFoundException {
        //fail fast pattern
        //if Argument is invalid, dont do any logic
        if(workspace == null){
            throw new exception.ArgumentNullException("WorkSpace can't be null");
        }
        if(workspace.getName().isEmpty() || workspace.getUrl().isEmpty()){
            throw new exception.InvalidArgumentException("Work Space Id or URl is null");
        }
        Chatting chatting = new Chatting();
        IdeaBoard ideaBoard = new IdeaBoard();
        chattingService.saveChatting(chatting);
        ideaBoardService.saveIdeaBoard(ideaBoard);
        MessageRoom messageRoom = new MessageRoom(workspace);
        messageRoomService.saveMessageRoom(messageRoom, userId, userId);  // 워크스페이스 생성시 나와의 DM방 생성
        Channel channel = new Channel("General", "-", chatting, ideaBoard, workspace);
        channelService.saveChannel(channel, userId);
        workspace.getMessageRooms().add(messageRoom);
        workspace.getChannels().add(channel);

        Workspace save = workspaceRepository.save(workspace);
        addUser(userId, workspace.getId());

        return save.getId();

    }

    public Workspace findOne(Long id) throws exception.DogpawNotFoundException {
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("Work space with id : " + id + "is not valid"));
        return workspace;
    }

    public boolean findByUrl(String url) throws exception.DogpawNotFoundException {
        Workspace workspace = workspaceRepository.findByUrl(url);
        return workspace != null;
    }



    public void deleteByWorkSpaceId(Long id) throws exception.DogpawNotFoundException {

        workspaceRepository.deleteById(id);
    }

    public List<UserWorkspace> getWorkspaceList(Long id) throws exception.DogpawNotFoundException{
        User user = userRepository.findById(id).get();
        return userWorkspaceRepository.findAllByUser(user);
    }

}
