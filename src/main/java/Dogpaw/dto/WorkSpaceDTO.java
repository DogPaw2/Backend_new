package Dogpaw.dto;

import lombok.Data;

public class WorkSpaceDTO {

    @Data
    public static class Create extends Update{
        private Long userId;
    }

    @Data
    public static class Update {

        private String name;

        private String url;

    }

    @Data
    public static class Delete {
        private Long id;
    }

    @Data
    public static class Get {
        private Long userId;
    }
}
