package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import werls.scis.dao.jpa.UserRepository;
import werls.scis.dao.pojo.ScisRule;
import werls.scis.dao.pojo.ScisUser;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
        System.out.println("nnnnn:"+username);
        ScisUser user = userRepository.findByLogin(username);
        if (user == null){
          throw new UsernameNotFoundException("用户名不存在");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<ScisRule> rules = user.getRules();
        for (ScisRule rule:rules){
            authorities.add(new SimpleGrantedAuthority(rule.getAuthority()));
        }
        System.out.println(user.getRules());
        return new User(user.getLogin(),user.getPassword(),authorities);
    }

    /**
     * 保存，修改用户时调用
     * @param user 用户
     */

    public void save(ScisUser user){
        this.userRepository.save(user);
    }
}
