package net.qiujuer.web.italker.push.service;

import net.qiujuer.web.italker.push.bean.api.account.AccountRspModel;
import net.qiujuer.web.italker.push.bean.api.account.LoginModel;
import net.qiujuer.web.italker.push.bean.api.account.RegisterModel;
import net.qiujuer.web.italker.push.bean.api.base.ResponseModel;
import net.qiujuer.web.italker.push.bean.card.UserCard;
import net.qiujuer.web.italker.push.bean.db.User;
import net.qiujuer.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author qiujuer
 */
// 127.0.0.1/api/account/...
@Path("/account")
public class AccountService {


    //POST 127.0.0.1/api/account/login
    @POST
    @Path("/login")
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> login(LoginModel model) {
        User user = UserFactory.login(model.getAccount(),model.getPassword());
        if (user != null){
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        }else{
            return ResponseModel.buildLoginError();
        }
    }


    //POST 127.0.0.1/api/account/register
    @POST
    @Path("/register")
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> register(RegisterModel model) {
        User user = UserFactory.findByPhone(model.getAccount().trim());
        if (user != null){
            return ResponseModel.buildHaveAccountError();
        }
        user = UserFactory.findByName(model.getName().trim());
        if (user != null){
            return ResponseModel.buildHaveNameError();
        }

        user = UserFactory.register(model.getAccount(),model.getPassword(),model.getName());

        if (user != null){
            AccountRspModel rspModel = new AccountRspModel(user,false);
            return ResponseModel.buildOk(rspModel);
        }else {
            return ResponseModel.buildRegisterError();
        }
    }

}
