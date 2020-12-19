package com.akijoey.jbatison.executor;

import java.sql.ResultSet;
import java.util.List;

public interface ResultSetHandler {

    <E> List<E> handleResultSets(ResultSet resultSet);

}
