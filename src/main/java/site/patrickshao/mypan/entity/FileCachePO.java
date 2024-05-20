package site.patrickshao.mypan.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * @author Shao Yibo
 * @description
 * @date 2024/5/18
 */
@NotNull
@TableName("file_cache")
public class FileCachePO {
    @TableId
    private Long id;
    private Long fileId;
    private String type;
    @Nullable
    private Integer read_count;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Nullable
    public Integer getRead_count() {
        return read_count;
    }

    public void setRead_count(@Nullable Integer read_count) {
        this.read_count = read_count;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof FileCachePO that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(fileId, that.fileId) && Objects.equals(type, that.type) && Objects.equals(read_count, that.read_count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileId, type, read_count);
    }

    @Override
    public String toString() {
        return "FileCachePO{" +
                "id=" + id +
                ", fileId=" + fileId +
                ", type='" + type + '\'' +
                ", read_count=" + read_count +
                '}';
    }
}
