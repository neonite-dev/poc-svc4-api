package com.cjt.svc4.service.extranet;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjt.svc4.domain.extranet.Common;
import com.cjt.svc4.domain.extranet.CommonParams;
import com.cjt.svc4.domain.extranet.HeadTitleResultSets;
import com.cjt.svc4.domain.extranet.Menu;
import com.cjt.svc4.mapper.extranet.CommonMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommonService {
    private final CommonMapper commonMapper;

    @Transactional(readOnly = true)
    public List<Menu> getMenuList(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.getMenuList(commonParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Menu> getAuthUserList(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.getAuthUserList(commonParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Common> getDeptList(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.getDeptList(commonParamsRequest);
    }

    @Transactional
    public void setUserInfo(CommonParams commonParamsRequest) throws Exception {
        commonMapper.setUserInfo(commonParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Common> getNonDeptUserByClass(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.getNonDeptUserByClass(commonParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Common> getUserList(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.getUserList(commonParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Menu> getFilter(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.getFilter(commonParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Menu> getFilterList(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.getFilterList(commonParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Common> getDeptMember(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.getDeptMember(commonParamsRequest);
    }
    
    @Transactional(readOnly = true)
    public List<Common> getDeptAssignMember(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.getDeptAssignMember(commonParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Common> getAuthListCount(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.getAuthListCount(commonParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Menu> getUserMenuAuthList(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.getUserMenuAuthList(commonParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Menu> favoritesList(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.favoritesList(commonParamsRequest);
    }

    @Transactional
    public List<CommonParams> getUserInfoList(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.getUserInfoList(commonParamsRequest);
    }
     
    @Transactional
    public void favoritesInsert(CommonParams commonParamsRequest) throws Exception {
        commonMapper.favoritesInsert(commonParamsRequest);
    }
     
    @Transactional
    public void filterInsert(CommonParams commonParamsRequest) throws Exception {
        commonMapper.filterInsert(commonParamsRequest);
    }
    
    @Transactional
    public void menuAuthInsert(CommonParams commonParamsRequest) throws Exception {
        commonMapper.menuAuthInsert(commonParamsRequest);
    }

    @Transactional
    public void setChangeDeptName(CommonParams commonParamsRequest) throws Exception {
        commonMapper.setChangeDeptName(commonParamsRequest);
    }

    @Transactional
    public CommonParams setCreateDept(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.setCreateDept(commonParamsRequest);
    }

    @Transactional
    public void setAssignUserDept(CommonParams commonParamsRequest) throws Exception {
        commonMapper.setAssignUserDept(commonParamsRequest);
    }
    
    @Transactional
    public void setChangeUserDept(CommonParams commonParamsRequest) throws Exception {
        commonMapper.setChangeUserDept(commonParamsRequest);
    }
    
    @Transactional
    public void setUserChargeClass(CommonParams commonParamsRequest) throws Exception {
        commonMapper.setUserChargeClass(commonParamsRequest);
    }
    
    @Transactional
    public void setUnlockUser(CommonParams commonParamsRequest) throws Exception {
        commonMapper.setUnlockUser(commonParamsRequest);
    }
    
    @Transactional
    public CommonParams setAuth(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.setAuth(commonParamsRequest);
    }
    
    @Transactional
    public void setAuthDelete(CommonParams commonParamsRequest) throws Exception {
        commonMapper.setAuthDelete(commonParamsRequest);
    }

    @Transactional
    public void setDeleteAuthFromMenuId(CommonParams commonParamsRequest) throws Exception {
        commonMapper.setDeleteAuthFromMenuId(commonParamsRequest);
    }
    
    @Transactional
    public void favoritesDelete(CommonParams commonParamsRequest) throws Exception {
        commonMapper.favoritesDelete(commonParamsRequest);
    }
 
    @Transactional
    public CommonParams setDeleteDept(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.setDeleteDept(commonParamsRequest);
    }

    @Transactional
    public CommonParams setDeleteUserDept(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.setDeleteUserDept(commonParamsRequest);
    }
    
    @Transactional
    public List<CommonParams> getAuthList(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.getAuthList(commonParamsRequest);
    }
    
    @Transactional(readOnly = true)
    public List<HeadTitleResultSets> getHeadTitleWithMenu(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.getHeadTitleWithMenu(commonParamsRequest);
    }
    
    @Transactional
    public CommonParams setCreateMenuGroup(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.setCreateMenuGroup(commonParamsRequest);
    }

    @Transactional
    public CommonParams setDeleteBoard(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.setDeleteBoard(commonParamsRequest);
    }

    @Transactional
    public CommonParams setDeleteMenuGroup(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.setDeleteMenuGroup(commonParamsRequest);
    }

    @Transactional
    public CommonParams setCloneBoardWithAuth(CommonParams commonParamsRequest) throws Exception {
        return commonMapper.setCloneBoardWithAuth(commonParamsRequest);
    }
    
}
