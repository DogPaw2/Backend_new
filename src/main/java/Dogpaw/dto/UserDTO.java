package Dogpaw.dto;

import lombok.Data;

public class UserDTO {

    @Data
    public static class Create extends Update {

    }

    @Data
    public static class Update {
        private String name;
    }

    @Data
    public static class Delete {
        private Long id;
    }
}
