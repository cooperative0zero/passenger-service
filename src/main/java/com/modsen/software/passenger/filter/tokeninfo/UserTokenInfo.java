package com.modsen.software.passenger.filter.tokeninfo;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RequestScope
@Component
@Data
public class UserTokenInfo {
    private Long id;
    private List<String> roles;
    private String token;
}
