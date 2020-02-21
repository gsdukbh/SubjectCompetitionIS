package werls.scis.dao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
/**
 * 用户
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

    /**
     * 用户角色
     */
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinTable(name="Is_role_user",
            joinColumns={@JoinColumn(name="rule_id")},
            inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<ScisRule> rules;
    /**
     * 用户日志
     */
    @OneToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private List<ScisLogs> logs;
    /**
     * 班级
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id",referencedColumnName = "class_id")
    private ScisClass scisClass;

    /**
     * 学院
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id",referencedColumnName = "college_id")
    private ScisCollege college;

    /**
     * 竞赛报名
     */
//    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
//    private List<ScisApplyFrom>applyFroms;
    public User() {
    }
}
