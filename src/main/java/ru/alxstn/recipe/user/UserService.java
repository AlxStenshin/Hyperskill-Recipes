package ru.alxstn.recipe.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void add(User user){
        System.out.println("Checking user exists:" + user.getEmail() + ":" + user.getPassword());
        if (userRepository.existsById(user.getEmail())) {
            System.out.println("Already exists:" + user.getEmail());
            throw new UserAlreadyExistsException();
        }

        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        System.out.println("Saving user " + user.getEmail() + " with encoded pass: " + encodedPass);
        userRepository.save(user);
    }
}
