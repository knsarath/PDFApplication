package com.hp.augmentedprint.pdfmetadata;

import android.support.annotation.NonNull;

import com.hp.augmentedprint.mapschema.MapInformation;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sarath on 5/3/18.
 */
@RunWith(JUnit4.class)
public class PDFActivityTest {
    @Test
    public void testSchema() {

        MapInformation mapInformation = new MapInformation();
        mapInformation.url = "https://nofile.io/f/VCPDtZ1DowV/map.pdf";
        HashMap<String, MapPage> pageHashMap = new LinkedHashMap<>();
        pageHashMap.put("DWG", dwg());
        pageHashMap.put("BIM Data", bimData());
        pageHashMap.put("360 ", threeSixty());
        pageHashMap.put("VR", vr());
        pageHashMap.put("AR", ar());
        mapInformation.mapInfo = (List<HashMap<String, MapPage>>) pageHashMap;
        Gson gson = new Gson();
        String json = gson.toJson(mapInformation);
        System.out.println(json);

    }

    private MapPage ar() {
        ArrayList<MarkerInfo> markerInfos = new ArrayList<>();
        markerInfos.add(new MarkerInfo(new PointCoordinate(50f, 60f), new MarkerSize(32, 32), MarkerInfo.MarkerType.INFO, new MarkerData("")));
        markerInfos.add(new MarkerInfo(new PointCoordinate(17f, 10f), new MarkerSize(32, 32), MarkerInfo.MarkerType.HYPERLINK, new MarkerData("")));
        markerInfos.add(new MarkerInfo(new PointCoordinate(50f, 50f), new MarkerSize(32, 32), MarkerInfo.MarkerType.INFO, new MarkerData("")));
        markerInfos.add(new MarkerInfo(new PointCoordinate(17f, 10f), new MarkerSize(32, 32), MarkerInfo.MarkerType.HYPERLINK, new MarkerData("")));
        MapPage mapPage = new MapPage(markerInfos);
        return mapPage;
    }

    private MapPage vr() {
        ArrayList<MarkerInfo> markerInfos = new ArrayList<>();
        markerInfos.add(new MarkerInfo(new PointCoordinate(50f, 60f), new MarkerSize(32, 32), MarkerInfo.MarkerType.INFO, new MarkerData("")));
        markerInfos.add(new MarkerInfo(new PointCoordinate(17f, 10f), new MarkerSize(32, 32), MarkerInfo.MarkerType.HYPERLINK, new MarkerData("")));
        MapPage mapPage = new MapPage(markerInfos);
        return mapPage;
    }

    private MapPage bimData() {
        ArrayList<MarkerInfo> markerInfos = new ArrayList<>();
        markerInfos.add(new MarkerInfo(new PointCoordinate(50f, 50f), new MarkerSize(32, 32), MarkerInfo.MarkerType.INFO, new MarkerData("")));
        markerInfos.add(new MarkerInfo(new PointCoordinate(17f, 10f), new MarkerSize(32, 32), MarkerInfo.MarkerType.INFO, new MarkerData("")));
        MapPage mapPage = new MapPage(markerInfos);
        return mapPage;
    }

    private MapPage threeSixty() {
        ArrayList<MarkerInfo> markerInfos = new ArrayList<>();
        MarkerData markerData = new MarkerData("");
        markerData.redirectUrl = "http://pano-dev.autodesk.com/pano.html?url=jpgs/41391972-49d3-4b52-87fb-cf152de5c032";
        markerInfos.add(new MarkerInfo(new PointCoordinate(50f, 60f), new MarkerSize(32, 32), MarkerInfo.MarkerType.INFO, markerData));
        markerInfos.add(new MarkerInfo(new PointCoordinate(17f, 10f), new MarkerSize(32, 32), MarkerInfo.MarkerType.HYPERLINK, markerData));
        MapPage mapPage = new MapPage(markerInfos);
        return mapPage;
    }

    @NonNull
    private MapPage dwg() {
        return new MapPage(Collections.emptyList());
    }

}