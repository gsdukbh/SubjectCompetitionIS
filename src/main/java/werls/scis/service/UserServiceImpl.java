package werls.scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import werls.scis.dao.jpa.UserRepository;
import werls.scis.dao.pojo.ScisUser;

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
        ScisUser user = userRepository.findByLogin(username);
        if (user == null){
            user = userRepository.findByPhone(username);
            if (user == null){
                user = userRepository.findByIdentity(username);
                if (user == null){
                    throw new UsernameNotFoundException("用户不存在");
                }
            }
        }
        return null;
    }
}
