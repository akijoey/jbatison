package com.akijoey.jbatison.proxy;

import com.akijoey.jbatison.mapping.MappedStatement;
import com.akijoey.jbatison.mapping.SqlCommandType;
import com.akijoey.jbatison.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

public class MapperProxy<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = -7861758496991319661L;

    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            }
            return this.execute(method, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object execute(Method method, Object[] args) {
        String id = mapperInterface.getName() + "." + method.getName();
        MappedStatement ms = sqlSession.getConfiguration().getMappedStatement(id);
        SqlCommandType sqlCommandType = ms.getSqlCommandType();
        Object result = null;
        if (sqlCommandType == SqlCommandType.SELECT) {
            Class<?> returnType = method.getReturnType();
            if (Collection.class.isAssignableFrom(returnType)) {
                result = sqlSession.selectList(id, args);
            } else {
                result = sqlSession.selectOne(id, args);
            }
        } else if (sqlCommandType == SqlCommandType.UPDATE) {
            sqlSession.update(id, args);
        } else if (sqlCommandType == SqlCommandType.INSERT) {
            sqlSession.insert(id, args);
        } else if (sqlCommandType == SqlCommandType.DELETE) {
            sqlSession.delete(id, args);
        }
        return result;
    }

}