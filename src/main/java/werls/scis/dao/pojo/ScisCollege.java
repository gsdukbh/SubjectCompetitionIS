package werls.scis.dao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 学院
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:00
 */
@Entity
@Table(name = "Is_college")
public class ScisCollege implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "college_id")
    private Integer id;
    @Column(name = "college_name")
    private String name;
    /**
     *开设的专业
     */
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "college",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"college"})
    private List<ScisMajor> majors;
    /**
     * 学院教师
     */
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "college",fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"college"})
    private List<ScisUser> scisUserList;

    public ScisCollege() {
        super();
    }


    public List<ScisUser> getScisUserList() {
        return scisUserList;
    }

    public void setScisUserList(List<ScisUser> scisUserList) {
        this.scisUserList = scisUserList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScisMajor> getMajors() {
        return majors;
    }

    public void setMajors(List<ScisMajor> majors) {
        this.majors = majors;
    }
}
