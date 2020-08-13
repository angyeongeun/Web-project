package com.cartrapido.main.config.auth.dto;


import com.cartrapido.main.domain.entity.Member;
import com.cartrapido.main.domain.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {


    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String address;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String address) {
        System.out.println("OAuthAttributes 들어옴");
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes,String address) {
        System.out.println("OAuthAttributes of 들어옴");
        //System.out.println(address);
        System.out.println("4 "+ address);


        return ofGoogle(userNameAttributeName, attributes,address);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes,String address) {
        System.out.println(" OAuthAttributes ofGoogle 들어옴");
        System.out.println(attributes.get("name"));
        //String address = (String) session.getAttribute("address");
        System.out.println("3 "+address);
        
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .address(address)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }



    public Member toEntity(String address1) {
        System.out.println(" OAuthAttributes toEntity1 들어옴");

        return Member.builder()
                .name(name)
                .email(email)
                .address(address1)
                .role(Role.USER)
                .build();
    }

    public Member toEntity() {
        System.out.println(" OAuthAttributes toEntity2 들어옴");

        return Member.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .build();
    }
}