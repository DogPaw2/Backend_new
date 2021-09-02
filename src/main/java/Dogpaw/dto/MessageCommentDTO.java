package Dogpaw.dto;


import lombok.Data;

public class MessageCommentDTO {

    @Data
    public static class Create extends Update {
        private Long userId;
        private Long messageId;
    }

    @Data
    public static class Update {
        private String text;
    }

    @Data
    public static class Delete {
        private Long id;
    }
}
