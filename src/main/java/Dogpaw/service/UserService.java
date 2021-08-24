package Dogpaw.service;
import Dogpaw.domain.User;
import Dogpaw.repository.UserRepository;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    @NonNull
    private final UserRepository userRepository;

    public Long saveUser(User user) throws ArgumentNullException, InvalidArgumentException {
        if(user == null){
            throw new ArgumentNullException("User can't be null");
        }if(user.getName().isEmpty()){
            throw new InvalidArgumentException("User Name is null");
        }
        User save = userRepository.save(user);

        return user.getId();
    }

    public void deleteByUserId(Long id) throws NotFoundException{
        userRepository.deleteById(id);
    }

    public User findOne(Long id) throws  NotFoundException{
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User with id : " + id + "is not valid"));
        return user;
    }


//    exception
    public static class UserNotFoundException extends NotFoundException {
        public UserNotFoundException(String msg) {
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
