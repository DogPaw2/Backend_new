package Dogpaw.dto;

import lombok.Data;


public class FileDTO {

    @Data
    public static class Create extends FileDTO.Update {

    }

    @Data
    public static class Update {

        private String fileName;

        private String contentType;

    }

    @Data
    public static class Delete {
        private Long id;
    }
}
