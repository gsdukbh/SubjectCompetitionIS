package werls.scis.dao.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 竞赛
 *
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competition_id")
    private Integer id;
    @Column(name = "competition_name")
    private String name;
    @Column(name = "competition_status")
    private String status;
    @Column(name = "competition_start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date startTime;
    @Column(name = "competition_end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date endTime;
    @Column(name = "competition_apply_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date applyTime;
    @Column(name = "competition_apply_stop")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date applyStop;
    @Column(name = "competition_content", columnDefinition = "text")
    private String content;
    @Column(name = "competition_author")
    private String author;
    @Column(name = "competition_principal")
    private String principal;
    @Column(name = "competition_level")
    private String level;
    @Column(name = "competition_organizer")
    private String organizer;
    @Column(name = "competition_num_limit")
    private String numLimit;
    @Column(name = "competition_place")
    private String place;
    @Column(name = "competition_type")
    private String type;
    @Column(name = "competition_isTeam")
    private Boolean team;

    @Column(name = "competition_bucketName")
    private String bucketName;
    @Column(name = "competition_objectName")
    private String objectName;

    @Column(name = "competition_img")
    private String img;
    /**
     * 个人报名
     */
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "competition", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"competition"})
    @JsonIgnore
    private List<ScisApplyFrom> applyFromList;
    /**
     * 团体报名
     */
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "competition", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"competition"})
    @JsonIgnore
    private List<ScisTeamApply> teamApplyList;
    /**
     * 竞赛作品
     */
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "competition", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"competition"})
    @JsonIgnore
    private List<ScisWorks> works;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "competition", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"competition"})
    @JsonIgnore
    private List<ScisProblem> problems;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonIgnoreProperties({"competitionList"})
    @JsonIgnore
    private ScisUser user;

    @Override
    public String toString() {
        return "ScisCompetition{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", applyTime=" + applyTime +
                '}';
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public ScisUser getUser() {
        return user;
    }

    public void setUser(ScisUser user) {
        this.user = user;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Boolean getTeam() {
        return team;
    }

    public void setTeam(Boolean team) {
        this.team = team;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }


    public List<ScisProblem> getProblems() {
        return problems;
    }

    public void setProblems(List<ScisProblem> problems) {
        this.problems = problems;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScisCompetition)) {
            return false;
        }
        ScisCompetition that = (ScisCompetition) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                Objects.equals(status, that.status) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(applyTime, that.applyTime) &&
                Objects.equals(content, that.content) &&
                Objects.equals(author, that.author) &&
                Objects.equals(principal, that.principal) &&
                Objects.equals(level, that.level) &&
                Objects.equals(organizer, that.organizer) &&
                Objects.equals(numLimit, that.numLimit) &&
                Objects.equals(place, that.place) &&
                Objects.equals(type, that.type) &&
                Objects.equals(team, that.team) &&
                Objects.equals(bucketName, that.bucketName) &&
                Objects.equals(objectName, that.objectName) &&
                Objects.equals(img, that.img) &&
                Objects.equals(applyFromList, that.applyFromList) &&
                Objects.equals(teamApplyList, that.teamApplyList) &&
                Objects.equals(works, that.works) &&
                Objects.equals(problems, that.problems) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, startTime, endTime, applyTime, content, author, principal, level, organizer, numLimit, place, type, team, bucketName, objectName, img, applyFromList, teamApplyList, works, problems, user);
    }
}
