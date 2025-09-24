package com.cjt.svc4.service.eduapp;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjt.svc4.domain.CrmCode;
import com.cjt.svc4.mapper.eduapp.EduAppCodeMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EduAppCodeService {
    private final EduAppCodeMapper eduAppCodeMapper;

    /**
     * To-Do 조회
     */
    @Transactional(readOnly = true)
    public List<CrmCode> getCrmCodeList(CrmCode crmCodeRequest) throws Exception {
        return eduAppCodeMapper.getCrmCodeList(crmCodeRequest);
    }

    /**
     * To-Do 상세 조회
     */
    @Transactional(readOnly = true)
    public CrmCode getCrmCodeById(String id) throws Exception {
        return eduAppCodeMapper.getCrmCodeById(id);
    }

    @Transactional(readOnly = true)
    public String getTest() throws Exception {
        return eduAppCodeMapper.test();
    }

    @Transactional(readOnly = true)
    public List<CrmCode> getTest3() throws Exception {
        return eduAppCodeMapper.test3();
    }
}
