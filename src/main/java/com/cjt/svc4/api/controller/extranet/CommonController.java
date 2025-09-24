package com.cjt.svc4.api.controller.extranet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cjt.svc4.domain.extranet.Common;
import com.cjt.svc4.domain.extranet.CommonParams;
import com.cjt.svc4.domain.extranet.HeadTitleResultSets;
import com.cjt.svc4.domain.extranet.Menu;
import com.cjt.svc4.service.extranet.CommonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Common Controller API", description = "공통 API")
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/extra/common")
public class CommonController {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    private final CommonService commonService;

    @Operation(summary = "메뉴 리스트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "menuList")
    public List<Menu> getMenuList(HttpServletRequest request, HttpServletResponse response,
            CommonParams commonParamsRequest) throws Exception {
        return commonService.getMenuList(commonParamsRequest);
    }

    @Operation(summary = "권한자 리스트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "authUserList")
    public List<Menu> getAuthUserList(HttpServletRequest request, HttpServletResponse response,
            CommonParams commonParamsRequest) throws Exception {
        return commonService.getAuthUserList(commonParamsRequest);
    }

    @Operation(summary = "실 정보", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "deptList")
    public List<Common> getDeptList(CommonParams commonParamsRequest) throws Exception {
        return commonService.getDeptList(commonParamsRequest);
    }
    
    @Operation(summary = "마이페이지 정보 수정", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "updateInfo")
    public void setUserInfo(CommonParams commonParamsRequest) throws Exception {
        commonService.setUserInfo(commonParamsRequest);
    }

    @Operation(summary = "조직에 속하지 않은 사용자 리스트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "nonDeptUserList")
    public List<Common> getNonDeptUserByClass(CommonParams commonParamsRequest) throws Exception {
        return commonService.getNonDeptUserByClass(commonParamsRequest);
    }

    @Operation(summary = "학급별 사용자 리스트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "userList")
    public List<Common> getUserList(CommonParams commonParamsRequest) throws Exception {
        return commonService.getUserList(commonParamsRequest);
    }

    @Operation(summary = "메뉴권한 > 필터권한 목록", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "getFilter")
    public List<Menu> getFilter(CommonParams commonParamsRequest) throws Exception {
        return commonService.getFilter(commonParamsRequest);
    }

    @Operation(summary = "메뉴권한 > 필터권한 목록2", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "getFilterList")
    public List<Menu> getFilterList(CommonParams commonParamsRequest) throws Exception {
        return commonService.getFilterList(commonParamsRequest);
    }

    @Operation(summary = "메뉴권한 > 적용대상트리 사용자목록", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "getDeptMember")
    public List<Common> getDeptMember(CommonParams commonParamsRequest) throws Exception {
        return commonService.getDeptMember(commonParamsRequest);
    }

    @Operation(summary = "조직 관리 > 조직 트리 목록", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "getDeptAssignMember")
    public List<Common> getDeptAssignMember(CommonParams commonParamsRequest) throws Exception {
        return commonService.getDeptAssignMember(commonParamsRequest);
    }

    @Operation(summary = "기능 관리 > 기능 관리 목록 카운트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "getAuthListCount")
    public List<Common> getAuthListCount(CommonParams commonParamsRequest) throws Exception {
        return commonService.getAuthListCount(commonParamsRequest);
    }

    @Operation(summary = "메뉴권한 > 메뉴권한 목록", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "getUserMenuAuthList")
    public List<Menu> getUserMenuAuthList(CommonParams commonParamsRequest) throws Exception {
        return commonService.getUserMenuAuthList(commonParamsRequest);
    }

    @Operation(summary = "메뉴 > 즐겨찾기 목록", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "favoritesList")
    public List<Menu> favoritesList(CommonParams commonParamsRequest) throws Exception {
        return commonService.favoritesList(commonParamsRequest);
    }

    @Operation(summary = "사용자 관리 > 사용자 목록", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "getUserInfoList")
    public List<CommonParams> getUserInfoList(CommonParams commonParamsRequest) throws Exception {
    
        List<CommonParams> list = commonService.getUserInfoList(commonParamsRequest);

        for (CommonParams item : list) {
            item.setTotalRowsNum(commonParamsRequest.getTotalRowsNum());
        }
        
        return list ;
    }

    @Operation(summary = "메뉴 > 즐겨찾기 추가", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "favoritesInsert")
    public void favoritesInsert(CommonParams commonParamsRequest) throws Exception {
        commonService.favoritesInsert(commonParamsRequest);
    }

    @Operation(summary = "필터권한 인서트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "filterInsert")
    public void filterInsert(CommonParams commonParamsRequest) throws Exception {
        commonService.filterInsert(commonParamsRequest);
    }

    @Operation(summary = "메뉴권한 인서트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "menuAuthInsert")
    public ResponseEntity<Map<String, Object>> menuAuthInsert(CommonParams commonParamsRequest) throws Exception {
        commonService.menuAuthInsert(commonParamsRequest);

        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "조직 관리 > 조직 트리 조직명 변경", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setChangeDeptName")
    public void setChangeDeptName(CommonParams commonParamsRequest) throws Exception {
        commonService.setChangeDeptName(commonParamsRequest);
    }

    @Operation(summary = "조직 관리 > 조직 추가", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setCreateDept")
    public String setCreateDept(CommonParams commonParamsRequest) throws Exception {
        commonService.setCreateDept(commonParamsRequest);
        return commonParamsRequest.getNewId();
    }

    @Operation(summary = "조직 관리 > 사용자 조직 배정", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setAssignUserDept")
    public void setAssignUserDept(CommonParams commonParamsRequest) throws Exception {
        commonService.setAssignUserDept(commonParamsRequest);
    }

    @Operation(summary = "사용자 관리 > 사용자 부서 변경", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setChangeUserDept")
    public void setChangeUserDept(CommonParams commonParamsRequest) throws Exception {
        commonService.setChangeUserDept(commonParamsRequest);
    }    
 
    @Operation(summary = "사용자 관리 > 사용자 학교급 추가/삭제", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setUserChargeClass")
    public void setUserChargeClass(CommonParams commonParamsRequest) throws Exception {
        commonService.setUserChargeClass(commonParamsRequest);
    }    

    @Operation(summary = "사용자 관리 > 사용자 잠금 해제", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setUnlockUser")
    public void setUnlockUser(CommonParams commonParamsRequest) throws Exception {
        commonService.setUnlockUser(commonParamsRequest);
    }
    
    @Operation(summary = "기능 관리 > 사용자 목록 > 부여 버튼기능", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setAuth")
    public ResponseEntity<Map<String, Object>> setAuth(CommonParams commonParamsRequest) throws Exception {
        commonService.setAuth(commonParamsRequest);

           Map<String, Object> data = new HashMap<>();
           data.put("result", commonParamsRequest.getReturnCode());
        
        return ResponseEntity.ok(data);
    }
    
    @Operation(summary = "기능 관리 > 사용자 목록 > 권한 삭제버튼", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setAuthDelete")
    public ResponseEntity<Map<String, Object>> setAuthDelete(CommonParams commonParamsRequest) throws Exception {
        commonService.setAuthDelete(commonParamsRequest);

           Map<String, Object> data = new HashMap<>();
           data.put("success", true);
        
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "게시판 사용자 목록 설정 > 기존 메뉴 사용자 삭제", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setDeleteAuthFromMenuId")
    public void setDeleteAuthFromMenuId(CommonParams commonParamsRequest) throws Exception {
        commonService.setDeleteAuthFromMenuId(commonParamsRequest);
    }

    @Operation(summary = "메뉴 > 즐겨찾기 삭제", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "favoritesDelete")
    public void favoritesDelete(CommonParams commonParamsRequest) throws Exception {
        commonService.favoritesDelete(commonParamsRequest);
    }

    @Operation(summary = "조직 관리 > 조직 삭제", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setDeleteDept")
    public String setDeleteDept(CommonParams commonParamsRequest) throws Exception {
        commonService.setDeleteDept(commonParamsRequest);
        return commonParamsRequest.getReturnValue();
    }

    @Operation(summary = "조직 관리 > 조직 배정 사용자 삭제", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setDeleteUserDept")
    public String setDeleteUserDept(CommonParams commonParamsRequest) throws Exception {
        commonService.setDeleteUserDept(commonParamsRequest);
        return commonParamsRequest.getReturnValue();
    }

    @Operation(summary = "메뉴 권한 > 권한 사용자 목록", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "getAuthList")
    public List<CommonParams> getAuthList(CommonParams commonParamsRequest) throws Exception {

        List<CommonParams> authList = commonService.getAuthList(commonParamsRequest);

        for (CommonParams item : authList) {
            item.setTotalRowsNum(commonParamsRequest.getTotalRowsNum());
            //item.setCls(commonParamsRequest.getCls());
        }

        return authList;
    }

    @Operation(summary = "말머리설정 > 메뉴트리 및 말머리 목록 리스트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "getHeadTitleWithMenu")
    public List<HeadTitleResultSets> getHeadTitleWithMenu(CommonParams commonParamsRequest) throws Exception {
        return commonService.getHeadTitleWithMenu(commonParamsRequest);
    }

    @Operation(summary = "게시판 관리 > 게시판 그룹 생성", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setCreateMenuGroup")
    public int setCreateMenuGroup(CommonParams commonParamsRequest) throws Exception {
        commonService.setCreateMenuGroup(commonParamsRequest);
        return commonParamsRequest.getNewMenuId();
    }

    @Operation(summary = "게시판 관리 > 게시판 삭제", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setDeleteBoard")
    public String setDeleteBoard(CommonParams commonParamsRequest) throws Exception {
        commonService.setDeleteBoard(commonParamsRequest);
        return commonParamsRequest.getReturnValue();
    }

    @Operation(summary = "게시판 관리 > 그룹 삭제", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setDeleteMenuGroup")
    public String setDeleteMenuGroup(CommonParams commonParamsRequest) throws Exception {
        commonService.setDeleteMenuGroup(commonParamsRequest);
        return commonParamsRequest.getReturnValue();
    }

    @Operation(summary = "게시판 관리 > 게시판 복사", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setCloneBoardWithAuth")
    public int setCloneBoardWithAuth(CommonParams commonParamsRequest) throws Exception {
        commonService.setCloneBoardWithAuth(commonParamsRequest);
        return commonParamsRequest.getNewMenuId();
    }
    
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }

    

}
