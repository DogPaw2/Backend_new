package Dogpaw.dto.message;

import Dogpaw.domain.User;
import lombok.Data;

public class MessageDTO {

    @Data
    public static class Create {
        private Long messageRoomId;
        private Long userId;
        private String text;
    }

    @Data
    public static class Update {
        private Long id;
        private String text;
    }

    @Data
    public static class Get {
        private Long fileId;
    }

    @Data
    public static class Delete {
        private Long id;
    }
}
