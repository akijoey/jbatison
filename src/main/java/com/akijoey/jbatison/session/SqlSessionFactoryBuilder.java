package com.akijoey.jbatison.session;

import com.akijoey.jbatison.config.Configuration;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(String fileName) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        return build(inputStream);
    }

    public SqlSessionFactory build(InputStream inputStream) {
        try {
            Configuration.props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DefaultSqlSessionFactory(new Configuration());
    }

}
