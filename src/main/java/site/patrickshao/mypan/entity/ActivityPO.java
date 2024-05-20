package site.patrickshao.mypan.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 * @author Shao Yibo
 * @description
 * @date 2024/5/18
 */
@NotNull
@TableName("upload_activity")
public class ActivityPO {
    @TableId
    private Long id;
    @Size(max = 10)
    private String type;
    private Long fileId;
    private Long ownerId;
    private Long totalClip;
    private Long usedClip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getTotalClip() {
        return totalClip;
    }

    public void setTotalClip(Long totalClip) {
        this.totalClip = totalClip;
    }

    public Long getUsedClip() {
        return usedClip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUsedClip(Long usedClip) {
        this.usedClip = usedClip;


    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ActivityPO that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(type, that.type) && Objects.equals(fileId, that.fileId) && Objects.equals(ownerId, that.ownerId) && Objects.equals(totalClip, that.totalClip) && Objects.equals(usedClip, that.usedClip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, fileId, ownerId, totalClip, usedClip);
    }

    @Override
    public String toString() {
        return "ActivityPO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", fileId=" + fileId +
                ", ownerId=" + ownerId +
                ", totalClip=" + totalClip +
                ", usedClip=" + usedClip +
                '}';
    }
}
