package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void update(String sql, PreparedStatementSetter setter){

        try(
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ){
            setter.setValues(pstmt);
            pstmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public List query(String sql, RowMapper mapper) throws SQLException {
        try(
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
        ){
            ArrayList<Object> result = new ArrayList<>();
            while(rs.next()){
                result.add(mapper.mapRow(rs));
            }
            return result;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException(e);
        }
    }

    public Object queryForObject(String sql, PreparedStatementSetter setter, RowMapper mapper) throws SQLException {
        ResultSet rs = null;

        try(
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ){
            setter.setValues(pstmt);
            rs = pstmt.executeQuery();
            return mapper.mapRow(rs);
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException(e);
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            }
            catch (SQLException e) {
                throw new DataAccessException(e);
            }
        }
    }
}