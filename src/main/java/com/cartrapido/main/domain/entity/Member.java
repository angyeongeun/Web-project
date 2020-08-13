package com.cartrapido.main.domain.entity;

import com.cartrapido.main.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@Entity
public class Member {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @Column(nullable = false)
        private String email;

        private String address;

        private String password;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Role role;

        @Builder
        public Member(String name, String email,String address,String password, Role role){
            this.name = name;
            this.email = email;
            this.address = address;
            this.password = password;
            this.role = role;
        }

        public Member update(String name,String address){
            this.name = name;
            this.address = address;

            return this;
        }

        public Member update(String name){
            this.name = name;
            return this;
        }
        public Member updatePwd(String pwd){
            this.password = pwd;
            return this;
        }

        public String getRoleKey(){
            return this.role.getKey();
        }

}
