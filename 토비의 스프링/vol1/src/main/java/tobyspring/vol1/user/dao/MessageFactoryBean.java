package tobyspring.vol1.user.dao;

import net.bytebuddy.build.Plugin;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * 스프링을 대신해서 오브젝트의 생성로직을 담당하도록 만들어진 특별한 빈을 팩토리 빈이라고 한다.
 */

@Component("message")
public class MessageFactoryBean implements FactoryBean<MessageFactoryBean.Message> {

    private String text;

    @Override
    public Message getObject() throws Exception { //실제 빈으로 사용될 오브젝트를 직접 생성.
        return Message.newMessage(this.text);
    }

    @Override
    public Class<? extends Message> getObjectType() {
        return Message.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public static class Message{
        private String text;

        private Message(String text){
            this.text = text;
        }

        public String getText(){
            return text;
        }

        public static Message newMessage(String text){
            return new Message(text);
        }
    }
}
