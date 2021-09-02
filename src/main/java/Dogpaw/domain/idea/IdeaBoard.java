package Dogpaw.domain.idea;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class IdeaBoard {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
