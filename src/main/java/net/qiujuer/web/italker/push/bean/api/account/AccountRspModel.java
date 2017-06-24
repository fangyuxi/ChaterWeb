package net.qiujuer.web.italker.push.bean.api.account;

import com.google.gson.annotations.Expose;
import net.qiujuer.web.italker.push.bean.card.UserCard;
import net.qiujuer.web.italker.push.bean.db.User;

public class AccountRspModel {

    //用户基本信息
    @Expose
    private UserCard user;
    //当前登录账号
    @Expose
    private String account;
    //当前token
    @Expose
    private String token;
    //是否绑定了pushid
    @Expose
    private boolean isBind;

    public AccountRspModel(User user){
        this(user,false);
    }


    public AccountRspModel(User user, boolean isBind){
        super();
        this.user = new UserCard(user);
        this.isBind = isBind;
        this.account = user.getPhone();
        this.token = user.getToken();

    }

    public UserCard getUser() {
        return user;
    }

    public void setUser(UserCard user) {
        this.user = user;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }
}
