package com.akijoey.jbatison.config;

import com.akijoey.jbatison.proxy.MapperRegistry;
import com.akijoey.jbatison.mapping.MappedStatement;
import com.akijoey.jbatison.session.SqlSession;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

public class Configuration {

    public static Properties props = new Properties();

    public final MapperRegistry mapperRegistry = new MapperRegistry();
    public final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public <T> void addMapper(Class<T> type) {
        this.mapperRegistry.addMapper(type);
    }
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return this.mapperRegistry.getMapper(type, sqlSession);
    }

    public void addMappedStatement(String key, MappedStatement mappedStatement) {
        this.mappedStatements.put(key, mappedStatement);
    }
    public MappedStatement getMappedStatement(String key) {
        return this.mappedStatements.get(key);
    }

    public static String getProperty(String key) {
        return getProperty(key, "");
    }
    public static String getProperty(String key, String defaultValue) {
        return props.containsKey(key) ? props.getProperty(key) : defaultValue;
    }

}
