package com.akijoey.jbatison.proxy;

import com.akijoey.jbatison.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

public class MapperRegistry {

    private final Map<Class<?>, MapperProxyFactory<?>> mappers = new HashMap<>();

    public <T> void addMapper(Class<T> type) {
        this.mappers.put(type, new MapperProxyFactory<T>(type));
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) this.mappers.get(type);
        return mapperProxyFactory.newInstance(sqlSession);
    }

}
