package com.akijoey.jbatison.executor;

import java.sql.PreparedStatement;

public class DefaultParameterHandler implements ParameterHandler {

    private Object parameter;

    public DefaultParameterHandler(Object parameter) {
        this.parameter = parameter;
    }

    @Override
    public void setParameters(PreparedStatement preparedStatement) {
        try {
            if (parameter != null) {
                if (parameter.getClass().isArray()) {
                    Object[] params = (Object[]) parameter;
                    for (int i = 0;i < params.length;i++) {
                        // TODO 参数类型转换
                        preparedStatement.setObject(i + 1, params[i]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
