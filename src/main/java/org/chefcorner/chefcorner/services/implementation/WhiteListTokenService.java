package org.chefcorner.chefcorner.services.implementation;

import lombok.RequiredArgsConstructor;
import org.chefcorner.chefcorner.entities.User;
import org.chefcorner.chefcorner.entities.WhiteListToken;
import org.chefcorner.chefcorner.repositories.WhiteListTokenRepository;
import org.chefcorner.chefcorner.services.interfaces.WhiteListTokenServiceInterface;

@RequiredArgsConstructor
public class WhiteListTokenService implements WhiteListTokenServiceInterface {

    private final WhiteListTokenRepository whiteListTokenRepository;

    @Override
    public WhiteListToken saveWhiteListToken(User user, String accessToken, String refreshToken) {
        WhiteListToken whiteListToken = new WhiteListToken();
        whiteListToken.setUser(user);
        whiteListToken.setAccessToken(accessToken);
        whiteListToken.setRefreshToken(refreshToken);
        return this.whiteListTokenRepository.save(whiteListToken);
    }

    @Override
    public WhiteListToken getWhiteListTokenByToken(Long id) {
        return null;
    }
}
