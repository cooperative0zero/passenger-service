package com.modsen.software.passenger.filter.tokeninfo;

import com.modsen.software.passenger.filter.tokeninfo.util.ReactiveRequestContextHolder;
import org.springframework.stereotype.Component;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class TokenInfoFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        var userTokenInfo = new UserTokenInfo();

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            var payload = authorizationHeader.substring(7);

            userTokenInfo = TokenInfoPopulator.populateTokenInfo(payload);
        }

        var finalisedUserTokenInfo = userTokenInfo;
        return chain.filter(exchange).contextWrite(context ->
                context.put(ReactiveRequestContextHolder.CONTEXT_KEY, finalisedUserTokenInfo));
    }
}


