package com.tencent.goff;
import com.alibaba.fastjson.JSONObject;
import com.tencent.goff.model.AppModelTest;
import com.tencent.goff.model.JClusterNode;
import com.tencent.goff.model.JClusterOption;
import com.tencent.goff.supercluster.JSuperCluster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用启动类
 */
@SpringBootApplication
public class StartupSuperCluster {

    private static Logger logger = LogManager.getLogger(StartupSuperCluster.class);

    public static void main(String[] args){
        SpringApplication.run(StartupSuperCluster.class, args);
        logger.info("Start up Java Super Cluster Success!");

        // 测试点的汇聚
        List<AppModelTest> testDataList = new ArrayList<>();
        AppModelTest test1 = new AppModelTest();
        test1.setId(1);
        test1.setLongitude(121.001000);
        test1.setLatitude(31.000100);
        testDataList.add(test1);

        for (double i=0;i <=100;i++){
            AppModelTest test3 = new AppModelTest();
            test3.setId(2);
            test3.setLongitude(121.002000+i/100);//经度
            test3.setLatitude(31.000200+ i/100);//维度
            testDataList.add(test3);
        }

        JClusterOption option = new JClusterOption();

        JSuperCluster jsc = new JSuperCluster<AppModelTest>(option);
        jsc.load(testDataList);//生成kd树

        List<Double> bbox = new ArrayList<>();
        bbox.add(121.0000);
        bbox.add(31.00);
        bbox.add(122.0000);
        bbox.add(32.00);
        List<JClusterNode<AppModelTest>> resultList = jsc.getClusters(bbox, 10);

        System.out.println("汇聚结果:" + JSONObject.toJSONString(resultList));

        System.out.println("显示聚簇点下的叶子节点：");
        for (JClusterNode<AppModelTest> c : resultList) {
            if (c.isCluster()) {
                System.out.println("汇聚节点ID：" + c.getClusterId());
                System.out.println("叶子节点：" + JSONObject.toJSONString(jsc.getLeaves(c.getClusterId())));
            }
        }
    }
}
