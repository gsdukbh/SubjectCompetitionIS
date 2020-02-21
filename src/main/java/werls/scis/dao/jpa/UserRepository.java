package werls.scis.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import werls.scis.dao.pojo.ScisUser;

/**
 * 用户jpa
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月21日 23:33
 */
public interface UserRepository extends JpaRepository<ScisUser,Integer> {
    /**
     * 登录名/学号/工号，查找用户
     * @param login 登录名/学号/工号
     * @return 用户
     */
    ScisUser findByLogin (String login);

    /**
     * 手机号查询
     * @param phone 手机号码
     * @return 用户
     */
    ScisUser findByPhone (String phone);

    /**
     * 身份证查询
     * @param identity 身份证
     * @return 用户
     */
    ScisUser findByIdentity (String identity);
}
