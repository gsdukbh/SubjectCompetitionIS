package werls.scis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import werls.scis.dao.jpa.AnnouncementJpaRepository;
import werls.scis.dao.jpa.ClassJpaRepository;
import werls.scis.dao.jpa.CollegeJpaRepository;
import werls.scis.dao.jpa.UserRepository;
import werls.scis.dao.pojo.ScisAnnouncement;
import werls.scis.dao.pojo.ScisClass;
import werls.scis.dao.pojo.ScisRole;
import werls.scis.dao.pojo.ScisUser;

import javax.sound.midi.SoundbankResource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 登录验证
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

        ScisUser user = userRepository.findByLoginOrPhoneOrIdentityOrEmail(username,username,username,username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<ScisRole> rules = user.getRules();
        for (ScisRole rule : rules) {
            authorities.add(new SimpleGrantedAuthority(rule.getAuthority()));
        }
        return new User(user.getLogin(), user.getPassword(), authorities);
    }

    /**
     * 保存，修改用户时调用
     * 需要完整的user
     * @param user 用户
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(ScisUser user) {
        userRepository.save(user);
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
    /**
     * 通过用户查询
     * @param login login
     * @return ScisUser
     */
    @Transactional(rollbackFor = Exception.class)
    public ScisUser findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
    @Transactional(rollbackFor = Exception.class)
    public ScisUser findByPhone (String phone){
        return userRepository.findByPhone(phone);
    }
    @Transactional(rollbackFor = Exception.class)
    public ScisUser findByIdentity(String identity){
        return userRepository.findByIdentity(identity);
    }
    @Transactional(rollbackFor = Exception.class)
    public ScisUser findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    @Transactional(rollbackFor = Exception.class)
    public  Page<ScisUser> findByStatus(String status, Pageable pageable){
        return userRepository.findByStatus(status, pageable);
    }
}
