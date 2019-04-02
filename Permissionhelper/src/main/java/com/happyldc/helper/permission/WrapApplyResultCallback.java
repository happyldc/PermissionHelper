package com.happyldc.helper.permission;

/**权限申请回调
 * @author ldc
 * @Created at 2019/4/2 10:46.
 */

public abstract class WrapApplyResultCallback implements ApplyResultCallback {
    @Override
    public void onResult(int requestCode, PermissionResultInfo info) {
        if (info.hasFullGrant()) {
            onFullGranted(requestCode, info.getGranteds());
        }else{
            onDenieds(requestCode,info.getGranteds(),info.getDenieds());
        }
    }

    /**
     * 全部授权回调
     * @param requestCode 请求码
     * @param granteds 权限申请结果
     */
    public abstract void onFullGranted(int requestCode,String[] granteds);


    /**
     * 存在拒绝授权回调
     * @param requestCode 请求码
     * @param granteds 已授权的权限 长度==0 全部拒绝   >1部分授权
     * @param denieds 拒绝的权限
     */
    protected abstract void onDenieds(int requestCode, String[] granteds, String[] denieds);
}
