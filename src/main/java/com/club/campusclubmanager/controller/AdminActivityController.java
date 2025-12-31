package com.club.campusclubmanager.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.club.campusclubmanager.common.Result;
import com.club.campusclubmanager.dto.CheckinRequest;
import com.club.campusclubmanager.dto.CreateActivityRequest;
import com.club.campusclubmanager.dto.ReviewActivityRequest;
import com.club.campusclubmanager.dto.UpdateActivityRequest;
import com.club.campusclubmanager.service.ActivityService;
import com.club.campusclubmanager.vo.ActivitySignupVO;
import com.club.campusclubmanager.vo.ActivityVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 活动控制器（系统管理员）
 */
@Tag(name = "活动管理（系统管理员）", description = "系统管理员审核、管理所有活动")
@RestController
@RequestMapping("/admin/activity")
@RequiredArgsConstructor
@SaCheckRole("system_admin")  // 需要系统管理员角色
public class AdminActivityController {

    private final ActivityService activityService;

    /**
     * 查看所有活动
     */
    @Operation(summary = "查看所有活动", description = "系统管理员查看所有活动，支持关键词和状态筛选")
    @GetMapping("/list")
    public Result<Page<ActivityVO>> getAllActivities(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "搜索关键词") @RequestParam(required = false) String keyword,
            @Parameter(description = "活动状态") @RequestParam(required = false) String status
    ) {
        Page<ActivityVO> page = activityService.getAllActivities(pageNum, pageSize, keyword, status);
        return Result.success(page);
    }

    /**
     * 审核活动
     */
    @Operation(summary = "审核活动", description = "系统管理员审核活动（通过/拒绝）")
    @PutMapping("/{id}/review")
    public Result<Void> reviewActivity(
            @Parameter(description = "活动ID") @PathVariable Long id,
            @Valid @RequestBody ReviewActivityRequest request
    ) {
        activityService.reviewActivity(id, request);
        return Result.success("审核完成", null);
    }

    /**
     * 删除活动
     */
    @Operation(summary = "删除活动", description = "系统管理员删除活动")
    @DeleteMapping("/{id}")
    public Result<Void> deleteActivity(
            @Parameter(description = "活动ID") @PathVariable Long id
    ) {
        activityService.deleteActivity(id);
        return Result.success("删除成功", null);
    }

    /**
     * 创建活动
     */
    @Operation(summary = "创建活动", description = "系统管理员创建活动")
    @PostMapping("/create")
    public Result<Void> createActivity(@Valid @RequestBody CreateActivityRequest request) {
        activityService.createActivityByAdmin(request);
        return Result.success("活动创建成功", null);
    }

    /**
     * 更新活动
     */
    @Operation(summary = "更新活动", description = "系统管理员更新活动信息")
    @PutMapping("/{id}")
    public Result<Void> updateActivity(
            @Parameter(description = "活动ID") @PathVariable Long id,
            @Valid @RequestBody UpdateActivityRequest request
    ) {
        activityService.updateActivityByAdmin(id, request);
        return Result.success("活动更新成功", null);
    }

    /**
     * 取消活动
     */
    @Operation(summary = "取消活动", description = "系统管理员取消活动")
    @PutMapping("/{id}/cancel")
    public Result<Void> cancelActivity(
            @Parameter(description = "活动ID") @PathVariable Long id
    ) {
        activityService.cancelActivityByAdmin(id);
        return Result.success("活动已取消", null);
    }

    /**
     * 查看活动报名列表
     */
    @Operation(summary = "查看活动报名列表", description = "系统管理员查看任意活动的所有报名人员")
    @GetMapping("/{id}/signups")
    public Result<Page<ActivitySignupVO>> getActivitySignups(
            @Parameter(description = "活动ID") @PathVariable Long id,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Page<ActivitySignupVO> page = activityService.getActivitySignupsByAdmin(id, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 活动签到/标记缺席
     */
    @Operation(summary = "活动签到/标记缺席", description = "系统管理员为报名用户签到或标记缺席")
    @PostMapping("/{id}/checkin")
    public Result<Void> checkinActivity(
            @Parameter(description = "活动ID") @PathVariable Long id,
            @Valid @RequestBody CheckinRequest request
    ) {
        activityService.checkinActivityByAdmin(id, request);
        return Result.success("操作成功", null);
    }
}
