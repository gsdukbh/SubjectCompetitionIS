package werls.scis.dao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 竞赛作品，部分竞赛需要
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:09
 */
@Entity
@Table(name = "Is_works")
@EntityListeners(AuditingEntityListener.class)
public class ScisWorks  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "works_id")
    private Integer id;
    /**
     * 上传时间
     */
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @Column(name = "works_up_time")
    private Date upTime;
    /**
     * 作品名字
     */
    @Column(name = "works_name")
    private String name;
    /**
     * 作品描述
     */
    @Column(name = "works_description", columnDefinition = "text")
    private String description;

    /**
     * 作者
     */
    @Column(name = "works_author")
    private String author;

    /**
     * 分数
     */
    @Column(name = "works_score")
    private Integer score;
    @Column(name = "works_bucketName")
    private String bucketName;
    @Column(name = "works_objectName")
    private String objectName;
    @Column(name = "works_img")
    private String img;

    /**
     * 所属的竞赛项目
     */

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "competition_id", referencedColumnName = "competition_id")
    @JsonIgnoreProperties({"works"})
    private ScisCompetition competition;


    @JsonIgnoreProperties({"works"})
    @OneToOne(mappedBy = "works")
    private ScisApplyFrom applyFrom;

    @JsonIgnoreProperties({"works"})
    @OneToOne(mappedBy = "works")
    private ScisTeamApply teamApply;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ScisTeamApply getTeamApply() {
        return teamApply;
    }

    public void setTeamApply(ScisTeamApply teamApply) {
        this.teamApply = teamApply;
    }

    public ScisApplyFrom getApplyFrom() {
        return applyFrom;
    }

    public void setApplyFrom(ScisApplyFrom applyFrom) {
        this.applyFrom = applyFrom;
    }



    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }


    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public ScisCompetition getCompetition() {
        return competition;
    }

    public void setCompetition(ScisCompetition competition) {
        this.competition = competition;
    }
}
