package uk.co.sekaistudios.sekaiengine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by jamie on 16/01/2018.
 */

public class MyGLRenderer implements GLSurfaceView.Renderer
{

    private  static final String TAG = "MyGLRenderer";
    private Triangle mTriangle;

    private final float[] mMvpMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRoationMatrix = new float[16];

    private float mAngle;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config)
    {
        //set the background frame colour
        GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);

        mTriangle = new Triangle();

    }

    @Override
    public void  onDrawFrame(GL10 unused)
    {
        float[]scratch = new float[16];

        //draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        //Set camera position(View matrix)
        Matrix.setLookAtM(mViewMatrix,0,0,0,-3,0f,0f,0f,0f,1.0f,0.0f);

        //Calculate the projection and view transformation
        Matrix.multiplyMM(mMvpMatrix,0,mProjectionMatrix,0,mViewMatrix,0);

        Matrix.setRotateM(mRoationMatrix,0,mAngle,0,0,1.0f);

        Matrix.multiplyMM(scratch,0,mMvpMatrix,0,mRoationMatrix,0);

        //draw triangle
        mTriangle.draw(scratch);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        //Adjust the viewport based on geometry changes such as screen rotation
        GLES20.glViewport(0,0,width,height);

        float ratio = (float) width/height;

        Matrix.frustumM(mProjectionMatrix,0,-ratio,ratio,-1,1,3,7);

    }

    public  static int loadShader(int type, String shaderCode)
    {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public static void checkGLError(String glOperation)
    {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR)
        {
            Log.e(TAG, glOperation + "glError: " + error);
            throw new RuntimeException(glOperation + ":glError" + error);
        }
    }

    public float getAngle()
    {
        return mAngle;
    }

    public void setAngle(float angle)
    {
        mAngle = angle;
    }

}
