package com.sunho.travel.domain.user;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String id;
    private String password;
    private String name;
    private LocalDate birthDate;
    private String role;

    public User toEntity() {
        return User
                .builder()
                .id(this.id)
                .password(this.password)
                .name(this.name)
                .birthDate(this.birthDate)
                .role(this.role).build();
    }

    // public static UserDto fromEntity(User user){
    //     return UserDto
    //             .builder()
    //             .id(user.getId())
    //             .password(user.getPassword())
    //             .name(user.getName())
    //             .birthDate(user.getBirthDate())
    //             .role(user.getRole()).build();
    // }
}
