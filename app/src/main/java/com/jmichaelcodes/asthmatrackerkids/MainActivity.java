package com.jmichaelcodes.asthmatrackerkids;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.davidstemmer.screenplay.MutableStage;
import com.davidstemmer.screenplay.flow.Screenplay;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;

public class MainActivity extends AppCompatActivity {
    private MutableStage stage;
    private Flow flow;
    private Screenplay screenplay;

    @Bind(R.id.container) RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "kajlksdjff", Toast.LENGTH_LONG).show();
//        ButterKnife.bind(this);
//
//        stage = Application.getStage();
//        flow = Application.getMainFlow();
//        screenplay = Application.getScreenplay();
//
//        stage.bind(this, container, flow);
//        screenplay.enter();
    }

//    @Override
//    public void onBackPressed() {
//        flow.goBack();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        screenplay.exit();
//        stage.unbind();
//    }
}
