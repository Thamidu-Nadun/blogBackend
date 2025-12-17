package com.nadun.blog.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nadun.blog.model.Role;
import com.nadun.blog.model.User;
import com.nadun.blog.repo.UserRepo;
import com.nadun.blog.utils.Verification;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    /**
     * Get all users
     * 
     * @return List of users
     */
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    /**
     * Get user by ID
     * 
     * @param id {Integer}
     * @return User object
     */
    public User getUserById(Integer id) {
        return userRepo.findById(id).orElse(null);
    }

    /**
     * Create or update a user
     * 
     * @param user {User}
     * @return Saved User object
     */
    public User saveUser(String name, String email, String password) {
        // generate a new token
        String verificationToken = Verification.generateToken();

        // encode password
        password = passwordEncoder.encode(password);

        // save user
        User newUser = new User(null, name, email, password, false,
                Date.valueOf(LocalDate.now()), null, null, null,
                verificationToken, Role.ROLE_READER);
        newUser = userRepo.save(newUser);

        // send verification mail
        try {
            mailService.sendMail(newUser.getEmail(), "Verification Email",
                    "verify http://localhost:8080/api/v1/users/verify/" +
                            newUser.getId() + "?token=" +
                            verificationToken);

        } catch (Exception e) {
            System.err.println("Failed to send verification email to " + newUser.getEmail());
            e.printStackTrace();
        }
        return newUser;
    }

    /**
     * Generate login token for user
     * 
     * @param email
     * @param password
     * @return JWT token
     * @throws Exception
     */
    public String generateLoginToken(String email, String password) throws Exception {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception("Invalid credentials");
        }
        return jwtService.generateToken(user);
    }

    /**
     * Verify user by ID
     * 
     * @param id
     * @param token
     * @return true if verified, false otherwise
     */
    public boolean verifyUser(Integer id, String token) {
        User user = getUserById(id);
        if (user != null) {
            if (user.getVerificationToken() == null) {
                return true; // Already verified
            }
            boolean isValid = Verification.verifyToken(user.getVerificationToken(), token);
            if (isValid) {
                user.setVerified(true);
                user.setVerificationToken(null);
                userRepo.save(user);
                return true;
            }
        }
        return false;
    }

    /**
     * Delete user by ID
     * 
     * @param id {Integer}
     */
    public void deleteUser(Integer id) {
        userRepo.deleteById(id);
    }
}
