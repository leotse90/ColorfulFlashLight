package mobi.tuyage.com.colorfulflashlight;

import android.hardware.Camera;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity{

    private ImageView mFlashImgView;

    private Camera mCamera;

    private boolean isFlashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ensure the camera turn off
        flashOff();
    }

    private void initWidget(){
        mFlashImgView = (ImageView) findViewById(R.id.flash_img);

        // ensure flashlight is off
        flashOff();

        mFlashImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlashOn){
                    mFlashImgView.setImageResource(R.mipmap.flash_light_off);
                    flashOff();
                } else {
                    mFlashImgView.setImageResource(R.mipmap.flash_light_on);
                    flashOn();
                }
            }
        });
    }

    private void flashOn(){
        if (!isFlashOn){
            mCamera = Camera.open();
            Camera.Parameters cameraParameters = mCamera.getParameters();
            cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(cameraParameters);
            mCamera.startPreview();
            isFlashOn = true;
        }
    }

    private void flashOff(){
        if (isFlashOn && mCamera != null){
            mCamera.stopPreview();
            mCamera.release();
            isFlashOn = false;
        }
    }
}
