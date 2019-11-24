package com.happyldc.helper.permission;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限申请辅助Fragment
 * 权限的申请经过HelperFragment发起申请绕过Activity
 *
 * @author ldc
 * @Created at 2019/3/28 15:58.
 */

public final class HelperFragment extends Fragment {
    private long startTime = 0;
    private Map<Integer, ApplyResultCallback> mCallbackMap = new HashMap<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void requestPermissions(String[] permissions, int requestCode, ApplyResultCallback callback) {
        if (!mCallbackMap.containsKey(callback)) {
            mCallbackMap.put(requestCode, callback);
        }
        startTime = System.currentTimeMillis();
        //系统版本低于6.0无需申请
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            callback(requestCode, permissions, true);
            return;
        } else {
            //过滤已授权
            List<String> _needReqPermission = new ArrayList<String>();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(getContext(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    _needReqPermission.add(permissions[i]);
                }
            }
            //已授权无需申请
            if (_needReqPermission.size() == 0) {
                callback(requestCode, permissions, true);
                return;
            }

            String[] _permissions = new String[_needReqPermission.size()];
            _needReqPermission.toArray(_permissions);

            //权限申请
            requestPermissions(_permissions, requestCode);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!mCallbackMap.containsKey(requestCode)) {
            return;
        }
        if (grantResults == null || grantResults.length == 0) {
            return;
        }
        callback(requestCode, permissions, grantResults);
    }

    /**
     * 无需申请权限时调用
     *
     * @param requestCode 请求码
     * @param permissions 权限列表
     * @param isGranted   true全部授权  false 全部未授权
     */
    private void callback(int requestCode, String[] permissions, boolean isGranted) {
        int[] _grantResults = new int[permissions.length];
        for (int i = 0; i < _grantResults.length; i++) {
            _grantResults[i] = isGranted ? PackageManager.PERMISSION_GRANTED : PackageManager.PERMISSION_DENIED;
        }
        callback(requestCode, permissions, _grantResults);
    }

    /**
     * 权限申请结果回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    private void callback(int requestCode, String[] permissions, int[] grantResults) {
        ApplyResultCallback callbck = mCallbackMap.remove(requestCode);
        if (callbck != null) {
            callbck.onResult(requestCode, new PermissionResultInfo(permissions, grantResults, (int) (System.currentTimeMillis() - startTime)));
        }
    }



}
