package com.thesis.recipease.util.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DataSource dataSource;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, email, password, active FROM account WHERE email = ?"
            );
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long userId = resultSet.getLong("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                boolean isActive = resultSet.getBoolean("active");

                if (!isActive) {
                    throw new InactiveAccountException("Account is not active");
                }

                // Return CustomUserDetails
                return new CustomUserDetails(
                        userId,
                        email,
                        password,
                        isActive,
                        List.of() // Replace with actual roles/authorities if needed
                );
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