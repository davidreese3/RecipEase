package com.thesis.recipease.util.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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

                // Fetch authorities (roles) for this user
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                PreparedStatement roleStatement = connection.prepareStatement(
                        "select role from authority where email = ?"
                );
                roleStatement.setString(1, username);
                ResultSet roleResultSet = roleStatement.executeQuery();
                while (roleResultSet.next()) {
                    String role = roleResultSet.getString("role");
                    authorities.add(new SimpleGrantedAuthority(role));  // Mapping role to authority
                }

                // Return CustomUserDetails with roles/authorities
                return new CustomUserDetails(
                        userId,
                        email,
                        password,
                        isActive,
                        authorities
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
