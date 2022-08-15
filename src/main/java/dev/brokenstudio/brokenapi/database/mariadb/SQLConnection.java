package dev.brokenstudio.brokenapi.database.mariadb;

import dev.brokenstudio.brokenapi.serializer.Serializer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLConnection {

    private final String[] conString;

    public SQLConnection(String[] conString){
        this.conString = conString;
    }

    public void executePipeline(String... queries){
        SQLPipe pipe = new SQLPipe();
        for(String query : queries){
            pipe.addQuery(query);
        }
        pipe.execute(this);
    }

    public void executePipeline(SQLPipe pipe){
        pipe.execute(this);
    }

    public void query(String query){
        Connection con = null;
        try {
            con = getDriverConnection();
            con.prepareStatement(query).execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public <T> T result(String query, String column, Class<T> classOfT){
        Connection con = null;
        try {
            con = getDriverConnection();
            String object = con.prepareStatement(query).executeQuery().getString(column);
            return Serializer.deserialize(object, classOfT);
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public ResultSet resultSet(String query){
        Connection con = null;
        try {
            con = getDriverConnection();
            return con.prepareStatement(query).executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Connection getDriverConnection()throws SQLException {
        return DriverManager.getConnection(conString[0], conString[1], conString[2]);
    }

}
