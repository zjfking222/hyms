package com.hy.service.purchase;

import com.hy.common.SecurityUtil;
import com.hy.dto.SalesmanTracerDto;
import com.hy.mapper.ms.SalesmanTracerMapper;
import com.hy.model.SalesmanTracer;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/21 16:21
 * @Description:物资采购serviceImpl
 */
@Service
public class MaterialPurchasingServiceImpl implements MaterialPurchasingService {
    @Autowired
    private SalesmanTracerMapper salesmanTracerMapper;

    @Override
    //查询当前业务员的信息以及跟单员信息
    public List<SalesmanTracerDto> getSalesmanTracer(String salesmannum){
        List<SalesmanTracer> salesmanTracer = salesmanTracerMapper.selectSalesmanTracer(salesmannum);
        return DTOUtil.populateList(salesmanTracer, SalesmanTracerDto.class);
    }

    @Override
    //查询去重后的业务员信息,用于初始化显示业务员
    public List<SalesmanTracerDto> getDistinctSalesman(String value){
        List<SalesmanTracer> salesmanTracers = salesmanTracerMapper.selectDistinctSalesman(value);
        return DTOUtil.populateList(salesmanTracers, SalesmanTracerDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    //更新业务员、跟单员信息
    public void setSalesmanTracer(String salesmannum, List<SalesmanTracerDto> salesmanTracerDtos, int[] array){
        try {
            //查询更新之前该业务员对应的所有业务员、跟单员信息
            List<SalesmanTracer> salesmanTracerList = salesmanTracerMapper.selectSalesmanTracer(salesmannum);
            int size = salesmanTracerList.size();
            Map<String, String> map = new HashMap<>();
            List<SalesmanTracer> list = new ArrayList<>();
            boolean judgeAdd = false;
            IntStream.range(0, size).forEach(i ->{
                map.put(salesmanTracerList.get(i).getTracernum(), salesmanTracerList.get(i).getTracernum());
            });
            if(salesmanTracerDtos.size() > 0){
                for(int i = 0; i < salesmanTracerDtos.size(); i++){
                    if(size > 0){
                        if(map.containsKey(salesmanTracerDtos.get(i).getTracernum())){
                            judgeAdd = true;
                        }
                    }
                    if(!judgeAdd){
                        list.add(new SalesmanTracer(salesmanTracerDtos.get(i).getSalesmannum(), salesmanTracerDtos.get(i).getSalesmanname(),
                                salesmanTracerDtos.get(i).getTracernum(), salesmanTracerDtos.get(i).getTracername(), SecurityUtil.getLoginid(),
                                SecurityUtil.getLoginid()));
                    }
                    judgeAdd = false;
                }
            }
            if(list.size() > 0){
                int add = salesmanTracerMapper.insertSalesmanTracer(list);
                if(add <= 0){
                    throw new RuntimeException("新增业务员、跟单员信息失败！");
                }
            }
            if(array.length > 0){
                int del = salesmanTracerMapper.deleteSalesmanTracer(array);
                if(del < 0){
                    throw new RuntimeException("删除业务员、跟单员信息失败！");
                }
            }
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }

    @Override
    //根据业务员员工号删除所有该业务员、跟单员信息
    public boolean delBySalesmannum(String salesmannum){
        int num = salesmanTracerMapper.selectSalesmanTracer(salesmannum).size();
        if(num > 0){
            return salesmanTracerMapper.deleteBySalesmannum(salesmannum) > 0;
        }else {
            return true;
        }

    }
}
