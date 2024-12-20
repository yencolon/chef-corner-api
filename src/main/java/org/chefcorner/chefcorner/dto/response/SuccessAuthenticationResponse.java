package org.chefcorner.chefcorner.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chefcorner.chefcorner.entities.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private User user;
}
