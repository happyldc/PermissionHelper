package demo.happyldc.com.permissiondemo;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.happyldc.helper.permission.ApplyResultCallback;
import com.happyldc.helper.permission.PermissionHelper;
import com.happyldc.helper.permission.PermissionResultInfo;
import com.happyldc.helper.permission.WrapApplyResultCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void req(View view) {
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
    }

}
