package com.akijoey.jbatison.executor;

import com.akijoey.jbatison.mapping.MappedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultStatementHandler implements StatementHandler {

    private static Pattern pattern = Pattern.compile("#\\{([^\\{\\}]*)\\}");

    private MappedStatement mappedStatement;

    public DefaultStatementHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    private static String parseSymbol(String source) {
        source = source.trim();
        Matcher matcher = pattern.matcher(source);
        return matcher.replaceAll("?");
    }

    @Override
    public PreparedStatement prepare(Connection connection) throws SQLException {
        String originalSql = mappedStatement.getSql();
        if (originalSql != null && originalSql.trim().length() > 0) {
            return connection.prepareStatement(parseSymbol(originalSql));
        } else {
            throw new SQLException("Error: SQL not found");
        }
    }

    @Override
    public ResultSet query(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    @Override
    public void update(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.executeUpdate();
    }

}
