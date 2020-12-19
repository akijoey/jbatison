package com.akijoey.jbatison.session;

import com.akijoey.jbatison.config.Configuration;
import com.akijoey.jbatison.mapping.MappedStatement;
import com.akijoey.jbatison.mapping.SqlCommandType;
import com.akijoey.jbatison.annotation.Mapper;
import com.akijoey.jbatison.annotation.Select;
import com.akijoey.jbatison.annotation.Update;
import com.akijoey.jbatison.annotation.Insert;
import com.akijoey.jbatison.annotation.Delete;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.lang.reflect.Method;
import java.lang.annotation.Annotation;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
        loadMappers(Configuration.getProperty("mappers.package"));
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(this.configuration);
    }

    private void loadMappers(String packageName) {
        String dirName = packageName.replaceAll("\\.", "/");
        URL url = this.getClass().getClassLoader().getResource(dirName);
        File mappersDir = new File(url.getFile());
        if (mappersDir.isDirectory()) {
            try {
                for (File mapper : mappersDir.listFiles()) {
                    String className = packageName + "." + mapper.getName().replace(".class", "");
                    Class cls = Class.forName(className);
                    if (cls.getAnnotation(Mapper.class) != null) {
                        parseMethods(cls);
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Warn: Cannot found mapper");
        }
    }

    private void parseMethods(Class cls) {
        for (Method method : cls.getMethods()) {
            Annotation[] annotations = method.getAnnotations();
            if (annotations != null && annotations.length > 0) {
                String sql = null;
                SqlCommandType sqlCommandType = null;
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Select) {
                        sql = ((Select) annotation).value();
                        sqlCommandType = SqlCommandType.SELECT;
                    } else if (annotation instanceof Update) {
                        sql = ((Update) annotation).value();
                        sqlCommandType = SqlCommandType.UPDATE;
                    } else if (annotation instanceof Insert) {
                        sql = ((Insert) annotation).value();
                        sqlCommandType = SqlCommandType.INSERT;
                    } else if (annotation instanceof Delete) {
                        sql = ((Delete) annotation).value();
                        sqlCommandType = SqlCommandType.DELETE;
                    }
                }
                if (sql != null) {
                    String id = cls.getName() + "." + method.getName();
                    Class<?> returnType = getReturnType(method);
                    MappedStatement statement = new MappedStatement(id, sql, returnType, sqlCommandType);
                    configuration.addMappedStatement(id, statement);
                    configuration.addMapper(cls);
                }
            }
        }
    }

    private Class<?> getReturnType(Method method) {
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            return (Class<?>) actualTypeArguments[0];
        } else {
            return (Class<?>) genericReturnType;
        }
    }

}
