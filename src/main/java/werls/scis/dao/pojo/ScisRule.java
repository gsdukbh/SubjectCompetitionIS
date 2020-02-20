package werls.scis.dao.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 角色
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:02
 */
@Entity
@Table(name = "Is_rule")
public class ScisRule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id")
    private long id;
    @Column(name = "rule_authority")
    private String authority;

    @ManyToMany
    @JoinTable(name="Is_role_user",
            joinColumns={@JoinColumn(name="rule_id")},
            inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<User> userList;

    @Override
    public String toString() {
        return "ScisRule{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                ", userList=" + userList +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
