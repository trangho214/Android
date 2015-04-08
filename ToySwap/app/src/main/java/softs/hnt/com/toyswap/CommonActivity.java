package softs.hnt.com.toyswap;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by TrangHo on 09-10-2014.
 */
public class CommonActivity extends ActionBarActivity{
    public final static String CURRENT_PHOTO_PATH = "CURRENT_PHOTO_PATH";
    Context mContext;
    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getActionBar();
//        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.button)));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void startMyActivityFromId(int id, final Class activity, final int resultCode)
    {
        (findViewById(id)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resultCode!=0)
                    startActivity(new Intent(getApplicationContext(), activity));
                else
                    startActivityForResult(new Intent(getApplicationContext(),activity),resultCode);
            }
        });
    }

}
