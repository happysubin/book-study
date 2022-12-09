package next.service;

import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Question;
import next.model.User;

public class QuestionService {

    private final QuestionDao questionDao;
    private final UserDao userDao;

    public QuestionService(QuestionDao questionDao, UserDao userDao) {
        this.questionDao = questionDao;
        this.userDao = userDao;
    }

    public void execute(Long questionId, String userId){

        Question question = questionDao.findById(questionId);

        if(question.hasComment()){
            throw new RuntimeException("답변이 1개 이상 달리면 질문을 삭제할 수 없습니다.");
        }

        User user = userDao.findByUserId(userId);

        if(!question.isWriter(user)){
            throw new RuntimeException("작성자만 삭제할 수 있습니다");
        }

        //삭제
        questionDao.delete(questionId);
    }
}
