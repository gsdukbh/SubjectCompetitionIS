package werls.scis.dao.pojo;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * 竞赛
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 22:13
 */
@Entity
@Table(name = "Is_competition")
public class ScisCompetition implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="competition_id")
    private Integer id;
    @Column(name = "competition_name")
    private String name;
    @Column(name = "competition_status")
    /**
     * 0 1 发布 or 未发布
     */
    private String status;
    @Column(name = "competition_start_time")
    private Date startTime;
    @Column(name = "competition_end_time")
    private Date endTime;
    @Column(name = "competition_content")
    private String content;
    @Column(name = "competition_url")
    private String url;
    @Column(name = "competition_author")
    private String author;
    @Column(name = "competition_level")
    private String level;
    @Column(name = "competition_organizer")
    private String organizer;
    @Column(name = "competition_num_limit")
    private String numLimit;

    /**
     * 个人报名
     */
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "competition",fetch = FetchType.EAGER)
    private List<ScisApplyFrom> applyFromList;
    /**
     * 团体报名
     */
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "competition",fetch = FetchType.EAGER)
    private List<ScisTeamApply> teamApplyList;
    /**
     * 竞赛作品
     */
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "competition",fetch = FetchType.EAGER)
    private List<ScisWorks> works;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "competition",fetch = FetchType.EAGER)
    private List<ScisProblem> problems;

    @Override
    public String toString() {
        return "ScisCompetition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", author='" + author + '\'' +
                ", level='" + level + '\'' +
                ", organizer='" + organizer + '\'' +
                ", numLimit='" + numLimit + '\'' +
                ", applyFromList=" + applyFromList +
                ", teamApplyList=" + teamApplyList +
                ", works=" + works +
                ", problems=" + problems +
                '}';
    }

    public List<ScisProblem> getProblems() {
        return problems;
    }

    public void setProblems(List<ScisProblem> problems) {
        this.problems = problems;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ScisTeamApply> getTeamApplyList() {
        return teamApplyList;
    }

    public void setTeamApplyList(List<ScisTeamApply> teamApplyList) {
        this.teamApplyList = teamApplyList;
    }

    public List<ScisWorks> getWorks() {
        return works;
    }

    public void setWorks(List<ScisWorks> works) {
        this.works = works;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getNumLimit() {
        return numLimit;
    }

    public void setNumLimit(String numLimit) {
        this.numLimit = numLimit;
    }

    public List<ScisApplyFrom> getApplyFromList() {
        return applyFromList;
    }

    public void setApplyFromList(List<ScisApplyFrom> applyFromList) {
        this.applyFromList = applyFromList;
    }
}
