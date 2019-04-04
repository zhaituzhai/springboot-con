package com.zhaojm.redis.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.zhaojm.redis.dao.UserAccountDTO;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener extends AnalysisEventListener<UserAccountDTO> {
    //自定义用于暂时存储data。
    //可以通过实例获取该值
    private List<UserAccountDTO> datas = new ArrayList<UserAccountDTO>();

    //每解析一行调用一次invoke方法
    public void invoke(UserAccountDTO object, AnalysisContext context) {
        System.out.println("当前行："+context.getCurrentRowNum());
        System.out.println(object);
        datas.add(object);//数据存储到list，供批量处理，或后续自己业务逻辑处理。
        doSomething(datas);//根据自己业务做处理
    }

    private void doSomething(List<UserAccountDTO> list) {

    }

    //解析结束后自动调用
    public void doAfterAllAnalysed(AnalysisContext context) {


    }
    public List<UserAccountDTO> getDatas() {
        return datas;
    }
    public void setDatas(List<UserAccountDTO> datas) {
        this.datas = datas;
    }

}
