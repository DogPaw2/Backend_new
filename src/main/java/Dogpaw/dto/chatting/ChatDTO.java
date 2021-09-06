package Dogpaw.dto.chatting;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

public class ChatDTO {

    @Data
    public static class Create extends Update {
        private Long userId;
        private Long chattingId;

    }

    @Data
    public static class Update {
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
