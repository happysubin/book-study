package tobyspring.vol1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import tobyspring.vol1.user.dao.MessageFactoryBean;

@SpringBootTest
public class BeanFactoryTest {

    @Autowired
    ApplicationContext ac;

    @Test
    void test(){
        Object message = ac.getBean("message");//빈 팩토리의 getObject 빈을 가져옴
        MessageFactoryBean bean1 = ac.getBean(MessageFactoryBean.class);
        MessageFactoryBean.Message bean = ac.getBean(MessageFactoryBean.Message.class); //이렇게도 검색이 됨.
        System.out.println(bean);
        System.out.println("bean1.getClass() = " + bean1.getClass());
        System.out.println("bean.getClass() = " + bean.getClass());

        System.out.println("message = " + message);
        Assertions.assertThat(message.getClass()).isSameAs(MessageFactoryBean.Message.class);
    }

    @Test
    void test2(){
        Object message = ac.getBean("&message");//빈 팩토리를 가져옴.
        Object message2 = ac.getBean("message");//빈 팩토리를 가져옴.

        System.out.println("message = " + message.getClass());
        System.out.println("message = " + message2.getClass());

        Assertions.assertThat(message.getClass()).isSameAs(MessageFactoryBean.class);
    }
}
