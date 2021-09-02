package Dogpaw.service.exception;

import javassist.NotFoundException;

public class exception {
    public static class DogpawNotFoundException extends NotFoundException {
        public DogpawNotFoundException(String msg) {
            super(msg);
        }
    }

    public static class ArgumentNullException extends Throwable {
        public ArgumentNullException(String s) {
        }
    }

    public static class InvalidArgumentException extends Throwable {
        public InvalidArgumentException(String s) {
        }
    }
}
