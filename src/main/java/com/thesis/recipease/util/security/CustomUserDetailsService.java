package com.thesis.recipease.util.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DataSource dataSource;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT email, password, active FROM account WHERE email = ?"
            );
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                boolean isActive = resultSet.getBoolean("active");

                if (!isActive) {
                    throw new InactiveAccountException("Account is not active"); // Custom exception
                }

                // Return a UserDetails object
                return User.withUsername(email)
                        .password(password)
                        .roles("USER") // Update role logic as per your app
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error loading user", e);
        }
    }

    public static class InactiveAccountException extends RuntimeException {
        public InactiveAccountException(String message) {
            super(message);
        }
    }
}