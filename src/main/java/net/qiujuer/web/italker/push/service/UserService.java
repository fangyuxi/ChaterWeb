package net.qiujuer.web.italker.push.service;


import net.qiujuer.web.italker.push.bean.api.account.AccountRspModel;
import net.qiujuer.web.italker.push.bean.api.account.LoginModel;
import net.qiujuer.web.italker.push.bean.api.base.ResponseModel;
import net.qiujuer.web.italker.push.bean.api.user.UpdateInfoModel;
import net.qiujuer.web.italker.push.bean.card.UserCard;
import net.qiujuer.web.italker.push.bean.db.User;
import net.qiujuer.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

// 127.0.0.1/api/account/...
@Path("/user")
public class UserService extends BaseService{



    //POST 127.0.0.1/api/user/update
    @PUT
    //@Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> update(@HeaderParam("token")String token, UpdateInfoModel model){
        if (!UpdateInfoModel.check(model)){
            return ResponseModel.buildParameterError();
        }

        User self = getSelf();
        self = model.updateToUser(self);
        self = UserFactory.update(self);
        return ResponseModel.buildOk(new UserCard(self,true));

    }
}
