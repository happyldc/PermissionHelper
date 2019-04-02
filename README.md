# PermissionHelper
Android 6.0权限申请
## 使用
### 添加依赖
 1.在Project下的gradle中添加添加JitPack仓库
 

    allprojects {
        repositories {
          //...其他仓库
            maven { url 'https://www.jitpack.io' }
        }
    }
 2.在项目中添加PermissionHelper的依赖,其中XYZ表示版本号 如 1.0.0
 

    dependencies {
            implementation 'com.github.happyldc:PermissionHelper:X.Y.Z'
    }
### 调用
1. 需要在 AndroidManifest.xml中添加需要申请的权限


      <uses-permission android:name="android.permission.CAMERA"></uses-permission>
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
    
2. 在代码中调用 PermissionHelper.request()方法进行权限申请。
 * 不需要手动传入requestCode, PermissionHelper会自动生成
 * 可同时多个权限进行申请。
 ```java
  //要申请的权限
        String[] permissions = new String[]{
                Manifest.permission.CAMERA
                , Manifest.permission.WRITE_EXTERNAL_STORAGE};

        PermissionHelper
                .with(this)
                .permission(permissions)
                .request(new WrapApplyResultCallback() {

                    @Override
                    public void onFullGranted(int requestCode, String[] granteds) {
                        Toast.makeText(MainActivity.this, "全部授权", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void onDenieds(int requestCode, String[] granteds, String[] denieds) {
                        if (granteds.length == 0) {
                            Toast.makeText(MainActivity.this, "全部拒绝", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "部分授权", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
```
 
