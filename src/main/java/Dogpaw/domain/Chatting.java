package Dogpaw.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Chatting {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
