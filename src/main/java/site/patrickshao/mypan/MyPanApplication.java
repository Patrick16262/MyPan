package site.patrickshao.mypan;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootApplication
@MapperScan("site.patrickshao.mypan.repository")
public class MyPanApplication {


    private static final Logger log = LoggerFactory.getLogger(MyPanApplication.class);
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyPanApplication.class, args);
    }



}
