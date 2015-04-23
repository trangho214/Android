package softs.hnt.com.dimenconverter;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileWriter;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cloneDimenFile(1.5f);
    }
    private void cloneDimenFile(float rate) {
        String text = "";
        for (int i = 1; i <= 500; i++) {
            text = text + "\n<dimen name=\"value_" + i + "\">" + (i / rate) + "dp</dimen>";
        }
        WriteFileText(text, "dimen_file");
    }

    public static void WriteFileText(String text, String fileName) {
        FileWriter fWriter;
        try {
            File newFile = new File(Environment.getExternalStorageDirectory() + "/nhich");
            if (!newFile.exists()) {
                newFile.mkdir();
            }
            fWriter = new FileWriter(Environment.getExternalStorageDirectory() + "/nhich/" + fileName + ".txt");
            fWriter.write(text);
            fWriter.flush();
            fWriter.close();
            Log.d("done", "done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
}
