package dev.brokenstudio.brokenapi.database.mariadb;

public class ConnectionData {

    protected String host;
    protected String port;
    protected String user;
    protected String password;
    protected String database;


    public ConnectionData(String host, String port, String user, String password, String database) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
    }


    public String[] toStrings() {
        return new String[]{"jdbc:mariadb://"+host+":"+port+"/"+database, user, password};
    }
}
