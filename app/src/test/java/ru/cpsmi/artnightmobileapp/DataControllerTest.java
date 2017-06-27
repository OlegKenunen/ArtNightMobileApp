package ru.cpsmi.artnightmobileapp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by Oleg on 27.06.2017.
 * oleg.kenunen@gmail.com
 */
public class DataControllerTest {
    @Test
    public void getInstance() throws Exception {
        //test DataController is Singleton
        DataController dataController1 = DataController.getInstance();
        DataController dataController2 = DataController.getInstance();
        assertEquals(dataController1, dataController2);
    }

}