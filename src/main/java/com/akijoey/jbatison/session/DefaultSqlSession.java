package com.akijoey.jbatison.session;

import com.akijoey.jbatison.config.Configuration;
import com.akijoey.jbatison.executor.DefaultExecutor;
import com.akijoey.jbatison.executor.Executor;
import com.akijoey.jbatison.mapping.MappedStatement;

import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;
    private final Executor executor;


    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = new DefaultExecutor(configuration);
    }

    @Override
    public <T> T selectOne(String id, Object parameter) {
        List<T> results = this.<T> selectList(id, parameter);
        return (results != null && !results.isEmpty()) ? results.get(0) : null;
    }

    @Override
    public <E> List<E> selectList(String id, Object parameter) {
        MappedStatement ms = this.configuration.getMappedStatement(id);
        return this.executor.<E> doQuery(ms, parameter);
    }

    @Override
    public void update(String id, Object parameter) {
        MappedStatement ms = this.configuration.getMappedStatement(id);
        this.executor.doUpdate(ms, parameter);
    }

    @Override
    public void insert(String id, Object parameter) {
        MappedStatement ms = this.configuration.getMappedStatement(id);
        this.executor.doInsert(ms, parameter);
    }

    @Override
    public void delete(String id, Object parameter) {
        MappedStatement ms = this.configuration.getMappedStatement(id);
        this.executor.doDelete(ms, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.<T> getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }

}
