package kh.farrukh.notification_service;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static kh.farrukh.notification_service.Constants.TABLE_NAME_NOTIFICATION;

@Entity
@Table(name = TABLE_NAME_NOTIFICATION)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime sentAt;
}
