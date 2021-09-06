package Dogpaw.dto;

import lombok.Data;

public class MailDTO {

    @Data
    public static class Create {
        private String address;
        private String title;
        private String message;
    }

    @Data
    public static class Check{
        private Long id;
    }

}
