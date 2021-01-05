package com.tencent.goff.controlller;


import com.alibaba.fastjson.JSONObject;
import com.tencent.goff.model.AggregationModelBase;
import com.tencent.goff.model.AppModelTest;
import com.tencent.goff.model.JClusterNode;
import com.tencent.goff.model.JClusterOption;
import com.tencent.goff.pojo.Box;
import com.tencent.goff.pojo.MapBox;
import com.tencent.goff.supercluster.JSuperCluster;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@RestController
@RequestMapping("/cata/map/")
public class ClusterController {

    /**
     * 武汉市范围
     */
    private Box maxBox = new Box(113.41, 29.58, 115.05,31.22);

    //设置限制条件
    private JClusterOption option = new JClusterOption();
    private JSuperCluster jsc = new JSuperCluster<AppModelTest>(option);
    List<AppModelTest> testDataList = new ArrayList<>();

    /**
     * 构建kd书
     * @return
     */
    @GetMapping("/clusterTree/generate")
    public String generateTree(){

        //模拟数据库查询 把目录经纬度查询出来
        for (int i=0;i <=50;i++){
            AppModelTest test = new AppModelTest();
            test.setId(i);
            test.setLongitude(maxBox.getLeft()+i*0.025);//经度  113.41-115.05   1.64 50
            test.setLatitude(maxBox.getBottom()+ i*0.025);//维度 29.58-31.22    1.64
            testDataList.add(test);
        }

        for (int i=0;i <=100;i++){
            AppModelTest test = new AppModelTest();
            test.setId(i);
            test.setLongitude(maxBox.getLeft()+i*0.0154);//经度  113.41-115.05   1.64
            test.setLatitude(maxBox.getBottom()+ i*0.0154);//维度 29.58-31.22    1.64
            testDataList.add(test);
        }

        for (int i=0;i <=200;i++){
            AppModelTest test = new AppModelTest();
            test.setId(i);
            test.setLongitude(maxBox.getLeft()+i*0.0071);//经度  113.41-115.05   1.64
            test.setLatitude(maxBox.getBottom()+ i*0.0071);//维度 29.58-31.22    1.64
            testDataList.add(test);
        }

        //然后用这些数据生成kd树
        jsc.load(testDataList);//生成kd树

        //
        return "ok";
    }

    /**
     * 点聚合查询
     * @param box
     * @param zoom
     * @return
     */
    @GetMapping("/cluster/list")
    public String showClusterMap(Box box, @RequestParam("zoom") Integer zoom){

        List<Double> boxDouble = new ArrayList<>();
        boxDouble.add(box.getLeft());
        boxDouble.add(box.getBottom());
        boxDouble.add(box.getTop());
        boxDouble.add(box.getRight());

        //获取聚集结果
        List<JClusterNode<AppModelTest>> resultList = jsc.getClusters(boxDouble, zoom);
        /*//设置聚合节点的树叶节点
        for (JClusterNode<AppModelTest> c : resultList) {
            if (c.isCluster()) {
                c.setLeafList(jsc.getLeaves(c.getClusterId()));
            }
        }*/
        return JSONObject.toJSONString(resultList);
    }

    @GetMapping("/data/box")
    public MapBox showDataBorder(){

        OptionalDouble maxLatitude = testDataList.stream().mapToDouble(AppModelTest::getLatitude).max();
        OptionalDouble minLatitude = testDataList.stream().mapToDouble(AppModelTest::getLatitude).min();

        OptionalDouble maxLongitude = testDataList.stream().mapToDouble(AppModelTest::getLongitude).max();
        OptionalDouble minLongitude = testDataList.stream().mapToDouble(AppModelTest::getLongitude).min();

        MapBox mapBox = new MapBox();
        mapBox.setLeft(minLongitude.getAsDouble());
        mapBox.setBottom( minLatitude.getAsDouble());
        mapBox.setTop(maxLatitude.getAsDouble());
        mapBox.setRight( maxLongitude.getAsDouble());
        mapBox.setDataList(testDataList);
        return  mapBox;
    }

    public static void main(String[] args) {
        double begin = System.currentTimeMillis();
        int j = 5;

        for (int i=0;i <=1000000000;i++){
            JClusterNode jClusterNode = new JClusterNode();
            jClusterNode.setData(new AggregationModelBase());
            j=j+1;

        }
        double end = System.currentTimeMillis();
        System.out.println("100W次循环花费的时间为"+(end - begin)/1000);
    }



}
