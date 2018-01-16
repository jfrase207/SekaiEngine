package uk.co.sekaistudios.sekaiengine;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jamie on 16/01/2018.
 */

public class OpenGLES20Activity extends Activity
{

    private GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);

    }

    @Override
    protected void onPause()
    {
        super.onPause();

        mGLView.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        mGLView.onResume();
    }

    public String ExtractFromFile(String inFile) {
        String tContents = "";

        try {
            InputStream stream = getAssets().open(inFile);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;

    }

}
