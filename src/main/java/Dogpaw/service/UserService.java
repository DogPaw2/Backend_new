package Dogpaw.service;
import Dogpaw.domain.User;
import Dogpaw.repository.UserRepository;
import Dogpaw.service.exception.exception;
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

    public Long saveUser(User user) throws exception.ArgumentNullException, exception.InvalidArgumentException {
        if(user == null){
            throw new exception.ArgumentNullException("User can't be null");
        }if(user.getName().isEmpty()){
            throw new exception.InvalidArgumentException("User Name is null");
        }
        User save = userRepository.save(user);

        return user.getId();
    }

    public void deleteByUserId(Long id) throws exception.DogpawNotFoundException{
        userRepository.deleteById(id);
    }

    public User findOne(Long id) throws  exception.DogpawNotFoundException{
        User user = userRepository.findById(id).orElseThrow(()-> new exception.DogpawNotFoundException("User with id : " + id + "is not valid"));
        return user;
    }

}
