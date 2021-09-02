package Dogpaw.dto;

import Dogpaw.domain.*;
import Dogpaw.domain.chatting.ChatMapping;
import Dogpaw.domain.chatting.Chatting;
import Dogpaw.domain.idea.IdeaBoard;
import Dogpaw.domain.idea.IdeaMapping;
import Dogpaw.domain.message.MessageMapping;
import Dogpaw.domain.message.MessageRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import java.util.List;

public class ResponseDTO {


    @AllArgsConstructor
    @Data
    public static class BaseResponse {

        Boolean success;
    }

    public static class Create extends BaseResponse {
        Long id;

        public Create(Long id, Boolean success){
            super(success);
            this.id = id;
        }
    }


    // ** 업데이트 추가 **
    public static class Update extends BaseResponse {
        public Update(Boolean success) { super(success); }
    }

    public static class Delete extends BaseResponse {
        public Delete(Boolean success) {
            super(success);
        }
    }


    @Data
    @AllArgsConstructor
    public static class ChattingResponse {
        Boolean success;
        private Chatting chatting;
        private List<ChatMapping> chats;
    }

    @Data
    @AllArgsConstructor
    public static class ChannelResponse {
        Boolean success;
        private Channel channel;
    }

    @Data
    @AllArgsConstructor
    public static class MessageRoomResponse {
        Boolean success;
        private List<MessageMapping> messages;
        private MessageRoom messageRoom;
    }

    @Data
    @AllArgsConstructor
    public static class IdeaBoardResponse{
        Boolean success;
        private IdeaBoard ideaBoard;
        private List<IdeaMapping> ideas;
    }

    @Data
    @AllArgsConstructor
    public static class WorkspaceAllResponse{
        Boolean success;
        private List<UserWorkspace> workspaceList;
    }

    @Data
    @AllArgsConstructor
    public static class WorkspaceResponse{
        Boolean success;
        private Workspace workspace;
    }

    @Data
    @AllArgsConstructor
    public static class FileResponse{
        Boolean success;
        private HttpHeaders headers;
        private Resource body;
    }

}
