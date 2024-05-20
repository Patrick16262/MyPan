package site.patrickshao.mypan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.patrickshao.mypan.entity.ServerStatusPO;
import site.patrickshao.mypan.repository.ServerStatusRepository;
import site.patrickshao.mypan.service.ServerStatusService;

import java.util.Objects;

import static site.patrickshao.mypan.constant.StorageUsages.TEST_ONLY;
import static site.patrickshao.mypan.constant.StorageUsages.PER_USER;
import static site.patrickshao.mypan.constant.ServerStatusKeys.SPACE_USAGE;

/**
 * @author Shao Yibo
 * @description
 * @date 2024/5/18
 */
@SpringBootTest
public class ServerStatusServiceTest {
    @Autowired
    private ServerStatusService serverStatusService;
    @Autowired
    private ServerStatusRepository serverStatusRepository;

    @Test
    public void test() {
        serverStatusService.set(SPACE_USAGE, "0");
        serverStatusService.addUsage(TEST_ONLY);
        serverStatusService.addUsage(PER_USER);
        assert Long.parseLong(serverStatusService.use(SPACE_USAGE))
                == TEST_ONLY + PER_USER;
        serverStatusService.unuse(SPACE_USAGE);
        assert Objects.equals(serverStatusRepository.selectOne(new QueryWrapper<ServerStatusPO>()
                .eq("`key`", SPACE_USAGE)).getValue(), serverStatusService.get(SPACE_USAGE));
    }
}
