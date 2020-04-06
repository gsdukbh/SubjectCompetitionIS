package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.UserRepository;
import werls.scis.dao.pojo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 登录验证
 *
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.service
 * @Description: TODO
 * @date Date : 2020年02月21日 23:23
 */
@Service

public class UserServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public Page<ScisUser> findByRoleIsNot(String role, Pageable pageable) {
        return userRepository.findByRoleIsNot(role, pageable);
    }

    public Page<ScisUser> findByRole(String role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    public List<Map<String, Object>> findByNameOrLoginOrIdentityAndRole(String name, String role, Integer page, Integer size) {
        return userRepository.findByNameOrLoginOrIdentityAndRole(name, role, page, size);
    }

    public Page<ScisUser> findByScisClassMajorNameAndRole(String majorName, String role, Pageable pageable) {
        return userRepository.findByScisClassMajorNameAndRole(majorName, role, pageable);
    }

    public Page<ScisUser> findByCollegeNameAndRole(String name, String role, Pageable pageable) {
        return userRepository.findByScisClassMajorCollegeNameAndRole(name, role, pageable);
    }

    public List<Map<String, Object>> findByInfoAndCollege(
            String info,
            String college,
            String role,
            Integer page, Integer size) {
        return userRepository.findByInfoAndCollege(info, college, role, page, size);
    }

    public Page<ScisUser> findByScisClassMajorNameAndScisClassNameAndRole(
            String major,
            String className,
            String role, Pageable pageable) {
        return userRepository.findByScisClassMajorNameAndScisClassNameAndRole(major, className, role, pageable);
    }

    public Page<ScisUser> findByScisClassMajorCollegeNameAndScisClassNameAndRole(
            String collegeName,
            String className,
            String role,
            Pageable pageable) {
        return userRepository.findByScisClassMajorCollegeNameAndScisClassNameAndRole(collegeName, className, role, pageable);
    }

    public Page<ScisUser> findByScisClassMajorCollegeNameAndScisClassMajorNameAndRole(
            String collegeName,
            String majorName,
            String role,
            Pageable pageable) {
        return userRepository.findByScisClassMajorCollegeNameAndScisClassMajorNameAndRole(collegeName, majorName, role, pageable);
    }

    public List<Map<String, Object>> findByInfoAndMajorName(String info, String majorName, Integer page, Integer size) {
        return userRepository.findByInfoAndMajorName(info, majorName, page, size);
    }

    public List<Map<String, Object>> findByClassNameAndName(String info, String className, Integer page, Integer size) {
        return userRepository.findByClassNameAndName(info, className, page, size);
    }


    public Page<ScisUser> findByScisClassMajorName(String majorName, Pageable pageable) {
        return userRepository.findByScisClassMajorName(majorName, pageable);
    }

    public Page<ScisUser> findByNameOrLoginOrIdentity(String name, String login, String identity, Pageable pageable) {
        return userRepository.findByNameOrLoginOrIdentity(name, login, identity, pageable);
    }

    public Page<ScisUser> findByCollegeName(String name, Pageable pageable) {
        return userRepository.findByCollegeName(name, pageable);
    }

    public Page<ScisUser> findByScisClassMajorCollegeName(String scisClassMajorCollegeName, Pageable pageable) {
        return userRepository.findByScisClassMajorCollegeName(scisClassMajorCollegeName, pageable);
    }

    public List<Map<String, Object>> findNameAndClassNameAndMajorNameAndCollegeName(
            String name,
            String className,
            String majorName,
            String collegeName,
            Integer page,
            Integer size) {
        return userRepository.findNameAndClassNameAndMajorNameAndCollegeName(name, className, majorName, collegeName, page, size);
    }

    public Page<ScisUser> findByScisClassNameAndRole(String name, String role,Pageable pageable) {
        return userRepository.findByScisClassNameAndRole(name, role,pageable);
    }

    public List<String> findName() {
        return userRepository.findName();
    }

    public String finByLogin(String login) {
        return userRepository.finByLogin(login);
    }

    /**
     * find by role name
     *
     * @param name String
     * @return List<ScisUser>
     */
    public List<ScisUser> findByRoleName(String name) {
        return userRepository.findByRoleName(name);
    }

    /**
     * 保存，修改用户时调用
     * 需要完整的user
     *
     * @param user 用户
     */

    @Transactional(rollbackFor = Exception.class)
    public ScisUser save(ScisUser user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List<ScisUser> user) {
        userRepository.saveAll(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ScisUser> findAll() {
        return userRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public Page<ScisUser> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<ScisUser> findByRoleName(String name, Pageable pageable) {
        return userRepository.findByRoleName(name, pageable);
    }

    /**
     * 通过用户查询
     *
     * @param login login
     * @return ScisUser
     */

    public ScisUser findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public ScisUser findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public ScisUser findByIdentity(String identity) {
        return userRepository.findByIdentity(identity);
    }

    public ScisUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Page<ScisUser> findByStatus(String status, Pageable pageable) {
        return userRepository.findByStatus(status, pageable);
    }

    public Optional<ScisUser> findById(Integer id) {
        return userRepository.findById(id);
    }

    public void delete(ScisUser user) {
        this.userRepository.delete(user);
    }

    public void deleteAll(List<ScisUser> user) {
        this.userRepository.deleteAll(user);
    }

    public void deleteById(Integer id) {
        this.userRepository.deleteById(id);
    }

    /*查询时非常的久*/
    public ScisUser findByLoginOrPhoneOrIdentityOrEmail(String login) {
        return this.userRepository.findByLoginOrPhoneOrIdentityOrEmail(login, login, login, login);
    }


    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ScisUser user = findByLoginOrPhoneOrIdentityOrEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<ScisRole> rules = user.getRoles();
        for (ScisRole rule : rules) {
            authorities.add(new SimpleGrantedAuthority(rule.getAuthority()));
        }
        return new User(user.getLogin(), user.getPassword(), authorities);
    }
}
