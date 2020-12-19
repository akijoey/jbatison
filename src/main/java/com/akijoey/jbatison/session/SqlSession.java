package com.akijoey.jbatison.session;

import com.akijoey.jbatison.config.Configuration;

import java.util.List;

public interface SqlSession {

    <T> T selectOne(String id, Object parameter);

    <E> List<E> selectList(String id, Object parameter);

    void update(String id, Object parameter);

    void insert(String id, Object parameter);

    void delete(String id, Object parameter);

    <T> T getMapper(Class<T> paramClass);

    Configuration getConfiguration();

}
