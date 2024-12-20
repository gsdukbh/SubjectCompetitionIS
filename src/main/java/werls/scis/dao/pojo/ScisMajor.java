package werls.scis.dao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 专业
 *
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:01
 */
@Entity
@Table(name = "Is_major")
public class ScisMajor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id")
    private Integer id;
    @Column(name = "major_name")
    private String name;
    @Column(name = "education_level")
    private String level;


    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "college_id")
    @JsonIgnoreProperties({"majors"})
    private ScisCollege college;

    @JsonIgnore
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "major", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"major"})
    private List<ScisClass> classList;


    public List<ScisClass> getClassList() {
        return classList;
    }

    public void setClassList(List<ScisClass> classList) {
        this.classList = classList;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public ScisCollege getCollege() {
        return college;
    }

    public void setCollege(ScisCollege college) {
        this.college = college;
    }
}
