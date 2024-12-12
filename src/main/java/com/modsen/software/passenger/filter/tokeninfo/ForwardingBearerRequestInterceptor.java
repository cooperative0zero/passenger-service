package com.modsen.software.passenger.filter.tokeninfo;

import com.modsen.software.passenger.filter.tokeninfo.util.ReactiveRequestContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ForwardingBearerRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ReactiveRequestContextHolder.getUserTokenInfo()
                .doOnNext(tokenInfo -> {
                    Logger.getGlobal().info("Forwarded token info: %s".formatted(tokenInfo));
                    template.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(tokenInfo.getToken()));
                });
    }
}
