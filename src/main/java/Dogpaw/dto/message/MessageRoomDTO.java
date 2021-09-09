package Dogpaw.dto.message;

import lombok.Data;


public class MessageRoomDTO {

    @Data
    public static class Create {
        private Long userId1;
        private Long userId2;
    }

    @Data
    public static class Delete {
        private Long id;
    }

    @Data
    public static class Get {
        private Long id;
    }

}
