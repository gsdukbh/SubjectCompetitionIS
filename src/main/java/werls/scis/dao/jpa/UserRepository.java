package werls.scis.dao.jpa;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import werls.scis.dao.pojo.ScisRole;
import werls.scis.dao.pojo.ScisUser;

import java.util.List;

/**
 * 用户jpa
 *
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.jpa
 * @Description: TODO
 * @date Date : 2020年02月21日 23:33
 */
public interface UserRepository extends JpaRepository<ScisUser, Integer> {
    /**
     * 登录名/学号/工号
     *
     * @param login 登录名/学号/工号
     * @return 用户
     */
    ScisUser findByLogin(String login);

    /**
     * 手机号查询
     *
     * @param phone 手机号码
     * @return 用户
     */
    ScisUser findByPhone(String phone);

    /**
     * 身份证查询
     *
     * @param identity 身份证
     * @return 用户
     */
    ScisUser findByIdentity(String identity);

    /**
     * 邮箱查询
     *
     * @param email 邮箱
     * @return 用户
     */
    ScisUser findByEmail(String email);


    /**
     * 查看用户是否激活
     *
     * @param status   状态 String
     * @param pageable Pageable
     * @return Page<ScisUser>
     */
    Page<ScisUser> findByStatus(String status, Pageable pageable);

    /**
     * 登录时调用，
     *
     * @param login    login String
     * @param phone    phone String
     * @param identity identity String
     * @param email    email  String
     * @return ScisUser
     */
    ScisUser findByLoginOrPhoneOrIdentityOrEmail(String login, String phone, String identity, String email);

    /**
     * 按照名字
     *
     * @param name     String
     * @param pageable Pageable
     * @return Page<ScisUser>
     */
    Page<ScisUser> findByName(String name, Pageable pageable);

    /**
     * find by role name
     *
     * @param name     role name
     * @param pageable pageable
     * @return Page<ScisUser>
     */
    @Query(nativeQuery = true, value = "SELECT * FROM Is_user a , Is_role b , Is_role_user c WHERE " +
            " b.role_name=?1 and b.role_id =c.role_id and " +
            " a.user_id=c.user_id")
    Page<ScisUser> findByRoleName(String name, Pageable pageable);

    /**
     * find by role name
     *
     * @param name String
     * @return List<ScisUser>
     */
    @Query(nativeQuery = true, value = "SELECT * FROM Is_user a , Is_role b , Is_role_user c WHERE " +
            " b.role_name=?1 and b.role_id =c.role_id and " +
            " a.user_id=c.user_id")
    List<ScisUser> findByRoleName(String name);

    @Query(nativeQuery = true,
            value = "select user_login from Is_user where user_login = ?1")
    String finByLogin(String login);

    /**
     * 查找所以的名
     *
     * @return List<String>
     */
    @Query(nativeQuery = true, value = "select distinct user_name from Is_user")
    List<String> findName();

    /**
     * @param name String
     * @return Page<ScisUser>
     */
    Page<ScisUser> findByScisClassName(String name, Pageable pageable);

    List<ScisUser> findByScisClassMajorCollegeName(String scisClassMajorCollegeName);

    List<ScisUser> findByCollegeName(String name);

    List<ScisUser> findByScisClassMajorCollegeNameOrCollegeName(String string,String name);
}
