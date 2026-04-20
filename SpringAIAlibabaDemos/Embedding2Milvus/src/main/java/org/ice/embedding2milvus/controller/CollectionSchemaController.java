package org.ice.embedding2milvus.controller;

import io.milvus.v2.client.ConnectConfig;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.common.DataType;
import io.milvus.v2.common.IndexParam;
import io.milvus.v2.service.collection.request.AddFieldReq;
import io.milvus.v2.service.collection.request.CreateCollectionReq;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CollectionSchemaController {
    private static final String MILVUS_URI = "http://localhost:19530";
    private static final String TOKEN = "root:Milvus";

    /*
        http://localhost:8086/collection
     */
    @GetMapping("/collection")
    public void getCollection() {
        // 连接到 Milvus
        ConnectConfig connect = ConnectConfig.builder().uri(MILVUS_URI).token(TOKEN).build();
        MilvusClientV2 milvus = new MilvusClientV2(connect);

        // 创建 Collection
        String collectionName = "testCollection";
        createCollection(milvus, collectionName);

        // 列出 collection
        List<String> collectionNames = milvus.listCollections().getCollectionNames();
        System.out.println("Collection names: " + collectionNames);
    }

    /**
     * 创建 Milvus collection
     *
     * @param milvus         Milvus 客户端
     * @param collectionName collection 名称
     */
    private void createCollection(MilvusClientV2 milvus, String collectionName) {
        // 创建 schema
        CreateCollectionReq.CollectionSchema schema = MilvusClientV2.CreateSchema()
                .addField(AddFieldReq.builder().fieldName("id").dataType(DataType.Int64).isPrimaryKey(true).autoID(false).build())
                .addField(AddFieldReq.builder().fieldName("vector").dataType(DataType.FloatVector).dimension(5).build())
                .addField(AddFieldReq.builder().fieldName("color").dataType(DataType.VarChar).maxLength(512).build());

        //  构建索引
        List<IndexParam> indexParams = new ArrayList<>();
        IndexParam vector = IndexParam.builder().fieldName("vector").indexType(IndexParam.IndexType.IVF_FLAT).metricType(IndexParam.MetricType.COSINE).build();
        indexParams.add(vector);

        // 创建 collection
        milvus.createCollection(CreateCollectionReq.builder().collectionName(collectionName).collectionSchema(schema).indexParams(indexParams).build());
    }

}
