package dio.springsecurityjwt.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class Sessao {
    private String login;
    private String token;
    private Date tokenCreated;
    private Date tokenExpired;
    private Long expiration;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenCreated() {
        return tokenCreated;
    }

    public void setTokenCreated(Date tokenCreated) {
        this.tokenCreated = tokenCreated;
    }

    public Date getTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(Date tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    //getters e setters
}
