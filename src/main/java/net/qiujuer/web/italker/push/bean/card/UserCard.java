package net.qiujuer.web.italker.push.bean.card;

import com.google.gson.annotations.Expose;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.qiujuer.web.italker.push.bean.db.User;

import java.time.LocalDateTime;

public class UserCard {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String phone;
    @Expose
    private String portrait;
    @Expose
    private String desc;
    @Expose
    private int sex = 0;

    // 用户关注人数量
    @Expose
    private int follows;

    // 粉丝数量
    @Expose
    private int following;
    // 我与当前uer关系
    @Expose
    private Boolean isFollow;

    public UserCard(final User user){
        this(user,false);

        // TODO 得到关注和粉丝
    }

    public UserCard(final User user, boolean isFollow){
        this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.portrait = user.getPortrait();
        this.desc = user.getDesciption();
        this.sex = user.getSex();
        this.isFollow = isFollow;
        this.modifyAt = user.getUpdateAt();

        // TODO 得到关注和粉丝
    }

    //最后一次更新时间
    @Expose

    private LocalDateTime modifyAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public LocalDateTime getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(LocalDateTime modifyAt) {
        this.modifyAt = modifyAt;
    }

    public Boolean getFollow() {
        return isFollow;
    }

    public void setFollow(Boolean follow) {
        isFollow = follow;
    }
}
