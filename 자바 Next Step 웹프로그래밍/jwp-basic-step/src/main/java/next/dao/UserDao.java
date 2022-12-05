package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import next.model.User;

public class UserDao {

    public void insert(User user) throws SQLException {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql, (PreparedStatement pstmt) ->{
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        });
    }

    public void update(User user) throws SQLException {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?;";

        jdbcTemplate.update(sql, (PreparedStatement pstmt) ->{
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        });
    }

    public User findByUserId(String userId) throws SQLException {

        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        return (User) jdbcTemplate.queryForObject(
                sql,
                (PreparedStatement pstmt) -> pstmt.setString(1, userId),
                (ResultSet rs) -> {
                    Object user = null;
                    if (rs.next()) {
                        user = new User(
                                rs.getString("userId"),
                                rs.getString("password"),
                                rs.getString("name"),
                                rs.getString("email"));
                    }
                    return user;
                }
        );
    }


    public List<User> findAllUser() throws SQLException {

        String sql = "SELECT userId, password, name, email FROM USERS";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        return (List<User>) jdbcTemplate.query(sql, (ResultSet rs) -> new User(
                                        rs.getString("userId"),
                                        rs.getString("password"),
                                        rs.getString("name"),
                                        rs.getString("email")));
    }
}
