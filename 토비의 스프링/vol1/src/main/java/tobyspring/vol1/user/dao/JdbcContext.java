package tobyspring.vol1.user.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {

    private DataSource dataSource;


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void commandWithStatementStrategy(StatementStrategy stmt) throws SQLException {
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

    public DataSource getDataSource() {
        return dataSource;
    }

     public void executeSql(final String query)throws SQLException{
        commandWithStatementStrategy(c -> c.prepareStatement(query));
    }
}
