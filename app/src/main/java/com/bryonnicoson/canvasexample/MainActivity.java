package com.bryonnicoson.canvasexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyCanvasView myCanvasView;
        // no xml file; just one custom view created programatically
        myCanvasView = new MyCanvasView(this);
        // request full available screen for layout
        myCanvasView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(myCanvasView);
    }
}
