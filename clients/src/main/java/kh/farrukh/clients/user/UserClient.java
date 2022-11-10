package kh.farrukh.clients.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("api/v1/users/{id}")
    ResponseEntity<AppUser> getUserById(@PathVariable("id") long id);
}