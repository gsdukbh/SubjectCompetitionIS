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
    @Autowired
    private AnnouncementJpaRepository announcementJpaRepository ;
    @Autowired
    ClassJpaRepository classJpaRepository;

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
        System.out.println("nnnnn:" + username);

        ScisUser user = userRepository.findByLoginOrPhoneOrIdentityOrEmail(username,username,username,username);

        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<ScisRole> rules = user.getRules();
        for (ScisRole rule : rules) {
            authorities.add(new SimpleGrantedAuthority(rule.getAuthority()));
        }
        System.out.println(user.toString());

       /* Pageable pageable1= PageRequest.of(0, 2,Sort.by("time"));
        Date date=new Date();
        java.sql.Date date1=new java.sql.Date(date.getTime());
        System.out.println("时间"+this.announcementJpaRepository.findByTime(date1,pageable1).getContent().toString());
        Pageable pageable= PageRequest.of(0, 2);
        System.out.println(this.findByName("2016",pageable).getContent().toString());*/

        return new User(user.getLogin(), user.getPassword(), authorities);
    }

    /**
     * 保存，修改用户时调用
     *
     * @param user 用户
     */

    public void save(ScisUser user) {
        this.userRepository.save(user);
    }

    public Page<ScisClass> findByName(String name, Pageable pageable){
        return classJpaRepository.findByName(name,pageable);
    }
}
