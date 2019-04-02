package com.happyldc.helper.permission;

/**申请结果回调
 * @author ldc
 * @Created at 2018/9/18 14:38.
 */

public interface ApplyResultCallback {
    /**
     * 授权回调
     *
     * @param requestCode 请求码
     * @param info   授权结果
     */
    void onResult(int requestCode, PermissionResultInfo info);


}
