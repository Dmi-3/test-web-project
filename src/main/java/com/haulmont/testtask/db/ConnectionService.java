package com.haulmont.testtask.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService
{
    private static final Logger LOG = Logger.getLogger(ConnectionService.class);
    private static final String dbPath = "res:/db/hsqlDb";
    private Connection connection;

    public synchronized Connection getConnection()
    {
        try
        {
            if (connection == null || connection.isClosed())
            {
                connection = DriverManager.getConnection("jdbc:hsqldb:" + dbPath+";shutdown=true", "SA", "");
            }
        }
        catch (SQLException e)
        {
            LOG.error("Error occured during connecting to DataBase.");
            return null;
        }

        return connection;
    }

    public void closeConnection()
    {
        try
        {
            if (connection == null || connection.isClosed())
            {
                return;
            }

            connection.close();
            connection = null;
        }
        catch (SQLException e)
        {
            LOG.error("Error occured during connecting to DataBase.");
        }
    }
}
