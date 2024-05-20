package site.patrickshao.mypan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.annotation.Nullable;
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
@TableName("file")
public class FilePO {
    private Long id;
    private Long ownerId;
    @Size(max = 255)
    private String name;
    @Size(max = 2048)
    private String path;
    private Long size;
    private Long diskUsage;
    @Size(max = 10)
    private String status;
    private Instant createTime;
    @Size(max = 255)
    @Nullable
    private String digest;
    @Size(max = 16)
    @Nullable
    private String digestAlgorithm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(Long diskUsage) {
        this.diskUsage = diskUsage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    @Nullable
    public String getDigest() {
        return digest;
    }

    public void setDigest(@Nullable String digest) {
        this.digest = digest;
    }

    @Nullable
    public String getDigestAlgorithm() {
        return digestAlgorithm;
    }

    public void setDigestAlgorithm(@Nullable String digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof FilePO filePO)) return false;
        return Objects.equals(id, filePO.id) && Objects.equals(ownerId, filePO.ownerId) && Objects.equals(name, filePO.name) && Objects.equals(path, filePO.path) && Objects.equals(size, filePO.size) && Objects.equals(diskUsage, filePO.diskUsage) && Objects.equals(status, filePO.status) && Objects.equals(createTime, filePO.createTime) && Objects.equals(digest, filePO.digest) && Objects.equals(digestAlgorithm, filePO.digestAlgorithm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerId, name, path, size, diskUsage, status, createTime, digest, digestAlgorithm);
    }

    @Override
    public String toString() {
        return "FilePO{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", diskUsage=" + diskUsage +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", digest='" + digest + '\'' +
                ", digestAlgorithm='" + digestAlgorithm + '\'' +
                '}';
    }
}
