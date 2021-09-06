package Dogpaw.service;

import Dogpaw.domain.Mail;
import Dogpaw.dto.MailDTO;
import Dogpaw.repository.MailRepository;
import Dogpaw.repository.message.MessageRepository;
import Dogpaw.service.exception.exception;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "baejisu97@gmail.com";

    @NonNull
    private final MailRepository mailRepository;

    public void mailSend(MailDTO.Create mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        System.out.println(message);
        mailSender.send(message);
    }

    public Long saveMail(Mail mail) throws exception.InvalidArgumentException, exception.ArgumentNullException {
        if(mail == null) {
            throw new exception.ArgumentNullException("Mail can't be null");
        }
        if(mail.getAddress().isEmpty() || mail.getTitle().isEmpty() || mail.getMessage().isEmpty()) {
            throw new exception.InvalidArgumentException("Mail component is null");
        }
        Mail save = mailRepository.save(mail);

        return save.getId();
    }

    public Mail findOne(Long id) throws exception.DogpawNotFoundException {
        Mail mail = mailRepository.findById(id).orElseThrow(() -> new exception.DogpawNotFoundException("Mail with id : " + id + "is not valid"));
        return mail;
    }

}
