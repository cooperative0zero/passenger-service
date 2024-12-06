package com.modsen.software.passenger.filter.tokeninfo.util;

import com.modsen.software.passenger.filter.tokeninfo.UserTokenInfo;
import reactor.core.publisher.Mono;

public class ReactiveRequestContextHolder {
    public static final Class<UserTokenInfo> CONTEXT_KEY = UserTokenInfo.class;

    public static Mono<UserTokenInfo> getUserTokenInfo() {
        return Mono.deferContextual(contextView -> Mono.just(contextView.get(CONTEXT_KEY)));
    }
}
