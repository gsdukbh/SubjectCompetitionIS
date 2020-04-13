package werls.scis.dao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 用户
 *
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.dao.pojo
 * @Description: TODO
 * @date Date : 2020年02月19日 16:19
 */
@Entity
@Table(name = "Is_user")
public class ScisUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "user_login")
    private String login;


    //    @JsonIgnore
    @Column(name = "user_password")
    private String password;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_phone")
    private String phone;
    @Column(name = "user_sex")
    private String sex;
    @Column(name = "user_status")
    private String status;
    @Column(name = "user_identity")
    private String identity;
    @Column(name = "user_role")
    private String role;

    @Column(name = "user_avatar")
    private String avatar;
    /**
     * 用户角色
     */

//    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "Is_role_user",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @JsonIgnoreProperties({"scisUserList"})
    private List<ScisRole> roles;

    /**
     * 班级
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "class_id", referencedColumnName = "class_id")
    @JsonIgnoreProperties({"scisUserList"})
    private ScisClass scisClass;

    /**
     * 学院
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "college_id", referencedColumnName = "college_id")
    @JsonIgnoreProperties({"scisUserList"})
    private ScisCollege college;

    /**
     * 竞赛报名
     */
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "scisUser", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"scisUser"})
//    @JsonIgnore
    private List<ScisApplyFrom> applyFroms;
    /**
     * 公告教师，管理员专属，一对多
     */
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnoreProperties({"scisUser"})
    @OneToMany(mappedBy = "scisUser", fetch = FetchType.EAGER)
//    @JsonIgnore
    private List<ScisAnnouncement> announcements;

    @Fetch(FetchMode.SUBSELECT)
//    @JsonIgnore
    @JsonIgnoreProperties({"scisUserList"})
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "scisUserList")
    private List<ScisTeamApply> teamApplies;

    /*提出的问题*/
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnoreProperties({"scisUser"})
    @OneToMany(mappedBy = "scisUser", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
//    @JsonIgnore
    private List<ScisProblem> problems;


    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    @JsonIgnore
    private List<ScisCompetition> competitionList;


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<ScisCompetition> getCompetitionList() {
        return competitionList;
    }

    public void setCompetitionList(List<ScisCompetition> competitionList) {
        this.competitionList = competitionList;
    }

    public List<ScisRole> getRoles() {
        return roles;
    }

    public void setRoles(List<ScisRole> roles) {
        this.roles = roles;
    }

    public List<ScisProblem> getProblems() {
        return problems;
    }

    public void setProblems(List<ScisProblem> problems) {
        this.problems = problems;
    }

    public List<ScisTeamApply> getTeamApplies() {
        return teamApplies;
    }

    public void setTeamApplies(List<ScisTeamApply> teamApplies) {
        this.teamApplies = teamApplies;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public ScisClass getScisClass() {
        return scisClass;
    }

    public void setScisClass(ScisClass scisClass) {
        this.scisClass = scisClass;
    }

    public ScisCollege getCollege() {
        return college;
    }

    public void setCollege(ScisCollege college) {
        this.college = college;
    }

    public List<ScisApplyFrom> getApplyFroms() {
        return applyFroms;
    }

    public void setApplyFroms(List<ScisApplyFrom> applyFroms) {
        this.applyFroms = applyFroms;
    }

    public List<ScisAnnouncement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<ScisAnnouncement> announcements) {
        this.announcements = announcements;
    }


}
