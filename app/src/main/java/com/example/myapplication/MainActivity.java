package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Camera;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.BufferedInputStream;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private Button button;
Mat mat1,mat2,mat3;
JavaCameraView javaCameraView;
   Scalar scalarLow,scalarHigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        OpenCVLoader.initDebug();
        javaCameraView= (JavaCameraView)findViewById(R.id.cameraView);
        javaCameraView.setCameraIndex(0);

        scalarLow = new Scalar(225,225,225);
        scalarHigh = new Scalar(255,255,255);

        javaCameraView.setCvCameraViewListener(this);
        javaCameraView.enableView();
        button = findViewById(R.id.button);

       Imgproc.cvtColor('C:\\Users\\user\\AndroidStudioProjects\\MyApplication4\\app\\src\\main\\res\\drawable-v24\\bird.jpg',mat3,Imgproc.COLOR_BGR2HSV); // The bird will be converted to HSV
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You fired a shot", Toast.LENGTH_SHORT).show();
             Scalar a = Core.sumElems(mat2);
             Scalar b = Core.sumElems(mat3);
                String a1=a.toString();
                String b1=b.toString();
                System.out.println(b+" "+b1);
                double comp=a1.compareTo(b1);
                if(comp==0){
                    Toast.makeText(MainActivity.this, "Succesful dhot", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Unsuccesful shot", Toast.LENGTH_SHORT).show();
                }




            }

        });
    }
@Override
protected void onPause() {

    super.onPause();
    javaCameraView.disableView();
    Toast.makeText(this, "camera  not there", Toast.LENGTH_SHORT).show();
}
@Override
protected void onResume() {
    super.onResume();
    javaCameraView.enableView();
    Toast.makeText(this, "Camera there", Toast.LENGTH_SHORT).show();
}
@Override
protected void  onDestroy() {
javaCameraView.disableView();
    super.onDestroy();
    Toast.makeText(this, "Well this sucks", Toast.LENGTH_SHORT).show();

}

    @Override
    public void onCameraViewStarted(int width, int height) {
       mat1 = new Mat(width,height, CvType.CV_16UC4);
        mat2 = new Mat(width,height, CvType.CV_16UC4);
        Toast.makeText(this, "camera is started", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCameraViewStopped() {
        Toast.makeText(this, "stopped idk", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Imgproc.cvtColor(inputFrame.rgba(),mat1,Imgproc.COLOR_BGR2HSV);        //We convert the image to HSV to make for slight errors
        Core.inRange(mat1,scalarLow,scalarHigh,mat2);
        Toast.makeText(this, "on camera frame", Toast.LENGTH_SHORT).show();
        return mat2;

    }
}