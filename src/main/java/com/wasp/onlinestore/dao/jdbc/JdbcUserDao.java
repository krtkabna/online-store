package com.wasp.onlinestore.dao.jdbc;

import com.wasp.onlinestore.dao.UserDao;
import com.wasp.onlinestore.dao.jdbc.mapper.UserRowMapper;
import com.wasp.onlinestore.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcUserDao implements UserDao {
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final String SELECT_USER_BY_NAME = "SELECT name, password, salt, role FROM users WHERE name=:name;";
    private static final String INSERT = "INSERT INTO users (name, password, salt, role) VALUES(:name,:password, :salt, :role);";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public boolean saveUser(User user) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
            .addValue("name", user.getName())
            .addValue("password", user.getPassword())
            .addValue("salt", user.getSalt())
            .addValue("role", user.getRole());

        return namedParameterJdbcTemplate.update(INSERT, namedParameters) == 1;

//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(INSERT)) {
//            statement.setString(1, user.getName());
//            statement.setString(2, user.getPassword());
//            statement.setString(3, user.getSalt());
//            statement.setObject(4, user.getRole().name().toUpperCase(), Types.OTHER);
//            statement.execute();
//        } catch (SQLException e) {
//            throw new DataAccessException("Could not save user by name: " + user.getName(), e);
//        }
    }

    @Override
    public User getUserByName(String name) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("name", name);
        return namedParameterJdbcTemplate.queryForObject(SELECT_USER_BY_NAME, namedParameters, USER_ROW_MAPPER);
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_NAME)) {
//            statement.setString(1, name);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    return Optional.of(USER_ROW_MAPPER.mapRow(resultSet));
//                } else {
//                    return Optional.empty();
//                }
//            }
//        } catch (SQLException e) {
//            throw new DataAccessException("Could not find user by name: " + name, e);
//        }
    }
}
