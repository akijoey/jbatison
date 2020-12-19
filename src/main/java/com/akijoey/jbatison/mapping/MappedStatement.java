package com.akijoey.jbatison.mapping;

public final class MappedStatement {

    private String id;
    private String sql;
    private Class resultType;
    private SqlCommandType sqlCommandType;

    public MappedStatement(String id, String sql, Class resultType, SqlCommandType sqlCommandType) {
        this.id = id;
        this.sql = sql;
        this.resultType = resultType;
        this.sqlCommandType = sqlCommandType;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }
    public void setSql(String sql) {
        this.sql = sql;
    }

    public Class getResultType() {
        return resultType;
    }
    public void setResultType(Class resultType) {
        this.resultType = resultType;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }
    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

}
