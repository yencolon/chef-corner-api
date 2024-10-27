package org.chefcorner.chefcorner.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "white_list_tokens")
public class WhiteListToken {
    @Id
    private Long id;
    private String token;
    @OneToOne(mappedBy = "whiteListToken")
    private User user;
}
