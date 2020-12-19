package com.akijoey.jbatison.executor;

import com.akijoey.jbatison.mapping.MappedStatement;

import java.util.List;

public interface Executor {

    <E> List<E> doQuery(MappedStatement ms, Object parameter);

    void doUpdate(MappedStatement ms, Object parameter);

    void doInsert(MappedStatement ms, Object parameter);

    void doDelete(MappedStatement ms, Object parameter);

}
