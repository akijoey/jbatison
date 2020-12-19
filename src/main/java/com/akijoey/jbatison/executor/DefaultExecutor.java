package com.akijoey.jbatison.executor;

import com.akijoey.jbatison.config.Configuration;
import com.akijoey.jbatison.mapping.MappedStatement;

import java.sql.*;
import java.util.List;

public class DefaultExecutor implements Executor {

    private static Connection connection;
    private Configuration configuration;

    static {
        initConnect();
    }

    public DefaultExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    private static void initConnect() {
        String driver = Configuration.getProperty("db.driver");
        String url = Configuration.getProperty("db.url");
        String username = Configuration.getProperty("db.username");
        String password = Configuration.getProperty("db.password");
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnect() throws SQLException {
        if (connection != null) {
            return connection;
        } else {
            throw new SQLException("Error: Connect failed");
        }
    }

    @Override
    public <E> List<E> doQuery(MappedStatement ms, Object parameter) {
        try {
            Connection connection = getConnect();
            StatementHandler statementHandler = new DefaultStatementHandler(ms);
            PreparedStatement preparedStatement = statementHandler.prepare(connection);
            new DefaultParameterHandler(parameter).setParameters(preparedStatement);
            ResultSet resultSet = statementHandler.query(preparedStatement);
            ResultSetHandler resultSetHandler = new DefaultResultSetHandler(ms);
            return resultSetHandler.handleResultSets(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void doUpdate(MappedStatement ms, Object parameter) {
        try {
            Connection connection = getConnect();
            StatementHandler statementHandler = new DefaultStatementHandler(ms);
            PreparedStatement preparedStatement = statementHandler.prepare(connection);
            new DefaultParameterHandler(parameter).setParameters(preparedStatement);
            statementHandler.update(preparedStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doInsert(MappedStatement ms, Object parameter) {
        doUpdate(ms, parameter);
    }

    @Override
    public void doDelete(MappedStatement ms, Object parameter) {
        doUpdate(ms, parameter);
    }

}
