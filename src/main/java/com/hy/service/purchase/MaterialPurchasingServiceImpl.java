package com.hy.service.purchase;

import com.hy.common.SecurityUtil;
import com.hy.dto.PurchaseSalesmanDto;
import com.hy.dto.PurchaseTracerDto;
import com.hy.mapper.ms.PurchaseSalesmanMapper;
import com.hy.mapper.ms.PurchaseTracerMapper;
import com.hy.model.PurchaseSalesman;
import com.hy.model.PurchaseTracer;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/21 16:21
 * @Description:物资采购serviceImpl
 */
@Service
public class MaterialPurchasingServiceImpl implements MaterialPurchasingService {
    @Autowired
    private PurchaseSalesmanMapper purchaseSalesmanMapper;

    @Autowired
    private PurchaseTracerMapper purchaseTracerMapper;

    @Override
    //查询业务员信息
    public List<PurchaseSalesmanDto> getSalesman(String value){
        List<PurchaseSalesman> salesmanTracers = purchaseSalesmanMapper.selectSalesman(value);
        return DTOUtil.populateList(salesmanTracers, PurchaseSalesmanDto.class);
    }

    @Override
    //新增业务员
    public boolean addSalesman(PurchaseSalesmanDto purchaseSalesmanDto){
        PurchaseSalesman purchaseSalesman = DTOUtil.populate(purchaseSalesmanDto, PurchaseSalesman.class);
        purchaseSalesman.setCreater(SecurityUtil.getLoginid());
        purchaseSalesman.setModifier(SecurityUtil.getLoginid());
        return purchaseSalesmanMapper.insertSalesman(purchaseSalesman) == 1;
    }

    @Override
    @Transactional
    //删除业务员时需确认采购物资中是否含有业务员，删除业务员时同时删除跟单员
    public void delSalesman(int id){
        try {
            if(purchaseSalesmanMapper.deleteSalesman(id) != 1){
                throw new RuntimeException("删除业务员失败！");
            }
            if(purchaseTracerMapper.deleteTracerBySid(id) > 0){
                throw new RuntimeException("删除跟单员失败！");
            }
        }catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    @Override
    //查询跟单员
    public List<PurchaseTracerDto> getTracer(String filters, int sid, String value){
        List<PurchaseTracer> purchaseTracers = purchaseTracerMapper.selectTracer(filters ,sid, value);
        return DTOUtil.populateList(purchaseTracers, PurchaseTracerDto.class);
    }

    @Override
    //新增跟单员
    public boolean addTracer(PurchaseTracerDto purchaseTracerDto){
        PurchaseTracer purchaseTracer = DTOUtil.populate(purchaseTracerDto, PurchaseTracer.class);
        purchaseTracer.setCreater(SecurityUtil.getLoginid());
        purchaseTracer.setModifier(SecurityUtil.getLoginid());
        return purchaseTracerMapper.insertTracer(purchaseTracer) == 1;
    }

    @Override
    //删除跟单员
    public boolean delTracer(int id){
        return purchaseTracerMapper.deleteTracer(id) == 1;
    }
}
