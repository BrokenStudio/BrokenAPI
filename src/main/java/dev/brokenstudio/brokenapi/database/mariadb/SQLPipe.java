package dev.brokenstudio.brokenapi.database.mariadb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;

public class SQLPipe {

    private final HashSet<String> queries = new HashSet<>();

    public void addQuery(String query){
        queries.add(query);
    }

    protected void execute(SQLConnection connection){
        Connection con = null;
        try {
            con = connection.getDriverConnection();
            for(String query : queries){
                con.prepareStatement(query).execute();
            }
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

}
