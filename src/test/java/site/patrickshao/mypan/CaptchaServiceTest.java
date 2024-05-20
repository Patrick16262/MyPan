package site.patrickshao.mypan;

import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.patrickshao.mypan.service.CaptchaService;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Shao Yibo
 * @description
 * @date 2024/5/19
 */
@SpringBootTest
public class CaptchaServiceTest {
    @Autowired
    CaptchaService service;
    @Test
    void test() throws Exception {
        String filename = "captcha.gif";
        var os = Files.newOutputStream(Path.of(filename));
        String text = service.generate(os);
        System.out.println(text);
    }
}
