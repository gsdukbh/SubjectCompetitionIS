package werls.scis.dao.pojo;

import org.springframework.context.annotation.Configuration;

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
    /**
     * 角色
     */
    @Column(name = "rule_authority")
    private String authority;
    /**
     * 身份
     */
    @Column(name = "rule_name")
    private String name;
    @ManyToMany(mappedBy = "rules")
    private List<ScisUser> scisUserList;

    @Override
    public String toString() {
        return "ScisRule{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public List<ScisUser> getScisUserList() {
        return scisUserList;
    }

    public void setScisUserList(List<ScisUser> scisUserList) {
        this.scisUserList = scisUserList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
