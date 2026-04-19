# SpringAI

## AI 应用框架
- LangChain4j：适用于轻量化 LLM 应用、无需 Spring 生态、快速验证原型的场景（如小工具、Demo 级 RAG 应用），或非 Spring 技术栈的 Java 项目。
- Spring AI：适合跨云/跨模型、基于 Spring 生态、无厂商绑定的通用 AI 应用开发（如简单的 LLM 对话、基础 RAG 应用）。
- Spring AI Alibaba：适合基于阿里云生态、需要企业级管控、复杂智能体/工作流的生产级场景（如企业智能客服、智能办公助手、业务流程自动化 Agent）。
 
  Spring AI Alibaba 是基于 Spring AI 构建的框架，专注于与阿里云生态的深度集成。适合国内开发者，尤其是需要快速接入阿里云 AI 能力的场景。

## SpringAIAlibabaDemos
### demo01
- ChatModel 基本使用
- 流式访问

  分多次将生成内容返回，不需要长时间等待全部内容一次性返回
- ChatClient 对象调用

  封装 ChatModel 为高级客户端 API，简化调用代码，增加记忆上下文、Prompt 模板化、RAG 开发等功能，简化调用流程。
- 增加提示词（用户提供给大型语言模型 (LLM) 的一段文本，用于引导模型生成特定的输出）、提示词模板

### chatMemory1
增加对话记忆（模型在与用户进行交互式对话过程中，能够追踪、理解并利用先前对话上下文的能力），在对话中持续跟踪和理解用户的意图和上下文，从而实现更自然和连贯的对话。

手动编码，代码量大，侵入性高，灵活性高，高定制性复杂逻辑（如：敏感词过滤后不存、只存特定类型的对话、手动清理）

### ChatMemory2
chatMemroy1同功能的标准化、开箱即用的方案。

官方示例的 Advisor (顾问/拦截器) 机制，MessageChatMemoryAdvisor 是一个拦截器。当你调用 chatClient.prompt()...call() 时：
- Before: Advisor 拦截请求，根据 conversantId 自动调用 ChatMemory.get() 获取历史消息，并拼接到 Prompt 中。
- Call: 发送增强后的 Prompt 给大模型。
- After: 拿到响应后，Advisor 自动调用 ChatMemory.add() 把新的一轮对话存进去。

业务代码极其简洁，Controller 里只需要关心问题内容，不需要知道怎么存；统一治理：所有通过ChatClient 对象发出的请求都有记忆功能；用 AOP 思想解耦横切关注点（记忆）和核心业务（聊天）。

### Embedding1

向量化需要 embedding模型。就是把原始数据（文字、图片，音频等），通过embedding大模型，变成向量，每个数据对应一个向量，这么多向量会组成一个庞大的向量空间。每个向量就是向量空间中的一个点。这个过程叫向量化。
