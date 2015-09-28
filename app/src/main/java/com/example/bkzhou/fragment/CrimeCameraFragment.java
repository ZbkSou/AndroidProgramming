package com.example.bkzhou.fragment;

        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.util.List;
        import java.util.UUID;

        import android.annotation.TargetApi;
        import android.content.Context;
        import android.hardware.Camera;
        import android.hardware.Camera.Size;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.SurfaceHolder;
        import android.view.SurfaceView;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;

public class CrimeCameraFragment extends Fragment {
    private static final String TAG = "CrimeCameraFragment";

    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private View mProgressContainer;
    private View mProressContainer;
    private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback(){
        @Override
        public void onShutter() {
            mProressContainer.setVisibility(View.VISIBLE);
        }
    };
    private Camera.PictureCallback mJpegCallback = new Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            String filename = UUID.randomUUID().toString()+".jpg";
            FileOutputStream os = null;
            boolean success = true;
            try{
                os = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                os.write(bytes);
            } catch (Exception e) {
                success = false;
                e.printStackTrace();
            }finally {
                try{
                    if(os != null){
                        os.close();
                    }
                }catch(Exception e){
                    success = false;
                }
            }
            if (success){
                Log.i("CrimeFragment","JPEG saved at "+ filename);
            }
            getActivity().finish();
        }
    };

    @Override
    @SuppressWarnings("deprecation")
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_camera, parent, false);

        Button takePictureButton = (Button)v.findViewById(R.id.crime_camera_takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                getActivity().finish();
                if(mCamera != null){
                    mCamera.takePicture(mShutterCallback,null,mJpegCallback);
                }
            }

        });
        mProressContainer = v.findViewById(R.id.crime_camera_progressContainer);
        mProressContainer.setVisibility(View.INVISIBLE);
        mSurfaceView = (SurfaceView)v.findViewById(R.id.crime_camera_surfaceView);
        final SurfaceHolder holder = mSurfaceView.getHolder();
        // deprecated, but required for pre-3.0 devices
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if(mCamera != null){
                        mCamera.setPreviewDisplay(holder);
                    }
                } catch (IOException e) {
                        e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                if (mCamera == null){
                    return;
                }else {
                    Camera.Parameters parameters = mCamera.getParameters();
//                    Size s = null;
                    Size s = getBestSupportedSize(parameters.getSupportedPreviewSizes() ,i1 ,i2);
                    parameters.setPreviewSize(s.width, s.height);
                    s = getBestSupportedSize(parameters.getSupportedPictureSizes() ,i1 ,i2);
                    parameters.setPictureSize(s.width, s.height);
                    mCamera.setParameters(parameters);
                    try{
                    mCamera.startPreview();
                    }catch (Exception e){
                        Log.e(TAG,"Could not start preview",e);
                        mCamera.release();
                        mCamera = null;
                    }

                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (mCamera != null){
                    mCamera.stopPreview();
                }
            }
        });

        return v;
    }

    @TargetApi(9)
    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            mCamera = Camera.open(0);
        } else {
            mCamera = Camera.open();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
    private Size getBestSupportedSize(List<Size> sizes , int width ,int height){
        Size bestSize = sizes.get(0);
        int largestArea  = bestSize.width*bestSize.height;
        for (Size s: sizes){
            int area = s.width * s.height;
            if (area > largestArea) {
                bestSize = s;
                largestArea = area;
            }
        }
        return  bestSize;
    }


}
