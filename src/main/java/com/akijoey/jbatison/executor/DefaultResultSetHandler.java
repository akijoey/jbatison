package com.akijoey.jbatison.executor;

import com.akijoey.jbatison.mapping.MappedStatement;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DefaultResultSetHandler implements ResultSetHandler {

    private final MappedStatement mappedStatement;

    public DefaultResultSetHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> handleResultSets(ResultSet resultSet)
    {
        try {
            List<E> result = new ArrayList<>();
            if (result == null) {
                return null;
            }
            while (resultSet.next()) {
                Class<?> entityClass = mappedStatement.getResultType();
                E entity = (E) entityClass.newInstance();
                Field[] declaredFields = entityClass.getDeclaredFields();
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    Class<?> fieldType = field.getType();
                    if (String.class.equals(fieldType)) {
                        field.set(entity, resultSet.getString(field.getName()));
                    } else if (int.class.equals(fieldType) || Integer.class.equals(fieldType)) {
                        field.set(entity, resultSet.getInt(field.getName()));
                    } else {    // TODO 类型转换
                        field.set(entity, resultSet.getObject(field.getName()));
                    }
                }
                result.add(entity);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
