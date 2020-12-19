package com.akijoey.jbatison.executor;

import java.sql.PreparedStatement;

public interface ParameterHandler {

    void setParameters(PreparedStatement paramPreparedStatement);

}
