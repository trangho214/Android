package softs.hnt.com.batda;

import java.util.ArrayList;
import java.util.List;

import Sample.ChildSample;
import Sample.SampleObject1;

/**
 * Created by TrangHo on 07-05-2015.
 */
public class SampleData {


    public static List<SampleObject1> getData()
    {
        List<SampleObject1> sampleObject1s = new ArrayList<SampleObject1>();
        for (int i = 0; i<20; i++)
        {
            sampleObject1s.add(new SampleObject1("title " + i, "0000000d", "date", "Hoang Thi Than Thuong","TP HCM", "123456", "abc@gmail.com",
                    "114 cu xa do thanh",0.2, 10000, "Description", childSample()));
        }
        return sampleObject1s;
    }

    private static List<ChildSample> childSample()
    {
        List<ChildSample> childSamples = new ArrayList<ChildSample>();
        for (int i = 0; i<4; i++)
        {
            childSamples.add(new ChildSample("#1234", "facial product", i+1, 30000));
        }
        return childSamples;
    }

}
