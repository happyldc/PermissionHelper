package com.happyldc.helper.permission;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 权限申请助手
 *
 * @author ldc
 * @Created at 2019/3/28 15:55.
 */

public final class PermissionHelper {
    private static final String TAG = "PermissionHelper";
    /**
     * Request Code 最大的请求码
     */
    private static final int REQUEST_CODE_MAX = 500;

    private HelperFragment mHelperFragment;

    private PermissionHelper(FragmentActivity activity) {
        mHelperFragment = getHelperFragment(activity);
    }

    private PermissionHelper(Fragment fragment) {
        this(fragment.getActivity());
    }

    private HelperFragment getHelperFragment(FragmentActivity activity) {
        HelperFragment _helpFragment = findHelpFragment(activity);
        if (_helpFragment == null) {
            _helpFragment = new HelperFragment();
            FragmentManager _fmager = activity.getSupportFragmentManager();
            _fmager.beginTransaction()
                    .add(_helpFragment, TAG)
                    .commitNow();
        }
        return _helpFragment;
    }

    private HelperFragment findHelpFragment(FragmentActivity activity) {
        return (HelperFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG);
    }

    public static PermissionHelper with(FragmentActivity activity) {
        return new PermissionHelper(activity);
    }
    public static PermissionHelper with(Fragment fragment) {
        return new PermissionHelper(fragment);
    }

    Persmission intentObj = new Persmission();
    //对外的API

    public PermissionHelper permission(String... permisssion) {
        intentObj.addPersmission(permisssion);
        return this;
    }

    public PermissionHelper permission(List<String> permisssion) {
        String[] _objects = new String[permisssion.size()];
        permisssion.toArray(_objects);
        intentObj.addPersmission(_objects);
        return this;
    }

    public PermissionHelper requestCode(int requestCode) {
        intentObj.requestCode = requestCode;
        return this;
    }

    public void request(ApplyResultCallback callback) {

        if (intentObj.requestCode == -1) {
            request(intentObj.getPermissionArray(), callback);
        } else {
            request(intentObj.getPermissionArray(), intentObj.requestCode, callback);
        }
    }

    /**
     * 申请权限
     * @param permission 权限
     * @param callback 回调
     */
    public void request(String permission, ApplyResultCallback callback) {
        this.request(new String[]{permission},newRequestCode(), callback);
    }
    public void request(String[] permission, ApplyResultCallback callback) {
        this.request(permission,newRequestCode(), callback);
    }

    public void request(String[] permission, int requestCode, ApplyResultCallback callback) {
        if (permission.length <1) {
            throw new IllegalArgumentException("The number of permissions applied is 0. Please set the permissions.");
        }
        mHelperFragment.requestPermissions(permission, requestCode, callback);
    }

    private static class Persmission {
        int requestCode = -1;
        List<String> permissions = new ArrayList<>();

        public void addPersmission(String permission) {
            if (!permissions.contains(permission)) {
                permissions.add(permission);
            }
        }

        public void addPersmission(String permission[]) {
            int len = permission.length;
            for (int i = 0; i < len; i++) {
                addPersmission(permission[i]);
            }
        }

       protected String[] getPermissionArray() {
            String[] _result = new String[permissions.size()];
            permissions.toArray(_result);
            return _result;
        }
    }

    /**
     * 新建一个请求码
     *
     * @return
     */
    private int newRequestCode() {
        //随机取值控制在100-REQUEST_CODE_MAX 之间
        int reqCode = 100 + new Random().nextInt(REQUEST_CODE_MAX - 100);
        //如果请求码被使用则重新生成
        return reqCode;
    }
}
