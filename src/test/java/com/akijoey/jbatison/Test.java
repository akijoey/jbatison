package com.akijoey.jbatison;

import com.akijoey.jbatison.entity.User;
import com.akijoey.jbatison.repository.UserRepository;
import com.akijoey.jbatison.session.SqlSession;
import com.akijoey.jbatison.session.SqlSessionFactory;
import com.akijoey.jbatison.session.SqlSessionFactoryBuilder;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("db.properties");
        SqlSession session = factory.openSession();
        UserRepository userRepository = session.getMapper(UserRepository.class);
        List<User> users = userRepository.getUsers();
        System.out.println(users);
    }

}
