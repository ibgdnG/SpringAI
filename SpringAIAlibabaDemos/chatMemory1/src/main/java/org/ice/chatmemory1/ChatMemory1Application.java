package org.ice.chatmemory1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.ice.chatmemory1.mapper")  // 在启动类上添加注解，表示mapper接口所在位置
public class ChatMemory1Application {

    public static void main(String[] args) {
        SpringApplication.run(ChatMemory1Application.class, args);
    }

}
