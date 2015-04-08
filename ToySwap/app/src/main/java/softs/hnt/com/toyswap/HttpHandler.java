package softs.hnt.com.toyswap;

import android.content.Context;

/**
 * Created by TrangHo on 04-11-2014.
 */


public abstract class HttpHandler {
    //TODO: Use asyncTask to do HTTP job.
    Object[] params;
    Context context;
    public HttpHandler(Context context,Object... params)
    {
        this.context = context;
        this.params = params;
    }

    public Context getContext() {
        return context;
    }

    public abstract void doOnResponse(String result);
    public void execute()
    {
        //pass params is Object[] to execute Asynctask. params will be passed over to as input(params) in doInBackGround.
        new AsyncHttpTask(this).execute(params);
    }

}
