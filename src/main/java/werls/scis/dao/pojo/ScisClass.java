package werls.scis.dao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 班级
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 21:58
 */
@Entity
@Table(name = "Is_class")
public class ScisClass implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="class_id")
    private Integer id;
    @Column(name = "class_name")
    private String name;
    /**
     * 班级专业
     */

    @ManyToOne(fetch =FetchType.EAGER,optional = false)
    @JoinColumn(name = "major_id",referencedColumnName = "major_id")
    @JsonIgnoreProperties({"classList"})
    private ScisMajor major;


    @JsonIgnore
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "scisClass",fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"scisClass"})
    private  List<ScisUser> scisUserList;

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

    public ScisMajor getMajor() {
        return major;
    }

    public void setMajor(ScisMajor major) {
        this.major = major;
    }
}
