package net.qiujuer.web.italker.push.factory;


import com.google.common.base.Strings;
import net.qiujuer.web.italker.push.bean.db.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import sun.nio.cs.US_ASCII;
import utils.Hib;
import utils.TextUtil;

import javax.xml.soap.Text;
import java.util.List;
import java.util.UUID;

public class UserFactory {

    //查询用户
    public static User findByPhone(String phone){
        return Hib.query(session -> (User)session.createQuery("from User where phone=:inPhone").setParameter("inPhone", phone).uniqueResult());
    }

    public static User findByName(String name){
        return Hib.query(session -> (User)session.createQuery("from User where name=:name").setParameter("name", name).uniqueResult());
    }

    public  static User findByToken(String token){
        return Hib.query(session -> (User)session.createQuery("from User where token=:token").setParameter("token", token).uniqueResult());
    }

    public static User register(String account, String password, String name){

        account = account.trim();
        password = encodePassword(password);

        User user = UserFactory.createUser(account,password,name);
        if (user != null){
            user = UserFactory.login(user);
        }
        return user;
    }

    public static User login(String account, String password){

        final String accountString = account.trim();
        final String passWordString = encodePassword(password);

        User user = Hib.query(session -> (User)session.createQuery("from User where phone=:phone and passWord=:password")
                .setParameter("phone", accountString)
                .setParameter("password",passWordString)
                .uniqueResult());
        if (user != null){
            user = login(user);
        }
        return user;

    }

    @SuppressWarnings("unchecked")
    public static User bindPushId(User user, String pushId){

        Hib.queryOnly(session -> {
            List<User> users = session.createQuery("from User where lower(pushId)=:pushId and id != :userId")
                    .setParameter("pushId",pushId.toLowerCase())
                    .setParameter("userId",user.getId()).list();

            for (User u : users){
                u.setPushId(null);
                session.saveOrUpdate(u);
            }
        });

        if (pushId.equalsIgnoreCase(user.getPushId())){
            return user;
        }else{
            if (!Strings.isNullOrEmpty(user.getPushId())){
                // TODO 发送推送，让另外一个账户掉线
            }

            user.setPushId(pushId);
            return Hib.query(session -> {
                session.saveOrUpdate(user);
                session.getTransaction().commit();
                return user;
            });
        }
    }

    private static User createUser(String account, String password, String name){
        User user = new User();
        user.setPassWord(password);
        user.setPhone(account);
        user.setName(name);

        //return Hib.query(session -> (User) session.save(user));
        return Hib.query(new Hib.Query<User>() {
            @Override
            public User query(Session session) {
                String u = (String) session.save(user);
                session.getTransaction().commit();
                return null;
            }
        });
    }

    private static User login(User user){
        String newToken = UUID.randomUUID().toString();
        newToken = TextUtil.encodeBase64(newToken);
        user.setToken(newToken);
        return Hib.query(session -> {
            session.saveOrUpdate(user);
            session.getTransaction().commit();
            return user;
        });
    }

    private static String encodePassword(String password){

        password = password.trim();
        password = TextUtil.getMD5(password);
        return TextUtil.encodeBase64(password);
    }
}
