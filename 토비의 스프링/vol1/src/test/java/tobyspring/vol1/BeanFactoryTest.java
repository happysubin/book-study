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
        Object message = ac.getBean("message");
        Assertions.assertThat(message.getClass()).isSameAs(MessageFactoryBean.Message.class);
    }

    @Test
    void test2(){
        Object message = ac.getBean("&message");
        Assertions.assertThat(message.getClass()).isSameAs(MessageFactoryBean.class);
    }
}
