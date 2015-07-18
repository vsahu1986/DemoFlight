package demo.ixigo.network;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import de.greenrobot.event.EventBus;
import demo.ixigo.event.MainPageEvent;
import demo.ixigo.model.Flight;
import demo.ixigo.ui.utils.Utils;
import demo.ixigo.ui.utils.gson.GsonUtil;

/**
 * Created by vipinsahu on 17/7/15.
 */
public class NetworkRunnable implements Runnable {
    @Override
    public void run() {
        InputStream input = null;
        ByteArrayOutputStream output = null;
        try {
            int count;
            URL url = new URL("http://blog.ixigo.com/sampleflightdata.json");
            URLConnection conection = url.openConnection();
            conection.connect();

            // input stream to read file - with 8k buffer
            input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream to write file
            output = new ByteArrayOutputStream();

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output

            String repsonse = output.toString();
            if(repsonse!=null){
                Flight flight =GsonUtil.mapFromJSON(repsonse, Flight.class);
                Utils.sortByPriceAsc(flight.getFlightsData());
                EventBus.getDefault().post(new MainPageEvent(flight));
            }
            Log.v("Vipin", repsonse);

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        } finally {
            try {
                if (output != null) {
                    output.flush();
                    output.close();
                }
                if (input != null) {
                    input.close();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            // closing streams


        }
    }
}
