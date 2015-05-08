package Sample;

import java.util.ArrayList;
import java.util.List;

import softs.hnt.com.batda.R;

/**
 * Created by TrangHo on 24-04-2015.
 */
public class SampleObject1 {
    public  int imageId;
    public String title;
    public String price;
    public String date;


    public String customerName;
    public String city;
    public String phoneNumber;
    public String email;
    public String address;
    public double discount;
    public int shippingCost;
    public String description;

    public List<ChildSample> childSamples;


    public  SampleObject1(String title, String price, String date)
    {
        this.title = title;
        this.price = price;
        this.date = date;
        imageId = R.drawable.ic_action_github;
        childSamples = new ArrayList<ChildSample>();
    }

    public SampleObject1(String title, String price, String date, String customerName, String city,String phoneNumber,String email,
                         String address, double discount, int shippingCost, String description) {
        this.imageId = R.drawable.ic_action_github;
        this.title = title;
        this.price = price;
        this.date = date;
        this.customerName = customerName;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.discount = discount;
        this.shippingCost = shippingCost;
        this.description = description;
        this.childSamples = new ArrayList<ChildSample>();

    }
    public SampleObject1(String title, String price, String date, String customerName,String city, String phoneNumber,String email,
                         String address, double discount, int shippingCost, String description, List<ChildSample> childSamples) {
        this.imageId = R.drawable.ic_action_github;
        this.title = title;
        this.price = price;
        this.date = date;
        this.customerName = customerName;
        this.city  =city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.discount = discount;
        this.shippingCost = shippingCost;
        this.description = description;
        this.childSamples = childSamples;

    }

}
