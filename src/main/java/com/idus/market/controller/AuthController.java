package com.idus.market.controller;

import com.idus.market.config.CommonResponse;
import com.idus.market.config.auth.PrincipalDetails;
import com.idus.market.config.exception.LoginFailedException;
import com.idus.market.config.jwt.TokenProvider;
import com.idus.market.config.jwt.TokenService;
import com.idus.market.dto.AuthDto;
import com.idus.market.dto.UserDto.CreateUserDto;
import com.idus.market.service.AuthService;
import com.idus.market.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;
  private final UserService userService;
  private final TokenProvider tokenProvider;
  private final TokenService tokenService;

  @PostMapping("login")
  @ApiOperation(value = "회원 로그인", notes = "회원 로그인을 수행합니다. (토큰 유효 시간 : 15분)")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "email", value = "이메일", required = true),
      @ApiImplicitParam(name = "password", value = "비밀번호", required = true)
  })
  public CommonResponse login(AuthDto authDto) {
    PrincipalDetails principalDetails;
    try {
      principalDetails = authService.login(authDto);
    } catch (LoginFailedException loginFailedException) {
      return CommonResponse.builder()
          .status(401)
          .message(loginFailedException.getMessage())
          .build();
    }

    String token = tokenProvider.createToken(principalDetails);

    return CommonResponse.builder()
        .status(200)
        .message(token)
        .build();
  }

  @PostMapping("logout")
  @ApiOperation(value = "회원 로그아웃", notes = "회원 로그아웃을 수행합니다.")
  @ApiImplicitParam(name = "token", value = "로그인 시 발급받은 토큰", required = true)
  public CommonResponse logout(String token) {
    if (!tokenService.deleteToken(token)) {
      return CommonResponse.builder()
          .status(400)
          .message("Invalid token")
          .build();
    }
    return CommonResponse.builder()
        .status(200)
        .message("Success to sign out")
        .build();
  }

  @PostMapping("join")
  @ApiOperation(value = "회원가입", notes = "회원 가입을 수행합니다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "name", value = "이름 (최대길이 20자, 영문 대소문자, 한글만 허용)", required = true),
      @ApiImplicitParam(name = "email", value = "이메일 (최대길이 100자, 이메일 형식만 허용)", required = true),
      @ApiImplicitParam(name = "nick", value = "닉네임 (최대길이 30자, 영문 소문자만 허용)", required = true),
      @ApiImplicitParam(name = "password", value = "비밀번호 (최소길이 10자, 영문 대문자, 영문 소문자, 특수 문자, 숫자 각 1개 이상 포함해야 함)", required = true),
      @ApiImplicitParam(name = "phoneNumber", value = "전화번호 (최대길이 20자, 전화번호 형식만 허용)", required = true),
      @ApiImplicitParam(name = "gender", value = "성별 선택")
  })
  public CommonResponse join(@Valid CreateUserDto createUserDto, Errors errors) {
    if (errors.hasErrors()) {
      return CommonResponse.builder()
          .status(400)
          .message(errors.getAllErrors().get(0).getDefaultMessage())
          .build();
    }

    if (userService.findByEmail(createUserDto.getEmail()).isPresent()) {
      return CommonResponse.builder()
          .status(400)
          .message("Email already exist")
          .build();
    }

    authService.join(createUserDto);

    return CommonResponse.builder()
        .status(200)
        .message("Success to join")
        .build();
  }
}
