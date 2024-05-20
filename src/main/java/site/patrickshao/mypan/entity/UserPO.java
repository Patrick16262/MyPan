package site.patrickshao.mypan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Objects;

/**
 * @author Shao Yibo
 * @description
 * @date 2024/5/18
 */
@NotNull
@TableName("user")
public class UserPO {
    @TableId
    private Long id;
    @Size( max = 20)
    private String phoneNumber;
    @TableField("`password`")
    @Size(max = 255)
    private String password;
    @Size(max = 255)
    private String nickname;
    @Size(max = 255)
    private String dirRoot;
    private Instant createTime;
    private Long planId;
    private Long storageUsage;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDirRoot() {
        return dirRoot;
    }

    public void setDirRoot(String dirRoot) {
        this.dirRoot = dirRoot;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getStorageUsage() {
        return storageUsage;
    }

    public void setStorageUsage(Long storageUsage) {
        this.storageUsage = storageUsage;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof UserPO userPO)) return false;
        return Objects.equals(id, userPO.id) && Objects.equals(phoneNumber, userPO.phoneNumber) && Objects.equals(password, userPO.password) && Objects.equals(nickname, userPO.nickname) && Objects.equals(dirRoot, userPO.dirRoot) && Objects.equals(createTime, userPO.createTime) && Objects.equals(planId, userPO.planId) && Objects.equals(storageUsage, userPO.storageUsage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber, password, nickname, dirRoot, createTime, planId, storageUsage);
    }

    @Override
    public String toString() {
        return "UserPO{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", dir_root='" + dirRoot + '\'' +
                ", createTime=" + createTime +
                ", planId=" + planId +
                ", storageUsage=" + storageUsage +
                '}';
    }
}
