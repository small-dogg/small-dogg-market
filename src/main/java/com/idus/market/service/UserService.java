package com.idus.market.service;

import com.idus.market.domain.user.User;
import com.idus.market.dto.UserDto.GetUsersRequestDto;
import com.idus.market.dto.UserDto.GetUsersResponseDto;
import com.idus.market.repository.QUserRepository;
import com.idus.market.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

  private final UserRepository userRepository;
  private final QUserRepository qUserRepository;

  public List<GetUsersResponseDto> findAll(Pageable pageable,
      GetUsersRequestDto getUsersRequestDto) {
    List<GetUsersResponseDto> usersResponseDtoList = new ArrayList<>();
    return qUserRepository
        .findAllByUsernameIsContainingWithLastOrder(pageable, getUsersRequestDto);
//    return userRepository.findAll(pageable).toList();
  }

  public Optional<User> findByName(String name) {
    return userRepository.findByName(name);
  }

  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
