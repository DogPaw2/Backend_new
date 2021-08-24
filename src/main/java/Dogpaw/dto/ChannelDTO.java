package Dogpaw.dto;

import Dogpaw.domain.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ChannelDTO {
    @Data
    public static class Create extends Update {

    }

    @Data
    public static class Update {
        private String name;
        private String purpose;
        private Long userId;
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
