package Dogpaw.dto;

import lombok.Data;

public class WorkSpaceDTO {

    @Data
    public static class Create extends Update{

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
}
