package by.epam.training.dao.impl.connectionpool;

import java.sql.Connection;

/**
 * Created by Настенька on 11/13/2015.
 */
public interface ConnectionPool {
    Connection takeConnection() throws ConnectionPoolException;
    void returnConnection(Connection connection) throws ConnectionPoolException;
}
