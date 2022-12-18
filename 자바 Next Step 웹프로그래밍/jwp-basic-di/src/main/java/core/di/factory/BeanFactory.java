package core.di.factory;

import java.lang.reflect.Constructor;
import java.util.*;

import com.google.common.collect.Lists;
import core.annotation.Controller;
import core.annotation.Inject;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        preInstanticateBeans
                .forEach(aClass -> {
                    Object bean = instantiateClass(aClass);
                    beans.put(aClass, bean);
                });
    }

    private Object instantiateClass(Class<?> aClass){
        //@Inject 애노티에션이 걸린 생성자들을 찾음.
        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(aClass);
        try{
            if(constructor == null){ //존재하지 않으면 기본 생성자로 인스턴스를 생성. 인터페이스도 있으니 구체 클래스를 찾아야함.
                Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(aClass, preInstanticateBeans);
                return concreteClass.getConstructor().newInstance();
            }
            return instantiateConstructor(constructor); //@Inject 애노테이션이 걸린 생성자의 경우 해당 메서드로 진입.
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //Inject 애노테이션이 있는 생성자가 존재하면 instantiateConstructor 메서드를 통해 인스턴스 생성,
    private Object instantiateConstructor(Constructor<?> constructor){
        Class<?>[] parameterTypes = constructor.getParameterTypes(); //DI로 들어갈 생성자의 파라미터를 전부 가져옴.
        List<Object> args = Lists.newArrayList();
        for (Class<?> parameter : parameterTypes) {
            Object bean = instantiateClass(parameter); //DI할 파라미터가 초기화가 안됐으면 초기화함.
            args.add(bean);//초기화가 완료되면 인자에 추가됨.
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

    public Map<Class<?>, Object> getControllers(){
        HashMap<Class<?>, Object> controllers = Maps.newHashMap();
        for (Class<?> clazz : preInstanticateBeans){
            Controller annotation = clazz.getAnnotation(Controller.class);
            if(annotation != null){
                controllers.put(clazz, beans.get(clazz));
            }
        }
        return controllers;
    }
}
