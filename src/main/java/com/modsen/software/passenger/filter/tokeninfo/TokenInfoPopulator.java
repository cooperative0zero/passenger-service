package com.modsen.software.passenger.filter.tokeninfo;

import com.modsen.software.passenger.filter.tokeninfo.util.JwtParser;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public final class TokenInfoPopulator {

    public static UserTokenInfo populateTokenInfo(String payload) {
        Map<String, Object> claims = JwtParser.parseJwtPayload(payload);
        var userTokenInfo = new UserTokenInfo();

        userTokenInfo.setToken(payload);
        userTokenInfo.setId((Integer.toUnsignedLong((Integer) claims.get("userId"))));
        userTokenInfo.setRoles(parseRealmRoles(claims));

        Logger.getGlobal().info("Received token info: %s".formatted(userTokenInfo));
        return userTokenInfo;
    }

    public static List<String> parseRealmRoles(Map<String, Object> claims) {
        Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            return ((List<String>) realmAccess.get("roles"));
        }
        return List.of();
    }
}
