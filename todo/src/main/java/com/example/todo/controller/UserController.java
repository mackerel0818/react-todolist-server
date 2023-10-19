package com.example.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.todo.dto.ResponseDTO;
import com.example.todo.dto.UserDTO;
import com.example.todo.model.UserEntity;
import com.example.todo.security.TokenProvider;
import com.example.todo.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private TokenProvider tokenProvider;
	
	@GetMapping("/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable String userId) {
        try {
            UserEntity user = userService.getUserInfo(userId);

            if (user != null) {
                UserDTO userInfoDTO = UserDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build();

                return ResponseEntity.ok().body(userInfoDTO);
            } else {
                return ResponseEntity.notFound().build(); 
            }
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
	
	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUserInfo(@PathVariable String userId, @RequestBody UserEntity updatedUser) {
	    try {
	        // userId에 해당하는 사용자 정보 가져오는 로직 수행
	        UserEntity user = userService.getUserInfo(userId);

	        if (user != null) {
	            // 업데이트된 사용자 정보를 기존 사용자 정보에 복사
	            user.setUsername(updatedUser.getUsername());
	            user.setEmail(updatedUser.getEmail());
	            // 다른 정보 업데이트

	            // 업데이트된 사용자 정보 저장
	            userService.updateUser(user);

	            return ResponseEntity.ok().body("User information updated successfully");
	        } else {
	            return ResponseEntity.notFound().build(); // 사용자를 찾을 수 없는 경우 404 응답
	        }
	    } catch (Exception e) {
	        ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
	        return ResponseEntity.badRequest().body(responseDTO);
	    }
	}


	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
		try {
			UserEntity user = UserEntity.builder().email(userDTO.getEmail()).username(userDTO.getUsername())
					.password(userDTO.getPassword()).build();

			UserEntity registeredUser = userService.create(user);
			UserDTO responseUserDTO = userDTO.builder().email(registeredUser.getEmail()).id(registeredUser.getId())
					.username(registeredUser.getUsername()).build();

			return ResponseEntity.ok().body(responseUserDTO);
		} catch (Exception e) {
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
		UserEntity user = userService.getByCredentials(userDTO.getEmail(),

				userDTO.getPassword());
		if (user != null) {
			final String token = tokenProvider.create(user);
			final UserDTO responseUserDTO = UserDTO.builder()

					.email(user.getEmail()).id(user.getId()).token(token).build();

			return ResponseEntity.ok().body(responseUserDTO);
		} else {
			ResponseDTO responseDTO = ResponseDTO.builder()

					.error("Login failed").build();

			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        try {
            // 유저 삭제 로직을 수행
            userService.deleteUser(userId);

            return ResponseEntity.ok().body("User deleted successfully");
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}