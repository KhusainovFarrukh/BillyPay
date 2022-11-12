package kh.farrukh.feign_clients.notification.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {

    private Long id;

    private String message;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("sent_at")
    private LocalDateTime sentAt;
}
