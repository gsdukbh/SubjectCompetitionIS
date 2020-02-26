package werls.scis.dao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
@Table(name = "Is_role")
public class ScisRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id")
    private Integer id;
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

    /**
     * 角色拥有的菜单
     */
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinTable(name="Is_role_menu",
            joinColumns={@JoinColumn(name="rule_id")},
            inverseJoinColumns={@JoinColumn(name="menu_id")})

    private List<ScisMenu> menuList;

    @Override
    public String toString() {
        return "ScisRole{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                ", name='" + name + '\'' +
                ", menuList=" + menuList +
                '}';
    }

    @JsonIgnore
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

    public List<ScisMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<ScisMenu> menuList) {
        this.menuList = menuList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
