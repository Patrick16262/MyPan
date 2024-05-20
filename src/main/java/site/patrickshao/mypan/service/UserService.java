package site.patrickshao.mypan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.patrickshao.mypan.constant.StorageUsages;
import site.patrickshao.mypan.entity.UserPO;
import site.patrickshao.mypan.repository.UserRepository;

import java.time.Instant;

/**
 * @author Shao Yibo
 * @description
 * @date 2024/5/18
 */
@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Transactional
    public void register(String phoneNumber) {
        UserPO user = new UserPO();
        user.setPhoneNumber(phoneNumber);
        user.setCreateTime(Instant.now());
        user.setPlanId(0L);
        user.setStorageUsage(StorageUsages.PER_USER);

        repository.insert(user);

        user.setDirRoot(user.getId().toString());
        repository.updateById(user);

        createUserDir(user.getId().toString());
    }


    private void createUserDir(String dirName) {

    }
}
