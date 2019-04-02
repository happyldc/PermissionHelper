package com.happyldc.helper.permission;

import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限申请结果
 *
 * @author ldc
 * @Created at 2018/9/19 9:15.
 */

public class PermissionResultInfo {
    /**
     * 欲申请的权限列表
     */
    private String[] mApplyPermissions;
    /**
     * 申请结果
     */
    private int[] mApplyResults;
    /**
     * 从触发申请到回调消耗的时长  单位ms
     */
    private int mConsumedTime;

    public PermissionResultInfo(String[] applyPermission, int[] applyResult, int consumedTime) {
        this.mApplyPermissions = applyPermission;
        this.mApplyResults = applyResult;
        this.mConsumedTime = consumedTime;

    }

    /**
     * 获取申请权限的列表
     *
     * @return
     */
    public String[] getApplyPermissions() {
        return mApplyPermissions;
    }

    /**
     * 获取授权结果
     *
     * @return
     */
    public boolean[] getApplyResults() {
        boolean[] _reuslt = new boolean[mApplyResults.length];
        for (int i = 0; i < mApplyResults.length; i++) {
            _reuslt[i] = mApplyResults[i] == PackageManager.PERMISSION_GRANTED;
        }
        return _reuslt;
    }

    /**
     * 从开始申请到回调消耗的时长
     * @return 单位 毫秒
     */
    public int getConsumedTime() {
        return mConsumedTime;
    }

    /**
     * 判断是否全部授权
     *
     * @return
     */
    public boolean hasFullGrant() {
        if (mApplyPermissions.length == 0) {
            return true;
        }
        boolean _isGranted = true;
        for (int i = 0; i < mApplyPermissions.length; i++) {
            _isGranted &= (mApplyResults[i] == PackageManager.PERMISSION_GRANTED);
            if (!_isGranted) {
                break;
            }
        }
        return _isGranted;
    }
    /**
     * 判断是否全部拒绝
     *
     * @return
     */
    public boolean hasFullDenied() {
        if (mApplyPermissions.length == 0) {
            return true;
        }
        boolean _isGranted = true;
        for (int i = 0; i < mApplyPermissions.length; i++) {
            _isGranted &= (mApplyResults[i] == PackageManager.PERMISSION_DENIED);
            if (!_isGranted) {
                break;
            }
        }
        return _isGranted;
    }
    /**
     * 获取被拒绝的权限
     *
     * @return
     */
    public String[] getDenieds() {
        List<String> _permission = new ArrayList<>();
        for (int i = 0; i < mApplyPermissions.length; i++) {
            if (mApplyResults[i] == PackageManager.PERMISSION_DENIED) {
                _permission.add(mApplyPermissions[i]);
            }
        }

        String[] _result = new String[_permission.size()];
        _permission.toArray(_result);
        return _result;
    }
    /**
     * 获取已授予的权限
     *
     * @return
     */
    public String[] getGranteds() {
        List<String> _permission = new ArrayList<>();
        for (int i = 0; i < mApplyPermissions.length; i++) {
            if (mApplyResults[i] == PackageManager.PERMISSION_GRANTED) {
                _permission.add(mApplyPermissions[i]);
            }
        }

        String[] _result = new String[_permission.size()];
        _permission.toArray(_result);
        return _result;
    }
}
