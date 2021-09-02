package Dogpaw.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserWorkspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Getter
    @ManyToOne
    @JoinColumn(name = "WORKSPACE_ID")
    private Workspace workspace;

    @Builder
    public UserWorkspace(User user, Workspace workspace){
        this.user = user;
        this.workspace = workspace;
    }


}
