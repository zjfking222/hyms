package com.hy.service.ms;

public interface AdminService {
    String login(String username, String userpass);
    boolean checkToken(String token);
}
