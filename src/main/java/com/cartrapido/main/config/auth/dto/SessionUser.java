package com.cartrapido.main.config.auth.dto;

import com.cartrapido.main.domain.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String address;

    public SessionUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.address = member.getAddress();
    }
}
