package com.hy.service.purchase;

import com.hy.common.SecurityUtil;
import com.hy.dto.PurchaseSalesmanDto;
import com.hy.mapper.ms.PurchaseSalesmanMapper;
import com.hy.model.PurchaseSalesman;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    //删除业务员时需确认采购物资中是否含有业务员，删除业务员时同时删除跟单员
    //还未完成！
    public boolean delSalesman(int id){
        return purchaseSalesmanMapper.deleteSalesman(id) == 1;
    }
}
