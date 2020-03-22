package werls.scis.dao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
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
public class ScisWorks  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="works_id")
    private Integer id;
    /**
     * 上传时间
     */
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
    @Column(name = "works_description")
    private String description;
    /**
     * 作品永久连接地址
     */
    @Column(name = "works_url")
    private String url;
    /**
     * 作品下载地址
     */
    @Column(name = "works_download")
    private String download;
    /**
     * 作者
     */
    @Column(name = "works_author")
    private String author;
    /**
     * 作品形式
     */
    @Column(name = "works_moder")
    private String moder;
    /**
     * 分数
     */
    @Column(name = "works_score")
    private Integer score;
    /**
     * 评分成员
     */
    @Column(name = "works_mem")
    private String mem;
    /**
     * 评语
     */
    @Column(name = "works_remark")
    private String remark;
    /**
     * 所属的竞赛项目
     */
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "competition_id",referencedColumnName = "competition_id")
    @JsonIgnoreProperties({"works"})
    private ScisCompetition competition;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "worksList",fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties({"worksList"})
    private List<ScisProblem> problemList;

    @Override
    public String toString() {
        return "ScisWorks{" +
                "id=" + id +
                ", upTime=" + upTime +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", download='" + download + '\'' +
                ", author='" + author + '\'' +
                ", moder='" + moder + '\'' +
                ", score=" + score +
                ", mem='" + mem + '\'' +
                ", remark='" + remark + '\'' +
                ", problemList=" + problemList +
                '}';
    }

    public List<ScisProblem> getProblemList() {
        return problemList;
    }

    public void setProblemList(List<ScisProblem> problemList) {
        this.problemList = problemList;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getModer() {
        return moder;
    }

    public void setModer(String moder) {
        this.moder = moder;
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

    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @JsonIgnore
    public ScisCompetition getCompetition() {
        return competition;
    }

    public void setCompetition(ScisCompetition competition) {
        this.competition = competition;
    }
}
