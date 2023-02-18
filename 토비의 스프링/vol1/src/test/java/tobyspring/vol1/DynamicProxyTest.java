package tobyspring.vol1;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

public class DynamicProxyTest {

    @Test
    void pointcutAdvisor(){
        ProxyFactoryBean pf = new ProxyFactoryBean();
        pf.setTarget(new HelloTarget());

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        pf.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvise()));
        Hello proxyHello = (Hello)pf.getObject();

        assertThat(proxyHello.sayHello("Toby")).isEqualTo("HELLO TOBY");
        assertThat(proxyHello.sayHi("Toby")).isEqualTo("HI TOBY");
        assertThat(proxyHello.sayThankYou("Toby")).isEqualTo("Thank you!! Toby");
    }

    @Test
    void proxyFactoryBean(){
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());
        proxyFactoryBean.addAdvice(new UppercaseAdvise());

        Hello hello = (Hello)proxyFactoryBean.getObject();

        assertThat(hello.sayHello("Toby")).isEqualTo("HELLO TOBY");
        assertThat(hello.sayHi("Toby")).isEqualTo("HI TOBY");
        assertThat(hello.sayThankYou("Toby")).isEqualTo("THANK YOU!! TOBY");
    }

    static class UppercaseAdvise implements MethodInterceptor{

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String result = (String) invocation.proceed();
            return result.toUpperCase(Locale.ROOT);
        }
    }

    interface Hello{
        String sayHello(String name);
        String sayHi(String name);
        String sayThankYou(String name);
    }

    static class HelloTarget implements Hello{

        @Override
        public String sayHello(String name) {
            return "Hello " + name;
        }

        @Override
        public String sayHi(String name) {
            return "Hi " + name;
        }

        @Override
        public String sayThankYou(String name) {
            return "Thank you!! " + name;
        }
    }
}
