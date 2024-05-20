package site.patrickshao.mypan.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.patrickshao.mypan.constant.ServerStatusKeys;
import site.patrickshao.mypan.entity.ServerStatusPO;
import site.patrickshao.mypan.repository.ServerStatusRepository;
import site.patrickshao.mypan.utils.NonReentrantLockMap;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shao Yibo
 * @description
 * @date 2024/5/18
 */
@Service
@ParametersAreNonnullByDefault
public class ServerStatusService implements InitializingBean {
    @Autowired
    private ServerStatusRepository serverStatusRepository;
    private final NonReentrantLockMap<String> lockMap = new NonReentrantLockMap<>();
    private final ConcurrentHashMap<String, String> serverStatusMap = new ConcurrentHashMap<>();

    public void set(String key, String value) {
        try {
            lockMap.lockWrite(key);
            serverStatusMap.replace(key, value);
            serverStatusRepository.update(
                    new UpdateWrapper<ServerStatusPO>()
                            .eq("`key`", key)
                            .set("value", value)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lockMap.unlockWrite(key);
        }
    }

    public String use(String key) {
        String value = null;
        try {
            lockMap.lockRead(key);
            value = get(key);
            if (value == null) {
                throw new IllegalArgumentException("key not found");
            }
        } catch (Exception e) {
            lockMap.unlockRead(key);
            throw new RuntimeException(e);
        }
        return value;
    }

    public void unuse(String key) {
        lockMap.unlockRead(key);
    }

    @NotNull
    public String get(String key) {
        String value = null;
        try {
            lockMap.lockRead(key);
            value = serverStatusMap.get(key);
            if (value == null) {
                throw new IllegalArgumentException("key not found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lockMap.unlockRead(key);
        }
        return value;
    }

    @NotNull
    public void addUsage(long usage) {
        final String key = ServerStatusKeys.SPACE_USAGE;
        try {
            lockMap.lockWrite(key);
            String value = serverStatusMap.get(key);
            if (value == null) {
                throw new IllegalArgumentException("key not found");
            }
            String newString = String.valueOf(Long.parseLong(value) + usage);
            serverStatusMap.replace(key, newString);
            serverStatusRepository.update(
                    new UpdateWrapper<ServerStatusPO>()
                            .eq("`key`", key)
                            .set("value", newString)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lockMap.unlockWrite(key);
        }
    }


    @NotNull
    public void reduceUsage(long usage) {
        addUsage(-usage);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        serverStatusRepository.selectList(new QueryWrapper<>()).forEach(it ->
                serverStatusMap.put(it.getKey(), it.getValue()));
    }
}
