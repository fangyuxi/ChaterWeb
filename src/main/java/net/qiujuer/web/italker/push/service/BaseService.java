package net.qiujuer.web.italker.push.service;

import net.qiujuer.web.italker.push.bean.db.User;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

public class BaseService {

    @Context
    protected SecurityContext securityContext;

     User getSelf(){
        if (securityContext == null){
            return null;
        }
        return (User)securityContext.getUserPrincipal();
    }
}
