package Dogpaw.dto;

import lombok.Data;


public class FileDTO {

    @Data
    public static class Create{
        private String originName;

        private String fileName;

        private String contentType;

        private String path;
    }

    @Data
    public static class Delete {
        private Long id;
    }
}
