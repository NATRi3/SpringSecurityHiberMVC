package com.illilois.spring.service;

import com.illilois.spring.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("expireUserService")
@AllArgsConstructor
public class SessionServiceImpl implements SessionService{

    private final SessionRegistry sessionRegistry;

    @Override
    public void expireUserSessions(String username) {
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof User) {
                UserDetails userDetails = (UserDetails) principal;
                if (userDetails.getUsername().equals(username)) {
                    for (SessionInformation information : sessionRegistry
                            .getAllSessions(userDetails, true)) {
                        information.expireNow();
                        information.refreshLastRequest();
                    }
                }
            }
        }
    }

}
