package com.example.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.todo.model.UserEntity;
import com.example.todo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public UserEntity create(final UserEntity userEntity) {
		if (userEntity == null || userEntity.getEmail() == null) {
			throw new RuntimeException("Invalid arguments");
		}
		final String email = userEntity.getEmail();
		if (userRepository.existsByEmail(email)) {
			log.warn("Email already exists {}", email);
			throw new RuntimeException("Email already exists");
		}
		return userRepository.save(userEntity);
	}

	public UserEntity getByCredentials(final String email, final String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
		
	}

	public UserEntity getUserInfo(String userId) {
        if (userId == null) {
            throw new RuntimeException("Invalid user ID");
        }

        return userRepository.findById(userId)
                .orElse(null);
    }

    public void updateUser(UserEntity userEntity) {
        if (userEntity == null) {
            throw new RuntimeException("Invalid user information");
        }

        userRepository.save(userEntity);
    }
}
