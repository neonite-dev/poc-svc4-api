package com.cjt.svc4.mapper.isherpa;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.cjt.svc4.domain.CrmCode;

@Repository
@Mapper
public interface CrmCodeMapper {
    /**
   * To-Do 목록 조회
   */
  List<CrmCode> getCrmCodeList(CrmCode crmCodeRequest);

  /**
   * To-Do 상세 조회
   */
  CrmCode getCrmCodeById(String id);

  String test();
}
