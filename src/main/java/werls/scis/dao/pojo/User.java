package werls.scis.dao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 16:19
 */
@Entity
@Table(name = "Is_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id")
    private long id;
    @Column(name = "class_id")
    private long classId;
    @Column(name = "college_id")
    private long collegeId;
    @Column(name = "user_login")
    private String login;
    @Column(name = "user_password")
    private String password;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_phone")
    private String phone;
    @Column(name = "user_sex")
    private String sex;
    @Column(name = "user_status")
    private String status;
    @Column(name = "user_identity")
    private String identity;
    /*配置用户角色*/

    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinTable(name="Is_role_user",
            joinColumns={@JoinColumn(name="rule_id")},
            inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<ScisRule> rules;

    public User() {
    }
}
