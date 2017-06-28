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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// 127.0.0.1/api/account/...
@Path("/user")
public class UserService extends BaseService{

    //更新我的用户信息
    //PUT 127.0.0.1/api/user/update
    @PUT
    @Path("/update")
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

    //获取我关注的
    //GET 127.0.0.1/api/user/followings
    @GET
    @Path("/followings")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<UserCard>> following(){
        User self = getSelf();
        List<User> users = UserFactory.getFollowing(self);
        List<UserCard> userCards = new ArrayList<>();
        for (User user:
             users) {
            userCards.add(new UserCard(user,true));
        }
        return ResponseModel.buildOk(userCards);
    }


    //关注接口
    //PUT 127.0.0.1/api/user/follow/{userId}
    @PUT
    @Path("/follow/{userId}")
    public ResponseModel<UserCard> follow(@PathParam("userId") String userId){
        User self = getSelf();
        if (self.getId().equalsIgnoreCase(userId)){
            return ResponseModel.buildParameterError();
        }
        User user = UserFactory.findById(userId);
        if (user == null){
            return ResponseModel.buildNotFoundUserError("没有发现该用户");
        }



    }
}
