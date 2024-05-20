package site.patrickshao.mypan.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Shao Yibo
 * @description
 * @date 2024/5/18
 */

@NotNull
@TableName("plan")
public class PlanPO {
    @TableId
    private Long id;
    @Size(max = 20)
    private String name;
    private Long storageLimit;
    private Long uploadLimit;
    private Long downloadLimit;
    private Long uploadConcurrency;
    private Long downloadConcurrency;
    private Long fileSizeLimit;

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

    public Long getStorageLimit() {
        return storageLimit;
    }

    public void setStorageLimit(Long storageLimit) {
        this.storageLimit = storageLimit;
    }

    public Long getUploadLimit() {
        return uploadLimit;
    }

    public void setUploadLimit(Long uploadLimit) {
        this.uploadLimit = uploadLimit;
    }

    public Long getDownloadLimit() {
        return downloadLimit;
    }

    public void setDownloadLimit(Long downloadLimit) {
        this.downloadLimit = downloadLimit;
    }

    public Long getUploadConcurrency() {
        return uploadConcurrency;
    }

    public void setUploadConcurrency(Long uploadConcurrency) {
        this.uploadConcurrency = uploadConcurrency;
    }

    public Long getDownloadConcurrency() {
        return downloadConcurrency;
    }

    public void setDownloadConcurrency(Long downloadConcurrency) {
        this.downloadConcurrency = downloadConcurrency;
    }

    public Long getFileSizeLimit() {
        return fileSizeLimit;
    }

    public void setFileSizeLimit(Long fileSizeLimit) {
        this.fileSizeLimit = fileSizeLimit;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof PlanPO planPO)) return false;
        return Objects.equals(id, planPO.id) && Objects.equals(name, planPO.name) && Objects.equals(storageLimit, planPO.storageLimit) && Objects.equals(uploadLimit, planPO.uploadLimit) && Objects.equals(downloadLimit, planPO.downloadLimit) && Objects.equals(uploadConcurrency, planPO.uploadConcurrency) && Objects.equals(downloadConcurrency, planPO.downloadConcurrency) && Objects.equals(fileSizeLimit, planPO.fileSizeLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, storageLimit, uploadLimit, downloadLimit, uploadConcurrency, downloadConcurrency, fileSizeLimit);
    }

    @Override
    public String toString() {
        return "PlanPO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", storageLimit=" + storageLimit +
                ", uploadLimit=" + uploadLimit +
                ", downloadLimit=" + downloadLimit +
                ", uploadConcurrency=" + uploadConcurrency +
                ", downloadConcurrency=" + downloadConcurrency +
                ", fileSizeLimit=" + fileSizeLimit +
                '}';
    }
}
