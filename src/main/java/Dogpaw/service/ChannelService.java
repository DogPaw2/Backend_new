package Dogpaw.service;

import Dogpaw.domain.*;
import Dogpaw.repository.ChannelRepository;
import Dogpaw.repository.UserChannelRepository;
import Dogpaw.repository.UserRepository;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ChannelService {
    @NonNull
    private final ChannelRepository channelRepository;
    @NonNull
    private final UserRepository userRepository;
    @NonNull
    private final UserChannelRepository userChannelRepository;

    public void addUser(Long userId, Long channelId){
        Channel channel = channelRepository.findById(channelId).get();
        User user = userRepository.findById(userId).get();

        userChannelRepository.save(UserChannel.builder()
                .user(user)
                .channel(channel)
                .build());

    }

    public Long saveChannel (Channel channel, Long userId) throws ArgumentNullException, InvalidArgumentException, UserService.UserNotFoundException {
        if(channel == null){
            throw new ArgumentNullException("Channel can't be null");
        }
        if(channel.getName().isEmpty() || channel.getPurpose().isEmpty()){
            throw new InvalidArgumentException("Channel Id or URl is null");
        }
        Channel save = channelRepository.save(channel);
        addUser(userId, channel.getId());

        return save.getId();

    }

    public Channel findOne(Long id) throws NotFoundException {
        Channel channel = channelRepository.findById(id).orElseThrow(() -> new ChannelNotFoundException("Channel with id : " + id + "is not valid"));
        return channel;
    }

    public List<UserChannelMapping> getChannelList(Long id) throws NotFoundException{
        User user = userRepository.findById(id).get();
        return userChannelRepository.findAllByUser(user);
    }





    public void deleteByChannelId(Long id) throws NotFoundException {
        channelRepository.deleteById(id);
    }



    public static class ChannelNotFoundException extends NotFoundException {
        public ChannelNotFoundException(String msg) {
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
