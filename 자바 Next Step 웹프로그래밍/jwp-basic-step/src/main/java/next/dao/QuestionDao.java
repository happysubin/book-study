package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Question;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QuestionDao {
    public Question findById(Long questionId){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionId=?";
        return jdbcTemplate.queryForObject(
                sql,
                (PreparedStatement pstmt) -> pstmt.setString(1, String.valueOf(questionId)),
                (ResultSet rs) -> {
                    Question question = null;
                    if (rs.next()) {
                        question = new Question(
                                Long.valueOf(rs.getString("questionId")),
                                rs.getString("writer"),
                                rs.getString("title"),
                                rs.getString("contents"),
                                rs.getTimestamp("createdDate"),
                                Integer.parseInt(rs.getString("countOfAnswer")));
                    }
                    return question;
                }
                );
    }

    public List<Question> findAll() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
                + "order by questionId desc";

        RowMapper<Question> rm = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs) throws SQLException {
                return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"), null,
                        rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
            }

        };

        return jdbcTemplate.query(sql, rm);
    }
}
