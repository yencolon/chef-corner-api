package org.chefcorner.chefcorner.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "white_list_tokens")
public class WhiteListToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accessToken;
    private String refreshToken;
    @OneToOne(mappedBy = "whiteListToken")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
