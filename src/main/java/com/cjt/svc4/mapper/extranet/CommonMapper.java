package com.cjt.svc4.mapper.extranet;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.cjt.svc4.domain.extranet.Common;
import com.cjt.svc4.domain.extranet.CommonParams;
import com.cjt.svc4.domain.extranet.HeadTitleResultSets;
import com.cjt.svc4.domain.extranet.Menu;

@Repository
@Mapper
public interface CommonMapper {
    /* 
     * 메뉴리스트
     * 
     */
    List<Menu> getMenuList(CommonParams commonParamsRequest);

    /* 
     * 권한자 리스트
     * 
     */
    List<Menu> getAuthUserList(CommonParams commonParamsRequest);

    /* 
     * 마이페이지 > 사용자 정보 수정 > 실 정보 가져오기
     * Class, Level, TopId
     */
    List<Common> getDeptList(CommonParams commonParamsRequest);
    
    /* 
     * 마이페이지 팝업 > 사용자 정보수정
     * UserId, Name, Password, Phone, Ip, LoginUserId
     */
    void setUserInfo(CommonParams commonParamsRequest);

    /* 
     * 관리자 페이지 > 조직 관리 > 사용자 목록
     * Cls
     */
    List<Common> getNonDeptUserByClass(CommonParams commonParamsRequest);

    /* 
     * 메뉴권한 > 사용자 목록
     * Cls
     */
    List<Common> getUserList(CommonParams commonParamsRequest);

    /* 
     * 메뉴권한 > 필터권한 목록
     * MenuId, Cls
     */
    List<Menu> getFilter(CommonParams commonParamsRequest);

    /* 
     * 메뉴권한 > 필터권한 목록2
     * Cls
     */
    List<Menu> getFilterList(CommonParams commonParamsRequest);

    /* 
     * 메뉴권한 > 적용대상트리 사용자목록
     * Cls
     */
    List<Common> getDeptMember(CommonParams commonParamsRequest);

    /* 
     * 조직 관리 > 조직 트리 목록
     * Cls
     */
    List<Common> getDeptAssignMember(CommonParams commonParamsRequest);

    /* 
     * 기능 관리 > 기능 관리 목록 카운트
     * Cls
     */
    List<Common> getAuthListCount(CommonParams commonParamsRequest);

    /* 
     * 메뉴권한 > 메뉴권한 목록
     * UserId, Cls
     */
    List<Menu> getUserMenuAuthList(CommonParams commonParamsRequest);

    /* 
     * 메뉴 > 즐겨찾기 목록
     * UserId
     */
    List<Menu> favoritesList(CommonParams commonParamsRequest);

    /* 
     * 사용자 관리 > 사용자 목록
     * SearchMode, SearchText, Lock, PageNo, PageSize, SortExpression, TotalRowsNum
     */
    List<CommonParams> getUserInfoList(CommonParams commonParamsRequest);

    /* 
     * 메뉴 > 즐겨찾기 추가
     * UserId, MenuId
     */
    void favoritesInsert(CommonParams commonParamsRequest);

    /* 
     * 필터권한 인서트
     * MenuId, Type, UserId, Cls
     */
    void filterInsert(CommonParams commonParamsRequest);

    /* 
     * 메뉴권한 인서트
     * UserId, MenuId, Type, UserId, Cls
     */
    void menuAuthInsert(CommonParams commonParamsRequest);

    /* 
     * 조직 관리 > 조직 트리 조직명 변경
     * Cls, Id, Name
     */
    void setChangeDeptName(CommonParams commonParamsRequest);

    /* 
     * 조직 관리 > 조직 추가
     * Cls, TopId, Name, NewId
     */
    CommonParams setCreateDept(CommonParams commonParamsRequest);

    /* 
     * 조직 관리 > 사용자 조직 배정
     * Class, UserId, Id
     */
    void setAssignUserDept(CommonParams commonParamsRequest);

    /* 
     * 사용자 관리 > 사용자 부서 변경
     * Cls, UserId, Room, Team, Part
     */
    void setChangeUserDept(CommonParams commonParamsRequest);

    /* 
     * 사용자 관리 > 사용자 학교급 추가/삭제
     * Cls, UserId, Flag
     */
    void setUserChargeClass(CommonParams commonParamsRequest);

    /* 
     * 사용자 관리 > 사용자 잠금 해제
     * UserId
     */
    void setUnlockUser(CommonParams commonParamsRequest);

    /* 
     * 기능 관리 > 사용자 목록 > 등록
     * Cls, AuthType, UserId, RegUserId
     */
    CommonParams setAuth(CommonParams commonParamsRequest);
    
    /* 
     * 기능 관리 > 사용자 목록 > 삭제
     * Cls, Idx
     */
    void setAuthDelete(CommonParams commonParamsRequest);

    /* 
     * 게시판 사용자 목록 설정 > 기존 메뉴 사용자 삭제
     * MenuId, UserId
     */
    void setDeleteAuthFromMenuId(CommonParams commonParamsRequest);

    /* 
     * 메뉴 > 즐겨찾기 삭제
     * UserId, MenuId
     */
    void favoritesDelete(CommonParams commonParamsRequest);

    /* 
     * 조직 관리 > 조직 삭제
     * Cls, Id, ReturnValue
     */
    CommonParams setDeleteDept(CommonParams commonParamsRequest);

    /* 
     * 조직 관리 > 조직 배정 사용자 삭제
     * Cls, UserId, ReturnValue
     */
    CommonParams setDeleteUserDept(CommonParams commonParamsRequest);

    /* 
     * 메뉴 권한 > 권한 사용자 목록
     * Cls, AuthType, UserId, PageNo, PageSize, SortExpression, TotalRowsNum
     */
    List<CommonParams> getAuthList(CommonParams commonParamsRequest);

    /* 
     * 말머리설정 > 메뉴트리 및 말머리 목록 리스트
     * Cls
     */
    List<HeadTitleResultSets> getHeadTitleWithMenu(CommonParams commonParamsRequest);

    /* 
     * 게시판 관리 > 게시판 그룹 생성
     * UpperMenuId, MenuName, Cls, NewMenuId
     */
    CommonParams setCreateMenuGroup(CommonParams commonParamsRequest);


    /* 
     * 게시판 관리 > 게시판 삭제
     * MenuId, UserId
     */
    CommonParams setDeleteBoard(CommonParams commonParamsRequest);

    /* 
     * 게시판 관리 > 그룹 삭제
     * MenuId, UserId
     */
    CommonParams setDeleteMenuGroup(CommonParams commonParamsRequest);

    /* 
     * 게시판 관리 > 게시판 복사
     * MenuId, MenuName, cloneAuthYn, Cls,NewMenuId
     */
    CommonParams setCloneBoardWithAuth(CommonParams commonParamsRequest);

    
}
