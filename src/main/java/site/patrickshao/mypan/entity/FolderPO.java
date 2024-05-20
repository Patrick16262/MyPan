package site.patrickshao.mypan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Objects;

/**
 * @author Shao Yibo
 * @description
 * @date 2024/5/18
 */
@NotNull
@TableName("folder")
public class FolderPO {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String path;
    private Long ownerId;
    private Instant createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof FolderPO folderPO)) return false;
        return Objects.equals(id, folderPO.id) && Objects.equals(name, folderPO.name) && Objects.equals(path, folderPO.path) && Objects.equals(ownerId, folderPO.ownerId) && Objects.equals(createTime, folderPO.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, path, ownerId, createTime);
    }

    @Override
    public String toString() {
        return "FolderPO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", ownerId=" + ownerId +
                ", createTime=" + createTime +
                '}';
    }
}
