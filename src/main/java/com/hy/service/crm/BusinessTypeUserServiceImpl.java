package com.hy.service.crm;

import com.hy.common.SecurityHelp;
import com.hy.dto.CrmBusinesstypeUserDelDto;
import com.hy.dto.CrmBusinesstypeUserDto;
import com.hy.dto.HrmResourceDto;
import com.hy.mapper.ms.CrmBusinesstypeUserMapper;
import com.hy.model.CrmBusinesstypeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BusinessTypeUserServiceImpl implements BusinessTypeUserService{

    @Autowired
    private CrmBusinesstypeUserMapper crmBusinesstypeUserMapper;

    @Override
    public List<CrmBusinesstypeUser> getBusinesstypeUser(int btid) {
        return crmBusinesstypeUserMapper.selectBusinesstypeUser(btid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setBusinesstypeUser(CrmBusinesstypeUserDto crmBusinesstypeUserDto) {
        try{
            //删除
            for (CrmBusinesstypeUserDelDto delHr: crmBusinesstypeUserDto.getrHrmResource()){
                crmBusinesstypeUserMapper.deleteBusinesstypeUser(delHr.getBtid(),delHr.getUid());
            }
            //新增
            for (HrmResourceDto addHr: crmBusinesstypeUserDto.getnHrmResource()){
                crmBusinesstypeUserMapper.insertBusinesstypeUser(new CrmBusinesstypeUser(crmBusinesstypeUserDto.getBtid(),
                        addHr.getId(),SecurityHelp.getUserId(),SecurityHelp.getUserId(),addHr.getLoginid(),
                        addHr.getLastname()));
            }
            return true;
        }
        catch (Exception e){
            throw e;
        }
    }

}
