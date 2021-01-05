package com.tencent.goff.pojo;

import com.tencent.goff.model.AppModelTest;
import lombok.Data;

import java.util.List;

@Data
public class MapBox {

    private Double left;
    private Double bottom;
    private Double top;
    private Double right;
    List<AppModelTest> dataList;

}
