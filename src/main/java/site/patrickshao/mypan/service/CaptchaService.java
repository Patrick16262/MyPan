package site.patrickshao.mypan.service;

import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

/**
 * @author Shao Yibo
 * @description
 * @date 2024/5/18
 */
@Service
public class CaptchaService {
    public String generate(OutputStream out) {
        String text = null;
        try {
            GifCaptcha captcha = new GifCaptcha(110, 48, 4);
            captcha.setFont(Captcha.FONT_9);
            captcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
            captcha.out(out);
            text = captcha.text();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return text;
    }
}
