package com.iyex.springsecuritywithjwt.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Token {
    private String token;
    private String name;
}
