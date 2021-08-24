package Dogpaw.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class File {
    @Id @GeneratedValue
    @Column(name = "File_ID")
    private Long id;

    @NonNull
    private String fileName;

    @NonNull
    private String contentType;

    @NonNull
    private String path;

}
