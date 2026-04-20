package org.ice.embedding2milvus.controller;

import io.milvus.v2.client.ConnectConfig;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.service.vector.request.GetReq;
import io.milvus.v2.service.vector.response.GetResp;
import io.milvus.v2.service.vector.response.QueryResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QueryDataController {
    private static final String MILVUS_URI = "http://localhost:19530";
    private static final String TOKEN = "root:Milvus";

    /*
        http://localhost:8086/queryData
     */
    @GetMapping("/queryData")
    public void getCollection() {
        // 连接到 Milvus
        ConnectConfig connect = ConnectConfig.builder().uri(MILVUS_URI).token(TOKEN).build();
        MilvusClientV2 milvus = new MilvusClientV2(connect);

        // 查询数据
        GetResp getResp = milvus.get(GetReq.builder().collectionName("testCollection").ids(List.of(1, 2, 3)).outputFields(List.of("id", "color")).build());

        for (QueryResp.QueryResult getResult : getResp.getGetResults()) {
            System.out.println("data: " + getResult.toString());
        }
    }
}
