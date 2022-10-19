package com.amit.blogapp.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JWTAuthResponse {

    private String accessToken;
    private String tokenType = "Bearer";

    public JWTAuthResponse(String accessToken){
        this.accessToken = accessToken;
    }
}
