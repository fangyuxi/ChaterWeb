package net.qiujuer.web.italker.push.factory;


import net.qiujuer.web.italker.push.bean.db.User;
import org.hibernate.Session;
import sun.nio.cs.US_ASCII;
import utils.Hib;
import utils.TextUtil;

import javax.xml.soap.Text;
import java.util.UUID;

public class UserFactory {

    //查询用户
    public static User findByPhone(String phone){
        return Hib.query(session -> (User)session.createQuery("from User where phone=:inPhone").setParameter("inPhone", phone).uniqueResult());
    }

    public static User findByName(String name){
        return Hib.query(session -> (User)session.createQuery("from User where name=:name").setParameter("name", name).uniqueResult());
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

    private static User createUser(String account, String password, String name){
        User user = new User();
        user.setPassWord(password);
        user.setPhone(account);
        user.setName(name);

        return Hib.query(session -> (User) session.save(user));
    }

    private static User login(User user){
        String newToken = UUID.randomUUID().toString();
        newToken = TextUtil.encodeBase64(newToken);
        user.setToken(newToken);
        return Hib.query(new Hib.Query<User>() {
            @Override
            public User query(Session session) {
                session.saveOrUpdate(user);
                return user;
            }
        });
    }

    private static String encodePassword(String password){

        password = password.trim();
        password = TextUtil.getMD5(password);
        return TextUtil.encodeBase64(password);
    }
}
