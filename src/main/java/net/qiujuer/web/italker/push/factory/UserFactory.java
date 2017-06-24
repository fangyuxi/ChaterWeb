package net.qiujuer.web.italker.push.factory;


import net.qiujuer.web.italker.push.bean.db.User;
import org.hibernate.Session;
import utils.Hib;
import utils.TextUtil;

import javax.xml.soap.Text;

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

        User user = new User();
        user.setPassWord(password);
        user.setPhone(account);
        user.setName(name);

        Session session = Hib.session();
        session.beginTransaction();
        try{
            session.save(user);
            session.getTransaction().commit();
            return user;
        }catch (Exception e){
            session.getTransaction().rollback();
            return null;
        }
    }

    private static String encodePassword(String password){

        password = password.trim();
        password = TextUtil.getMD5(password);
        return TextUtil.encodeBase64(password);
    }
}
