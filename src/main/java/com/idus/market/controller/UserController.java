package com.idus.market.controller;

import com.idus.market.config.CommonResponse;
import com.idus.market.config.auth.PrincipalDetails;
import com.idus.market.config.exception.LoginFailedException;
import com.idus.market.config.jwt.TokenProvider;
import com.idus.market.config.jwt.TokenService;
import com.idus.market.domain.user.User;
import com.idus.market.dto.AuthDto;
import com.idus.market.dto.UserDto;
import com.idus.market.service.AuthService;
import com.idus.market.service.UserService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

  private final UserService userService;

  @GetMapping()
  @ApiOperation(value = "사용자 리스트 조회", notes = "전체 사용자를 조회합니다")
  public List<User> getUsers() {
    return userService.findAll();

//    return CommonResponse.builder()
//        .code("LOGIN_SUCCESS")
//        .status(200)
//        .message()
//        .build();
  }

}
