package Dogpaw.service;

import Dogpaw.domain.*;
import Dogpaw.repository.ChannelRepository;
import Dogpaw.repository.UserChannelRepository;
import Dogpaw.repository.UserRepository;
import Dogpaw.service.exception.exception;
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

    public Long saveChannel (Channel channel, Long userId) throws exception.ArgumentNullException, exception.InvalidArgumentException, exception.DogpawNotFoundException {
        if(channel == null){
            throw new exception.ArgumentNullException("Channel can't be null");
        }
        if(channel.getName().isEmpty() || channel.getPurpose().isEmpty()){
            throw new exception.InvalidArgumentException("Channel Id or URl is null");
        }
        Channel save = channelRepository.save(channel);
        addUser(userId, channel.getId());

        return save.getId();

    }

    public Channel findOne(Long id) throws exception.DogpawNotFoundException {
        Channel channel = channelRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("Channel with id : " + id + "is not valid"));
        return channel;
    }

    public List<UserChannelMapping> getChannelList(Long id) throws exception.DogpawNotFoundException{
        User user = userRepository.findById(id).get();
        return userChannelRepository.findAllByUser(user);
    }

    public void deleteByChannelId(Long id) throws exception.DogpawNotFoundException {
        channelRepository.deleteById(id);
    }

}
