package Dogpaw.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
// why protected?
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//builds constructor with @NonNull annotation
@RequiredArgsConstructor
public class Workspace {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String url;

}
