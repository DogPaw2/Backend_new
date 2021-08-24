package Dogpaw.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

public class IdeaDTO {

    @Data
    public static class Create extends Update {
        private Long userId;
        private Long ideaBoardId;
    }

    @Data
    public static class Update {
        private String text;
        private LocalDate date;
        private LocalTime time;
    }

    @Data
    public static class Delete {
        private Long id;
    }
}
