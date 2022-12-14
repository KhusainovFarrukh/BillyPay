package kh.farrukh.user_service.app_user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import kh.farrukh.user_service.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static kh.farrukh.user_service.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "role", "image", "email", "username"})
@Entity
@Table(name = TABLE_NAME_USER,
        uniqueConstraints = @UniqueConstraint(name = "uk_app_user_phone_number", columnNames = "phone_number"))
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME_USER_ID)
    @SequenceGenerator(name = GENERATOR_NAME_USER_ID, sequenceName = SEQUENCE_NAME_USER_ID)
    private long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    // TODO: 6/7/22 set default to false and implement phone verification OTP
    private boolean isEnabled = true;

    @Column
    private boolean isLocked = false;

//    @Enumerated(EnumType.STRING)
//    private UserRole role = UserRole.USER;

    @ManyToOne(optional = false)
    private Role role;

//    @ManyToOne
//    @JoinColumn(
//        name = "image_id",
//        foreignKey = @ForeignKey(name = "fk_image_id_of_app_user")
//    )
//    private Image image;

//    @JsonIgnoreProperties("owner")
//    @OneToMany(mappedBy = "owner", orphanRemoval = true)
//    private List<Bill> bills = new ArrayList<>();

//    public AppUser(SignUpRequest signUpRequest, PasswordEncoder passwordEncoder, ImageRepository imageRepository) {
//        this.name = signUpRequest.getName();
//        this.email = signUpRequest.getEmail();
//        this.phoneNumber = signUpRequest.getPhoneNumber();
//        this.password = passwordEncoder.encode(signUpRequest.getPassword());
//        this.role = UserRole.USER;
//        this.image = imageRepository.findById(signUpRequest.getImageId()).orElseThrow(
//            () -> new ResourceNotFoundException("Image", "id", signUpRequest.getImageId())
//        );
//    }

//    @JsonIgnore
//    @Override
//    public String getUsername() {
//        return phoneNumber;
//    }
//
//    @JsonIgnore
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @JsonIgnore
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isAccountNonLocked() {
//        return !isLocked;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isEnabled() {
//        return isEnabled;
//    }
}
