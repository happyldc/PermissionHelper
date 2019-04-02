# PermissionHelper
Android 6.0Ȩ������
## ʹ��
### �������
 1.��Project�µ�gradle��������JitPack�ֿ�
 

    allprojects {
        repositories {
          //...�����ֿ�
            maven { url 'https://www.jitpack.io' }
        }
    }
 2.����Ŀ�����PermissionHelper������,����XYZ��ʾ�汾�� �� 1.0.0
 

    dependencies {
            implementation 'com.github.happyldc:PermissionHelper:X.Y.Z'
    }
### ����
1. ��Ҫ�� AndroidManifest.xml�������Ҫ�����Ȩ��


      <uses-permission android:name="android.permission.CAMERA"></uses-permission>
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
    
2. �ڴ����е��� PermissionHelper.request()��������Ȩ�����롣
 * ����Ҫ�ֶ�����requestCode, PermissionHelper���Զ�����
 * ��ͬʱ���Ȩ�޽������롣
 ```java
  //Ҫ�����Ȩ��
        String[] permissions = new String[]{
                Manifest.permission.CAMERA
                , Manifest.permission.WRITE_EXTERNAL_STORAGE};

        PermissionHelper
                .with(this)
                .permission(permissions)
                .request(new WrapApplyResultCallback() {

                    @Override
                    public void onFullGranted(int requestCode, String[] granteds) {
                        Toast.makeText(MainActivity.this, "ȫ����Ȩ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void onDenieds(int requestCode, String[] granteds, String[] denieds) {
                        if (granteds.length == 0) {
                            Toast.makeText(MainActivity.this, "ȫ���ܾ�", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "������Ȩ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
```
 
