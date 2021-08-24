package Dogpaw.dto;

import lombok.Data;

public class MessageDTO {

    @Data
    public static class Create {
        private Long messageRoomId;
        private byte sendBy;
        private String text;
    }

    @Data
    public static class Update {
        private Long id;
        private String text;
    }

    @Data
    public static class Delete {
        private Long id;
    }
}
