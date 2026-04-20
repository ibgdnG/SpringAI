package org.ice.embedding2milvus.controller;

import io.milvus.v2.client.ConnectConfig;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.service.vector.request.DeleteReq;
import io.milvus.v2.service.vector.response.DeleteResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeleteDataController {
    private static final String MILVUS_URI = "http://localhost:19530";
    private static final String TOKEN = "root:Milvus";

    /*
        http://localhost:8086/deleteData
     */
    @GetMapping("/deleteData")
    public void getCollection() {
        // 连接到 Milvus
        ConnectConfig connect = ConnectConfig.builder().uri(MILVUS_URI).token(TOKEN).build();
        MilvusClientV2 milvus = new MilvusClientV2(connect);

        // 删除数据
        DeleteResp deleteResp = milvus.delete(DeleteReq.builder().collectionName("testCollection").ids(List.of(0, 1)).build());

        System.out.println("delete collection: " + deleteResp.toString());
    }
}
