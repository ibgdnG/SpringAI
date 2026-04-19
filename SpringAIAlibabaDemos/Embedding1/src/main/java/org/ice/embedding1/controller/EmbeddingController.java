package org.ice.embedding1.controller;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingOptions;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class EmbeddingController {

    private final EmbeddingModel embeddingModel;

    @Value("${spring.ai.ollama.embedding.options.model}")
    private String embeddingModelName;

    public EmbeddingController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    /*
        http://localhost:8085/embeding?message=%E8%8B%B9%E6%9E%9C
     */

    @GetMapping("/embeding")
    public EmbeddingResponse getEmbedding(@RequestParam(name = "message") String message) {
        EmbeddingResponse call = embeddingModel.call(new EmbeddingRequest(List.of(message), EmbeddingOptions.builder().model(embeddingModelName).build()));
        // 提取向量数据
        System.out.println(Arrays.toString(call.getResult().getOutput()));
        return call;
    }
}
