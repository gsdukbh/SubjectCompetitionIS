package werls.scis.dao.jpa;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import werls.scis.dao.pojo.ScisUser;

import java.util.List;

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
     * 登录名/学号/工号
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

    /**
     * 邮箱查询
     * @param email 邮箱
     * @return 用户
     */
    ScisUser findByEmail(String email);


    /**
     * 查看用户是否激活
     * @param status 状态 String
     * @param pageable Pageable
     * @return Page<ScisUser>
     */
    Page<ScisUser> findByStatus(String status, Pageable pageable);

    /**
     * 登录时调用，
     * @param login login String
     * @param phone phone String
     * @param identity identity String
     * @param email email  String
     * @return ScisUser
     */
//    @Cacheable(value = "ScisUser",key = "0")
    ScisUser findByLoginOrPhoneOrIdentityOrEmail(String login,String phone,String identity,String email);

}
