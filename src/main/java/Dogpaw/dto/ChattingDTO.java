package Dogpaw.dto;

import lombok.Data;

public class ChattingDTO {
    @Data
    public static class Create extends Update{

    }

    @Data
    public static class Update {
        private String name;
        private String purpose;
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
