package com.idus.market.dto;

import com.idus.market.domain.user.GenderType;
import com.idus.market.domain.user.User;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateUserDto {

  @NotBlank(message = "유저명은 공백일 수 없습니다")
  @Size(min = 1, max = 20, message = "유저명의 길이는 최대 20자입니다")
  @Pattern(regexp = "^[가-힣a-zA-Z]*$", message = "유저명은 영문자 소문자, 영문자 대문자, 한글만 입력할 수 있습니다")
  private String username;

  @NotBlank(message = "닉네임은 공백일 수 없습니다")
  @Size(min = 1, max = 30, message = "닉네임의 길이는 최대 30자입니다")
  @Pattern(regexp = "^[a-z]*$", message = "닉네임은 영문자 소문자만 입력할 수 있습니다")
  private String nick;

  @NotBlank(message = "패스워드는 공백일 수 없습니다")
  @Size(min = 10, message = "패스워드의 길이는 최소 10자입니다")
  private String password;

  @NotBlank
  @Email
  @Size(max = 100, message = "이메일의 길이는 최대 100자입니다")
  private String email;

  @NotBlank(message = "전화번호는 공백일 수 없습니다")
  @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호는 010-1234-5678 형태로 표기하세요")
  private String phoneNumber;

  @NotNull
  private GenderType gender;

  @ApiModelProperty(hidden = true)
  private String roles;

  public static CreateUserDto of(User user) {
    return CreateUserDto.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .roles(user.getRole())
        .build();
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}