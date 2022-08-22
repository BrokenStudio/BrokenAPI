package dev.brokenstudio.brokenapi.security;

public class Credentials {

    private String user;
    private String password;

    public Credentials(String user, String password){
        this.user = user;
        this.password = password;
    }

    public String user() {
        return user;
    }

    public String password() {
        return password;
    }
}
