package com.hp.augmentedprint.pdfmetadata;

import com.hp.augmentedprint.schema.MapPage;
import com.hp.augmentedprint.schema.MarkerInfo;
import com.hp.augmentedprint.schema.MarkerData;
import com.hp.augmentedprint.schema.MarkerSize;
import com.hp.augmentedprint.schema.PointCoordinate;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarath on 5/3/18.
 */
@RunWith(JUnit4.class)
public class MainActivityTest {
    @Test
    public void testSchema() {

        MarkerSize markerSize = new MarkerSize(100, 100);


        List<MapPage> mapPages = new ArrayList<>();

        MarkerInfo markerInfo1 = new MarkerInfo(new PointCoordinate(10f, 20f), markerSize, new MarkerData("This is a marker data"));
        MarkerInfo markerInfo2 = new MarkerInfo(new PointCoordinate(30f, 20f), markerSize, new MarkerData("This is a marker data"));
        MarkerInfo markerInfo3 = new MarkerInfo(new PointCoordinate(25f, 35f), markerSize, new MarkerData("This is a marker data"));
        MarkerInfo markerInfo4 = new MarkerInfo(new PointCoordinate(50f, 50f), markerSize, new MarkerData("This is a marker data"));

        List<MarkerInfo> markerInfos = new ArrayList<>();
        markerInfos.add(markerInfo1);
        markerInfos.add(markerInfo2);
        markerInfos.add(markerInfo3);
        markerInfos.add(markerInfo4);

        mapPages.add(new MapPage(markerInfos));
        mapPages.add(new MapPage(markerInfos));

        Gson gson = new Gson();
        String json = gson.toJson(mapPages);

        System.out.println(json);

    }

}