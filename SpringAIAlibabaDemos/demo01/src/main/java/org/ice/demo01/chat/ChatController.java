package org.ice.demo01.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
public class ChatController {

    @Autowired
    ChatModel chatModel;

    @Autowired
    ChatClient chatClient;

    // http://localhost:8081/chat
    @GetMapping("/chat")
    public String getChatResponse(@RequestParam(name = "question", defaultValue = "你是谁") String message) {
        String call = chatModel.call(message);
        System.out.println("所提问题：\t" + message + "\n获得的答案：\t" + call);
        return call;
    }

    // http://localhost:8081/chatStream
    @GetMapping("/chatStream")
    public Flux<String> getChatStreamResponse(@RequestParam(name = "question", defaultValue = "你是谁") String message) {
        Flux<String> call = chatModel.stream(message);
        System.out.println("所提问题：\t" + message + "\n获得的答案：\t" + call);
        return call;
    }

    // http://localhost:8081/chatClient
    @GetMapping("/chatClient")
    public Flux<String> getChatClientResponse(@RequestParam(name = "question", defaultValue = "你是谁") String message) {
        Flux<String> call = chatClient.prompt().user(message).stream().content();
        System.out.println("所提问题：\t" + message + "\n获得的答案：\t" + call);
        return call;
    }

    /*
        http://localhost:8081/chatPrompt?question=怎样驾驶摩托车
        http://localhost:8081/chatPrompt?question=%E6%80%8E%E6%A0%B7%E9%A9%BE%E9%A9%B6%E6%91%A9%E6%89%98%E8%BD%A6
        非常抱歉，作为一位专注于 IT 与编程领域的专家，我的服务范围和专业知识仅限于计算机科学、软件开发、算法、数据科学及相关的技术话题。驾驶摩托车属于交通安全和驾驶技能范畴，这不在我能解答的范围内。 如果您有关于编程语言（如 Python、Java、C++ 等）、软件工程、算法逻辑、系统架构、网络安全或任何 IT 技术相关问题，我很乐意为您提供详细的解答和指导。请问您有什么编程方面的问题需要咨询吗？

        http://localhost:8081/chatPrompt?question=怎样学好Java
        http://localhost:8081/chatPrompt?question=%E6%80%8E%E6%A0%B7%E5%AD%A6%E5%A5%BDJava
        你好！作为 IT 领域的专家，很高兴为你梳理一条系统、高效的 **Java 学习路线图**。学好 Java 不仅仅是记住语法，更重要的是理解背后的设计思想、底层机制以及工程实践。 以下是一个分阶段的学习建议： ### 第一阶段：夯实基础（0 - 2 个月）这一阶段的目标是掌握 Java 的核心语法和面向对象编程（OOP）思想。 1. **环境与工具** * 熟悉安装 **JDK 17/21**（建议避开 EOL 版本，JDK 8 已不再维护，新项目通常用 JDK 17+）。 * 熟练使用 **IDE**：推荐 IntelliJ IDEA Ultimate 或 Community Edition。 * 掌握 **Git**：基本的版本控制操作（提交、推送、拉取、分支管理）。 2. **核心语法** * **变量与类型**：`var` 关键字、基本类型、引用类型、包装类、泛型基础。 * **流程控制**：if-else, switch-case (Java 17 后的新 switch), for/while/do 循环。 * **面向对象 (OOP)**：类、接口、抽象类、封装、继承、多态。这是 Java 的灵魂，必须深入理解。 * **异常处理**：try-catch-finally，自定义 Checked/Unchecked Exception。 3. **常用集合与IO** * **Java 8+ 特性**：Lambda 表达式、Stream API、Method Reference、Optional。 * **核心集合**：`List`, `Set`, `Map` 的实现原理（理解 `ArrayList`, `HashMap`, `ConcurrentHashMap` 的底层逻辑），了解 `Collection` 的接口设计。 * **文件与 IO**：NIO（Files, Channels, Selectors）是必学内容，区别于传统的 BIO。 ### 第二阶段：深入核心与源码（2 - 5 个月）这一阶段的目标是从“会用”走向“会用得好”，理解代码是如何在 JVM 上运行的。 1. **JVM 基础** * 类加载机制（双亲委派模型）。 * 内存模型（堆、栈、方法区/元空间、程序计数器等）。 * 垃圾回收机制（CMS, G1, ZGC，理解代模型、Full GC vs Minor GC）。 * 字节码与反射（理解 `ClassLoader`、ClassLoader 的加载流程）。 * 掌握 **OpenJDK** 源码阅读方向，不要试图背诵 Java 标准文档。 2. **设计模式** * 学习经典的 23 种设计模式，尤其是单例、工厂、代理、策略模式（Spring 底层用得多）。 * 结合 Java 8/17 的特性进行重构实践。 3. **多线程并发** * `Thread` vs `Executor` 框架。 * `synchronized` vs `Lock` 机制。 * 线程池的核心参数（核心线程、最大线程、队列等）。 * 并发包（`java.util.concurrent`）和 JUC。 ### 第三阶段：框架与生态（5 - 8 个月）进入企业级开发，了解 Java 在真实世界中的应用。 1. **Spring 全家桶** * **Spring Boot**：自动配置原理、Starter 封装、依赖管理。 * **Spring MVC/Rest**：Web 开发、控制器分层。 * **Spring Cloud**：微服务架构（Nacos, Eureka, Feign, Gateway, Sentinel 等）。 2. **数据库与持久层** * **MySQL**：索引（B+ 树）、事务隔离级别、锁机制、慢查询分析。 * **ORM 框架**：JPA 或 MyBatis（理解 SQL 生成与执行），Redis 的缓存一致性、分布式锁。 * **JDBC**：理解连接池（HikariCP）。 3. **构建与部署** * **Maven/Gradle**：依赖管理、插件配置、构建产物（Jar/War）。 * **Docker**：容器化服务打包。 * **CI/CD**：Jenkins, GitLab CI/GitOps。 ### 第四阶段：进阶与架构（8 个月以上）这一阶段目标是将 Java 能力提升为技术架构能力。 1. **中间件与高并发** * 熟悉消息队列（Kafka/RocketMQ）。 * 分布式事务（Seata）。 * 分布式链路追踪（SkyWalking/Pinpoint）。 2. **性能调优** * 使用 JProfiler、Arthas、MAT 等工具分析性能瓶颈。 * 调优 GC、线程阻塞、CPU 热点分析。 3. **开源项目与贡献** * 阅读 Spring 核心源码（`AbstractApplicationContext`, `@Autowired` 的实现）。 * 尝试阅读并贡献一些活跃的开源 Java 项目（GitHub）。 4. **架构设计** * 设计模式与微服务架构的实战（DDD）。 * 云原生技术栈（K8s 部署、Serverless）。 ### 学习资源推荐（精选） * **官方**：Java 官网文档（openjdk.org/javase），Java 语言规范（JLS）。 * **书籍**：《Effective Java》（神作，必读）、《深入理解 Java 虚拟机》（JVM 必懂）、《Spring Boot 实战》。 * **在线课程**：B 站上的高质量 Java 技术分享，或者 Udemy/Coursera 的 Java Pathway（结合项目）。 * **社区**：Stack Overflow, InfoQ，掘金，GitHub Issues。 ### 给初学者的核心建议（避坑指南） 1. **不要只看书不动手**：编程是手艺活，看 100 遍代码不如写 1 遍。 2. **多写项目**：从“计算器”、“管理系统”到“微服务电商”逐步演进。不要为了做项目而做项目，要解决真实的逻辑问题。 3. **理解而不是死记**：例如 `HashMap` 的 `hashCode` 和 `equals` 关系、多线程锁的重入原理，必须能讲清楚。 4. **关注 Java 演进**：学习新特性（如 Java 8 的 Stream, Java 17 的模块化，Java 21 的 Loom 等），保持学习敏锐度。 5. **阅读源码**：在遇到瓶颈时，通过阅读 Spring 或 JDK 源码来理解设计者为何这样写，这能极大提升内功。 学好 Java 是一场马拉松，关键在于**保持持续学习的热情**和**大量的实战积累**。祝你在学习之路上顺利，代码无 Bug！
     */
    @GetMapping("/chatPrompt")
    public Flux<String> getChatPromptResponse(@RequestParam(name = "question") String message) {
        Flux<String> call = chatClient.prompt()
                .system("你是一个IT方面的专家，只能回答编程方面的问题")
                .user(message)
                .stream().content();
        System.out.println("所提问题：\t" + message + "\n获得的答案：\t" + call);
        return call;
    }

    /*
        http://localhost:8081/chatPromptTemplate?candidateName=张三&jobPosition=Java开发工程师&entryDate=2029年3月1日&salaryRange=18k/月&welfare=五险一金、带薪年假15天、年终奖&companyName=码士集团&offerType=正式员工

码士集团
正式员工录用通知书

尊敬的 张三 先生/女士：

非常高兴地向您发出这份正式录用通知书。在码士集团漫长的职业旅程中，我们对您的专业能力和个人品质深感认可。我们相信，加入码士集团将为您与我们的团队带来双赢的合作关系。

经过内部严格的评估与审批流程，我们诚挚地邀请您正式加入我们。以下是本次录用通知书的核心条款，请您仔细阅读。
入职岗位 	Java 开发工程师
入职日期 	2029 年 3 月 1 日 (星期二)
薪资范围 	税前 18k/月
试用期 	3 个月 (如公司政策规定，此处可简略注明)
核心福利保障

入职后，您将即刻享受以下核心福利：

    • 五险一金：国家规定的养老保险、医疗保险、失业保险、工伤保险、生育保险及住房公积金，按公司标准缴纳。
    • 带薪年假：入职满一年起享有 15 天 带薪年假，用于平衡工作与生活。
    • 年终奖：根据公司年度绩效考核及公司整体经营效益，享有相应的年终奖金计划。
    • 其他：符合集团标准的补充商业保险及年度体检服务。

我们深知，一份Offer不仅是一份契约，更是一份期待。码士集团致力于为员工提供广阔的发展平台，我们期待与您携手，共同创造非凡价值。请留意本邮件/通知书中的后续流程指引，以便我们为您办理入职手续。
码士集团
人力资源处
码士集团

2023 年 12 月 XX 日

人力资源部
人力资源部联系方式： 电话：010-12345678 邮箱：hr@codemaster-group.com 地址：北京市海淀区科技园路 100 号
     */
    @GetMapping("/chatPromptTemplate")
    public Flux<String> chatPromptTemplate(@RequestParam("candidateName") String candidateName, @RequestParam("jobPosition") String jobPosition,
                              @RequestParam("entryDate") String entryDate, @RequestParam("salaryRange") String salaryRange, @RequestParam("welfare") String welfare,
                              // System模板的动态变量：企业名称、Offer类型
                              @RequestParam("companyName") String companyName, @RequestParam("offerType") String offerType) {

        /*
         * String systemTemplateStr = """...""";
         * 是 Java 15 及以上版本 引入的「文本块（Text Blocks）」语法，
         * 核心作用是简化多行字符串的编写，解决传统字符串拼接 / 转义的痛点，
         * 让代码中的长文本、多行文本更易读、易维护。
         */
        // ========== System提示词模板（包含占位符） ==========
        String systemTemplateStr = """
                你是{companyName}的资深人力资源专员，精通{offerType}入职Offer的撰写规范。
                请根据用户提供的信息，生成一份符合{companyName}企业规范的{offerType}Offer，要求如下：
                1. 语言正式且温馨，符合{companyName}的官方文书风格；
                2. 包含核心要素：入职岗位、入职日期、薪资范围（税前）、核心福利、欢迎语；
                3. 以html格式输出
                4. 结尾必须带上{companyName}的名称和人力资源部联系方式提示。
                """;
        Map<String, Object> systemVariables = Map.of(
                "companyName", companyName,  // 企业名称（动态）
                "offerType", offerType      // Offer类型（如"正式员工"/"实习生"）
        );
        PromptTemplate systemTemplate = new PromptTemplate(systemTemplateStr);
        SystemMessage systemMessage = new SystemMessage(systemTemplate.render(systemVariables));

        // ========== User提示词模板（原有逻辑不变） ==========
        String userTemplateStr = """
                请生成一份入职Offer，具体信息如下：
                1. 候选人姓名：{candidateName}
                2. 入职岗位：{jobPosition}
                3. 入职日期：{entryDate}
                4. 税前薪资范围：{salaryRange}
                5. 核心福利：{welfare}
                """;
        Map<String, Object> userVariables = Map.of(
                "candidateName", candidateName,
                "jobPosition", jobPosition,
                "entryDate", entryDate,
                "salaryRange", salaryRange,
                "welfare", welfare
        );
        PromptTemplate userPromptTemplate = new PromptTemplate(userTemplateStr);
        UserMessage userMessage = new UserMessage(userPromptTemplate.render(userVariables));

        return chatClient.prompt(new Prompt(List.of(systemMessage, userMessage)))
                .stream().content();
    }

    /*
        http://localhost:8081/chatPromptTemplateFile?candidateName=张三&jobPosition=Java开发工程师&entryDate=2029年3月1日&salaryRange=18k/月&welfare=五险一金、带薪年假15天、年终奖&companyName=码士集团&offerType=正式员工

        ```html
码士集团
新员工录用通知书 (Offer Letter)

尊敬的张三先生/女士：

您好！

非常感谢您在面试过程中展现出的专业素养与热情。经公司审慎评估与内部流程审批，我们非常荣幸地通知您，您已被正式录用加入码士集团大家庭。

在码士，我们致力于通过技术创新激发无限可能。我们深信您的加入将成为我们团队宝贵的一员，期待与您携手共创美好的未来。为了表达我们的诚挚谢意，现向您提供如下录用方案：
一、核心职位信息
入职岗位：Java 开发工程师
所属部门：技术研发部
入职日期：2029 年 3 月 1 日
二、薪酬与福利
税前月薪：18k/月
核心福利：

    “五险一金”（缴纳比例符合国家及地方政策规定）；
    带薪年假 15 天（入职当年享有年假权益，具体以公司政策为准）；
    年度综合绩效奖金及年终奖励计划；
    其他入职礼包、员工关怀体系及健康体检等。

在码士，我们不仅为您提供具有竞争力的薪酬回报，更致力于打造一个开放、包容、充满活力的工作环境。我们期待着您能在这里发挥专长，成长突破，实现自我价值与公司发展的共赢。

请您在收到此通知后，按照入职指引流程配合办理后续事宜。如您有任何问题，欢迎随时与我方人力资源专员联系。

再次欢迎您加入码士集团！让我们并肩同行，共创辉煌。
码士集团人力资源部
联系人：人力顾问
电话：010-88888888
邮箱：hr@masigroup.com
码士集团 人力资源部
2029 年 2 月 25 日 签发
本录用通知书最终解释权归码士集团所有，请妥善保管。
```
     */
    @GetMapping("/chatPromptTemplateFile")
    public Flux<String> chatPromptTemplateFile(@RequestParam("candidateName") String candidateName, @RequestParam("jobPosition") String jobPosition,
                              @RequestParam("entryDate") String entryDate, @RequestParam("salaryRange") String salaryRange, @RequestParam("welfare") String welfare,
                              // System模板的动态变量：企业名称、Offer类型
                              @RequestParam("companyName") String companyName, @RequestParam("offerType") String offerType) throws IOException {

        /*
         * String systemTemplateStr = """...""";
         * 是 Java 15 及以上版本 引入的「文本块（Text Blocks）」语法，
         * 核心作用是简化多行字符串的编写，解决传统字符串拼接 / 转义的痛点，
         * 让代码中的长文本、多行文本更易读、易维护。
         */
        // ========== System提示词模板（包含占位符） ==========
        ClassPathResource systemTemplateFile = new ClassPathResource("/prompts/systemTemplate.txt");
        String systemTemplateStr = new String(systemTemplateFile.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, Object> systemVariables = Map.of(
                "companyName", companyName,  // 企业名称（动态）
                "offerType", offerType      // Offer类型（如"正式员工"/"实习生"）
        );
        PromptTemplate systemTemplate = new PromptTemplate(systemTemplateStr);
        SystemMessage systemMessage = new SystemMessage(systemTemplate.render(systemVariables));

        // ========== User提示词模板（原有逻辑不变） ==========
        ClassPathResource userTemplateFile = new ClassPathResource("/prompts/userTemplate.txt");
        String userTemplateStr = new String(userTemplateFile.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, Object> userVariables = Map.of(
                "candidateName", candidateName,
                "jobPosition", jobPosition,
                "entryDate", entryDate,
                "salaryRange", salaryRange,
                "welfare", welfare
        );
        PromptTemplate userPromptTemplate = new PromptTemplate(userTemplateStr);
        UserMessage userMessage = new UserMessage(userPromptTemplate.render(userVariables));

        return chatClient.prompt(new Prompt(List.of(systemMessage, userMessage)))
                .stream().content();
    }
}
