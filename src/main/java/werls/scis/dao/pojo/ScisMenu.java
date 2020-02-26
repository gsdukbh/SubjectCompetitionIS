package werls.scis.dao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月24日 11:26
 */
@Entity
@Table(name = "Is_menu")
public class ScisMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="menu_id")
    private Integer id;
    /**
     * 路径
     */
    @Column(name = "url")
    private String url;
    /**
     * 目录
     */
    @Column(name = "component")
    private String component;
    /**
     * 状态
     */
    @Column(name = "keepAlive")
    private Integer keepAlive;
    /**
     * 验证
     */
    @Column(name = "requireAuth")
    private Integer requireAuth;

    /**
     * 菜单名称
     */
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "menuList")
    private List<ScisRole> rules;

    @Override
    public String toString() {
        return "ScisMenu{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", component='" + component + '\'' +
                ", keepAlive=" + keepAlive +
                ", requireAuth=" + requireAuth +
                ", name='" + name + '\'' +
                ", rules=" + rules +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
   @JsonIgnore
    public List<ScisRole> getRules() {
        return rules;
    }

    public void setRules(List<ScisRole> rules) {
        this.rules = rules;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Integer getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Integer keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Integer getRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(Integer requireAuth) {
        this.requireAuth = requireAuth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
