package Dogpaw.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class UploadFile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String originName;

    @NonNull
    private String fileName;

    @NonNull
    private String contentType;

    @NonNull
    private long fileSize;

    @NonNull
    private String path;
}
