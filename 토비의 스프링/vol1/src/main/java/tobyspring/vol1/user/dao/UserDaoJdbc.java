package tobyspring.vol1.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import tobyspring.vol1.user.domain.Level;
import tobyspring.vol1.user.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public  class UserDaoJdbc implements UserDao {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            return user;
        }
    };

    public UserDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(User user){
        this.jdbcTemplate.update("insert into users(id, name, password, level, login, recommend) " +
                        "values(?,?,?,?,?,?)",
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getLevel().intValue(),
                user.getLogin(),
                user.getRecommend());
    }


    @Override
    public User get(String id){
        return jdbcTemplate.queryForObject("select * from users where id = ?", userMapper, new Object[]{id}
        );
    }

    @Override
    public void deleteAll(){
        jdbcTemplate.update("delete from users");
    }


    @Override
    public int getCount(){
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("select * from users order by id", userMapper);
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(
                "update users set name = ?, password = ?, level = ?, login = ?, recommend = ? where id = ?",
                user.getName(), user.getPassword(),user.getLevel().intValue(), user.getLogin(), user.getRecommend(),
                user.getId()
        );
    }
}
