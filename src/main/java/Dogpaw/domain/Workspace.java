package Dogpaw.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "workspace")
    private List<UserWorkspace> users = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="channel_id")
    private List<Channel> channels = new ArrayList<>();
}
