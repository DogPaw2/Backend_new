package Dogpaw.domain.message;

import Dogpaw.domain.Workspace;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class MessageRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "messageRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserMessageRoom> users = new ArrayList<>();

    @NonNull
    @Getter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;
}
