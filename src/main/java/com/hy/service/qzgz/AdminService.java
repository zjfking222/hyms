package com.hy.service.qzgz;

public interface AdminService {
    String login(String username, String userpass);
    boolean checkToken(String token);
}
