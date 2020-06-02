package com.example.sevennine_Delivery.Fragment;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.sevennine_Delivery.Orders.CancelledTab;
import com.example.sevennine_Delivery.Orders.DeliverdTab;
import com.example.sevennine_Delivery.Orders.NewOrderTab;
import com.example.sevennine_Delivery.Orders.ProcessingTab;


/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {

            case 0:
                System.out.println("llllllllllllllllllll1");
                NewOrderTab tab1 = new NewOrderTab();
                return tab1;
            case 1:
                ProcessingTab scheduledTabFragment=new ProcessingTab();
                return scheduledTabFragment;
            case 2:
                DeliverdTab tab4=new DeliverdTab();
                return tab4;
            case 3:
                CancelledTab tab5=new CancelledTab();
                return  tab5;

            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}