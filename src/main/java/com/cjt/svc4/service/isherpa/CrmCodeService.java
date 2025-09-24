package com.cjt.svc4.service.isherpa;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjt.svc4.domain.CrmCode;
import com.cjt.svc4.mapper.isherpa.CrmCodeMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CrmCodeService {
    private final CrmCodeMapper crmCodeMapper;
    
    /**
     * To-Do 조회
     */
    @Transactional(readOnly = true)
    public List<CrmCode> getCrmCodeList(CrmCode crmCodeRequest) throws Exception {
        return crmCodeMapper.getCrmCodeList(crmCodeRequest);
    }

    /**
     * To-Do 상세 조회
     */
    @Transactional(readOnly = true)
    public CrmCode getCrmCodeById(String id) throws Exception {
        return crmCodeMapper.getCrmCodeById(id);
    }

    @Transactional(readOnly = true)
    public String getTest() throws Exception {
        return crmCodeMapper.test();
    }
}
