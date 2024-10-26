package org.chefcorner.chefcorner.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessRefreshTokenResponse {
    private String accessToken;
    private String refreshToken;
}
