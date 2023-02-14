package tobyspring.vol1.user.dao;

import org.hibernate.sql.Delete;
import org.springframework.dao.EmptyResultDataAccessException;
import tobyspring.vol1.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public  class UserDao {

    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        User user = null;
        if(rs.next()){
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();


        if(user == null){
            throw new EmptyResultDataAccessException(1);
        }
        return user;
    }

    public void deleteAll() throws SQLException {
        StatementStrategy st = new DeleteAllStatement(); //선정한 전략 클래스의 오브젝트 생성
        jdbcContextWithStatementStrategy(st); //컨텍스트 호출. 전략 오브젝트 생성
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try{
            c = dataSource.getConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        }
        catch(SQLException e){
            throw e;
        }
        finally {
            if(ps != null){
                try{
                    ps.close();
                }
                catch (SQLException e){
                    throw e;
                }
            }
            if(c != null){
                try{
                    c.close();
                }
                catch (SQLException e){
                    throw e;
                }
            }
        }
    }

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            c = dataSource.getConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch (SQLException e){
            throw e;
        }
        finally {
            if(rs != null){
                try{
                    rs.close();
                }
                catch (SQLException e){
                    throw e;
                }
            }
            if(ps != null){
                try{
                    ps.close();
                }
                catch (SQLException e){
                    throw e;
                }
            }
            if(c != null){
                try{
                    c.close();
                }
                catch (SQLException e){
                    throw e;
                }
            }
        }
    }
}
