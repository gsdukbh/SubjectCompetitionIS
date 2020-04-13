package werls.scis.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNullApi;
import werls.scis.dao.pojo.ScisTeamUserApply;
import werls.scis.dao.pojo.ScisUser;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Query(nativeQuery = true, value = "select b.isCaptain,\n" +
            "       isApply,\n" +
            "       isRead\n" +
            "from Is_user a,\n" +
            "     Is_team_user b\n" +
            "where a.user_id = b.user_id\n" +
            "  and b.team_id = ?1\n" +
            "  and a.user_id=?2")
    Map<String, Object> tex(Integer teamId, Integer userId);

    /**
     * findById
     *
     * @param id Integer
     * @return Optional<ScisUser>
     */
    @Override
    @Query(nativeQuery = true, value = "select * from Is_user where user_id=?1")
    Optional<ScisUser> findById(Integer id);

    @Query(nativeQuery = true,
            value = "select a.user_id,\n" +
                    "       user_avatar,\n" +
                    "       user_email,\n" +
                    "       user_identity,\n" +
                    "       user_login,\n" +
                    "       user_name,\n" +
                    "       user_password,\n" +
                    "       user_phone,\n" +
                    "       user_role,\n" +
                    "       user_sex,\n" +
                    "       user_status,\n" +
                    "       college_id,\n" +
                    "       class_id\n" +
                    "from Is_user a,\n" +
                    "     Is_team_user b\n" +
                    "where a.user_id=b.user_id\n" +
                    "and b.team_id=?1")
    List<ScisUser> findByTeamId(Integer id);

    /**
     * deleteById
     *
     * @param id Integer id
     */
    @Modifying
    @Query(nativeQuery = true, value = "delete from Is_user where user_id=?1")
    @Override
    void deleteById(Integer id);


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
    Optional<ScisUser> findByEmail(String email);

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
     * 班级名称
     *
     * @param name String
     * @return Page<ScisUser>
     */
    Page<ScisUser> findByScisClassNameAndRole(String name, String role, Pageable pageable);

    /**
     * 学院名称
     *
     * @param scisClassMajorCollegeName String
     * @param pageable                  Pageable
     * @return Page<ScisUser>
     */
    Page<ScisUser> findByScisClassMajorCollegeName(String scisClassMajorCollegeName, Pageable pageable);

    /**
     * 教师 ，学院名称
     *
     * @param name     String
     * @param pageable Pageable
     * @return Page<ScisUser>
     */
    Page<ScisUser> findByCollegeName(String name, Pageable pageable);

    /**
     * 学院名称 身份
     *
     * @param name     String
     * @param role     String
     * @param pageable Pageable
     * @return Page<ScisUser>
     */
    Page<ScisUser> findByScisClassMajorCollegeNameAndRole(String name, String role, Pageable pageable);

    /**
     * 通过学生姓名，班级，专业，学院查询
     *
     * @param name        String
     * @param className   String
     * @param majorName   String
     * @param collegeName String
     * @param page        Integer
     * @param size        Integer
     * @return List<Map < String, Object>>
     */
    @Query(nativeQuery = true,
            value = "select a.user_id        as id,\n" +
                    " user_avatar as avatar," +
                    "       user_login       as login,\n" +
                    "       user_name        as name,\n" +
                    "       user_email       as email,\n" +
                    "       user_phone       as phone,\n" +
                    "       user_sex         as sex,\n" +
                    "       user_status      as status,\n" +
                    "       user_identity    as identity,\n" +
                    "       count(a.user_id) as totalElements,\n" +
                    "       b.class_name as className ,\n" +
                    "       c. major_name as majorName,\n" +
                    "       education_level as level ,\n" +
                    "       d.college_name as collegeName\n" +
                    "     \n" +
                    "from Is_user a,\n" +
                    "     Is_class b,\n" +
                    "     Is_major c,\n" +
                    "     Is_college d\n" +
                    "where a.class_id = b.class_id\n" +
                    "  and b.major_id = c.major_id\n" +
                    "  and c.college_id = d.college_id\n" +
                    "  and (a.user_name = ?1 or a.user_login = ?1 or a.user_identity = ?1)\n" +
                    "  and b.class_name = ?2\n" +
                    "  and c.major_name = ?3\n" +
                    "  and d.college_name = ?4\n" +
                    "  and a.user_role = '学生'\n" +
                    "group by a.user_id\n" +
                    "limit ?5,?6")
    List<Map<String, Object>> findNameAndClassNameAndMajorNameAndCollegeName(
            String name,
            String className,
            String majorName,
            String collegeName,
            Integer page,
            Integer size);

    /**
     * 学院名称和专业
     *
     * @param collegeName String
     * @param majorName   String
     * @param role        String
     * @param pageable    Pageable
     * @return Page<ScisUser>
     */
    Page<ScisUser> findByScisClassMajorCollegeNameAndScisClassMajorNameAndRole(
            String collegeName,
            String majorName,
            String role,
            Pageable pageable);

    /**
     * 学院 班级
     *
     * @param collegeName String
     * @param className   String
     * @param role        String
     * @param pageable    Pageable
     * @return Page<ScisUser>
     */
    Page<ScisUser> findByScisClassMajorCollegeNameAndScisClassNameAndRole(
            String collegeName,
            String className,
            String role,
            Pageable pageable);

    /**
     * 专业 班级
     *
     * @param major     String
     * @param className String
     * @param pageable  Pageable
     * @param role      String
     * @return Page<ScisUser>
     */
    Page<ScisUser> findByScisClassMajorNameAndScisClassNameAndRole(
            String major,
            String className,
            String role, Pageable pageable);


    /**
     * 专业
     *
     * @param majorName String
     * @param pageable  pageable
     * @return Page<ScisUser>
     */
    Page<ScisUser> findByScisClassMajorName(String majorName, Pageable pageable);

    /**
     * 专业和角色
     *
     * @param majorName String
     * @param role      String
     * @param pageable  Pageable
     * @return Page<ScisUser>
     */
    Page<ScisUser> findByScisClassMajorNameAndRole(String majorName, String role, Pageable pageable);

    /**
     * 名字、学号、身份证
     *
     * @param name     String
     * @param login    String
     * @param identity String
     * @param pageable Pageable
     * @return Page<ScisUser>
     */
    Page<ScisUser> findByNameOrLoginOrIdentity(String name, String login, String identity, Pageable pageable);

    /**
     * 角色名
     *
     * @param role     String
     * @param pageable Pageable
     * @return Page<ScisUser>
     */
    Page<ScisUser> findByRole(String role, Pageable pageable);

    /**
     * 。。。
     *
     * @param role     String
     * @param pageable Pageable
     * @return age<ScisUser>
     */
    Page<ScisUser> findByRoleIsNot(String role, Pageable pageable);

    @Query(nativeQuery = true,
            value = "select a.user_id as id, \n" +
                    " user_avatar as avatar," +
                    "       user_login as login,\n" +
                    "       user_name as name,\n" +
                    "       user_email as email,\n" +
                    "       user_phone  as phone,\n" +
                    "       user_sex  as sex,\n" +
                    "       user_status as status, \n" +
                    "       user_identity as identity,\n" +
                    "       count(a.user_id) as totalElements ,\n" +
                    "       b.class_name as className ,\n" +
                    "       c. major_name as majorName,\n" +
                    "       education_level as level ,\n" +
                    "       d.college_name as collegeName\n" +
                    "     \n" +
                    "from Is_user a,\n" +
                    "     Is_class b,\n" +
                    "     Is_major c,\n" +
                    "     Is_college d\n" +
                    "where a.class_id = b.class_id\n" +
                    "  and b.major_id = c.major_id\n" +
                    "  and c.college_id = d.college_id\n" +
                    "  and a.user_role = ?2\n" +
                    "  and (a.user_name = ?1 or a.user_login = ?1 or a.user_identity = ?1)\n" +
                    "group by a.user_id\n" +
                    "limit ?3,?4")
    List<Map<String, Object>> findByNameOrLoginOrIdentityAndRole(String name, String role, Integer page, Integer size);

    @Query(nativeQuery = true,
            value = "select a.user_id as id, \n" +
                    " user_avatar as avatar," +
                    "       user_login as login,\n" +
                    "       user_name as name,\n" +
                    "       user_email as email,\n" +
                    "       user_phone  as phone,\n" +
                    "       user_sex  as sex,\n" +
                    "       user_status as status, \n" +
                    "       user_identity as identity,\n" +
                    "       count(a.user_id) as totalElements ,\n" +
                    "       b.college_name as collegeName\n" +
                    "from Is_user a," +
                    "  Is_college b " +
                    "where " +
                    " b.college_id = a.college_id " +
                    "  and (a.user_name = ?1 or a.user_login = ?1 or a.user_identity = ?1)\n" +
                    "group by a.user_id\n" +
                    "limit ?2,?3")
    List<Map<String, Object>> findByNameOrLoginOrIdentityAndRole(String name, Integer page, Integer size);

    /**
     * info and collegeName
     *
     * @param info    String
     * @param college String
     * @param role    String
     * @param page    Integer
     * @param size    Integer
     * @return List<Map < String, Object>>
     */
    @Query(nativeQuery = true,
            value = "select a.user_id as id,\n" +
                    " user_avatar as avatar," +
                    "       user_login as login,\n" +
                    "       user_name as name,\n" +
                    "       user_email as email,\n" +
                    "       user_phone as phone,\n" +
                    "       user_sex as sex,\n" +
                    "       user_status as status,\n" +
                    "       user_identity  as identity,\n" +
                    "       count(a.user_id) as totalElements ,\n" +
                    "       b.class_name as className ,\n" +
                    "       c. major_name as majorName,\n" +
                    "       education_level as level ,\n" +
                    "       d.college_name as collegeName\n" +
                    "     \n" +
                    "from Is_user a,\n" +
                    "     Is_class b,\n" +
                    "     Is_major c,\n" +
                    "     Is_college d\n" +
                    "where\n" +
                    "        a.class_id = b.class_id\n" +
                    "  and b.major_id = c.major_id\n" +
                    "  and c.college_id = d.college_id\n" +
                    "  and (a.user_name = ?1 or a.user_login=?1 or a.user_identity=?1)\n" +
                    "  and d.college_name = ?2\n" +
                    "  and a.user_role= ?3  " +
                    "group by a.user_id \n" +
                    "limit ?4,?5")
    List<Map<String, Object>> findByInfoAndCollege(
            String info,
            String college,
            String role,
            Integer page, Integer size);

    /**
     * s
     *
     * @param info    String
     * @param college String
     * @param page    Integer
     * @param size    Integer
     * @return List<Map < String, Object>>
     */
    @Query(nativeQuery = true,
            value = "select a.user_id as id,\n" +
                    " user_avatar as avatar," +
                    "       user_login as login,\n" +
                    "       user_name as name,\n" +
                    "       user_email as email,\n" +
                    "       user_phone as phone,\n" +
                    "       user_sex as sex,\n" +
                    "       user_status as status,\n" +
                    "       user_identity  as identity,\n" +
                    "       count(a.user_id) as totalElements ,\n" +
                    "       b.college_name as collegeName\n" +
                    "     \n" +
                    "from Is_user a,\n" +
                    "     Is_college b \n" +
                    "where " +
                    "   a.college_id = b.college_id\n" +
                    "  and (a.user_name = ?1 or a.user_login=?1 or a.user_identity=?1)\n" +
                    "  and b.college_name = ?2\n" +
                    "group by a.user_id \n" +
                    "limit ?4,?5")
    List<Map<String, Object>> findByInfoAndCollege(
            String info,
            String college,
            Integer page, Integer size);


    /**
     * ... or className
     *
     * @param info      String
     * @param className String
     * @param page      Integer
     * @param size      Integer
     * @return List<Map < String, Object>>
     */
    @Query(nativeQuery = true,
            value = "select a.user_id as id,\n" +
                    " user_avatar as avatar," +
                    "       user_login as login, \n" +
                    "       user_name as name,\n" +
                    "       user_email as email,\n" +
                    "       user_phone as phone,\n" +
                    "       user_sex as sex,\n" +
                    "       user_status as status,\n" +
                    "       user_identity as identity ,\n" +
                    "       count(a.user_id) as totalElements ,\n" +
                    "       b.class_name as className ,\n" +
                    "       c. major_name as majorName,\n" +
                    "       education_level as level ,\n" +
                    "       d.college_name as collegeName\n" +
                    "     \n" +
                    "from Is_user a,\n" +
                    "     Is_class b,\n" +
                    "     Is_major c,\n" +
                    "     Is_college d\n" +
                    "where\n" +
                    "        a.class_id = b.class_id\n" +
                    "  and b.major_id = c.major_id\n" +
                    "  and c.college_id = d.college_id\n" +
                    "  and (a.user_name = ?1 or a.user_login=?1 or a.user_identity=?1)\n" +
                    "  and b.class_name = ?2\n" +
                    "group by a.user_id\n" +
                    "limit ?3,?4")
    List<Map<String, Object>> findByClassNameAndName(String info, String className, Integer page, Integer size);

    /**
     * ...
     *
     * @param info      String
     * @param majorName String
     * @param page      Integer
     * @param size      Integer
     * @return List<Map < String, Object>>
     */
    @Query(nativeQuery = true,
            value = "select a.user_id as id,\n" +
                    " user_avatar as avatar," +
                    "       user_login as login,\n" +
                    "       user_name as name,\n" +
                    "       user_email as email,\n" +
                    "       user_phone as phone,\n" +
                    "       user_sex as sex,\n" +
                    "       user_status as status, \n" +
                    "       user_identity as identity,\n" +
                    "       count(a.user_id) as totalElements ,\n" +
                    "       b.class_name as className ,\n" +
                    "       c. major_name as majorName,\n" +
                    "       education_level as level ,\n" +
                    "       d.college_name as collegeName \n" +
                    "     \n" +
                    "from Is_user a,\n" +
                    "     Is_class b,\n" +
                    "     Is_major c,\n" +
                    "     Is_college d\n" +
                    "where\n" +
                    "        a.class_id = b.class_id\n" +
                    "  and b.major_id = c.major_id\n" +
                    "  and c.college_id = d.college_id\n" +
                    "  and (a.user_name = ?1 or a.user_login=?1 or a.user_identity=?1)\n" +
                    "  and c.major_name = ?2\n" +
                    "group by a.user_id\n" +
                    "limit ?3,?4")
    List<Map<String, Object>> findByInfoAndMajorName(String info, String majorName, Integer page, Integer size);
}
