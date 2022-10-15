
package edu.schoo21.openprojectback.models.requests;

import lombok.Data;

@Data
public class NewPasswordRequest {
    private String token;
    private String password;
}
