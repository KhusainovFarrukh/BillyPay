package kh.farrukh.user_service.role;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import kh.farrukh.common.security.Permission;
import kh.farrukh.user_service.app_user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static kh.farrukh.user_service.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "title"})
@Entity
@Table(name = TABLE_NAME_ROLE, uniqueConstraints = {
        @UniqueConstraint(name = "uk_role_title", columnNames = "title")
})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME_ROLE_ID)
    @SequenceGenerator(name = GENERATOR_NAME_ROLE_ID, sequenceName = SEQUENCE_NAME_ROLE_ID)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Boolean isDefault = false;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Permission> permissions;

    @OneToMany(mappedBy = "role")
    private List<AppUser> users;
}