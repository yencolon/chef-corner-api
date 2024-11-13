package org.chefcorner.chefcorner.services.interfaces;

import org.chefcorner.chefcorner.entities.User;
import org.chefcorner.chefcorner.entities.WhiteListToken;

public interface WhiteListTokenServiceInterface {
    WhiteListToken saveWhiteListToken(User user, String accessToken, String refreshToken);
    WhiteListToken getWhiteListTokenByToken(Long id);
}
