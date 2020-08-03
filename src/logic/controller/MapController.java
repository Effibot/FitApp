package logic.controller;

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.util.MarkerImageFactory;

import javafx.fxml.FXMLLoader;

import java.util.ArrayList;
import java.util.List;

import logic.entity.dao.GymDAO;
import logic.maputil.Geocode;
import logic.entity.Gym;
import logic.viewcontroller.BookingOnMapViewController;

public class MapController {
	 private static MapController instance = null;
	    LatLong base;
	    Marker newMarker;
	    LatLong posit;
	    List<Marker> listMarker = new ArrayList<>();
	    List<String> listGymsName = new ArrayList<>();
        GymDAO dao = GymDAO.getInstance();

	    Marker marker;
	    private String center;
	    protected MapController() {

	    }

	    public void setPosit(LatLong posit) {
	        this.posit = posit;
	    }


	    public List<Marker> getListMarker() {
	        return listMarker;
	    }

	    public List<String> getListIdGym() {
	        return listIdGym;
	    }

	    public void setListIdGym(List<String> listIdGym) {
	        this.listIdGym = listIdGym;
	    }

	    private List<String> listIdGym;

	    public void setListMarker(List<Marker> listMarker) {
	        this.listMarker = listMarker;
	    }

	    public String getCenter(){
	        return this.center;
	    }

	    public void setCenter(String addr){
	        this.center = addr;
	    }

	    public LatLong getBase() {
	        return base;
	    }

//	    public void startGeocode(String data, String timeStart, String distance,String  baseAddress){
//	        this.setListMarker(this.distance(data,timeStart,distance,baseAddress));
//	    }


//	    public List<Marker> distance(String data, String timeStart, String dist, String baseAddress) {
//	        dao = GymDAO.getInstance();
//	        listIdGym = dao.getGymList(data, timeStart);
//	        LatLong endPoint;
//	        double distance = Integer.parseInt(dist);
//
//
//	        this.setCenter(baseAddress);
//
//
//	        Geocode pos = Geocode.getSingletonInstance();
//	        pos.getLocation(baseAddress);
//	        base = pos.getCoordinates();
//	        Marker baseMarker = this.nMarker(base, "You are Here!", baseAddress);
//	        listMarker.add(baseMarker);
//	        for (String s : listIdGym) {
//	            Gym gymEntity = dao.getGymEntity(Integer.parseInt(s));
//	            pos.getLocation(gymEntity.getstreet());
//	            endPoint = pos.getCoordinates();
//	            double relativeDistance = distanceRelative(base.getLatitude(), endPoint.getLatitude(), base.getLongitude(), endPoint.getLongitude());
//	            if (relativeDistance <= distance) {
//	                setPosit(endPoint);
//	                newMarker = nMarker(endPoint, gymEntity.getGymName(), gymEntity.getstreet());
//	                listMarker.add(newMarker);
//
//
//	            } else{
//	                listIdGym.remove(s);
//	            }
//
//	        }
//	        this.setListIdGym(listIdGym);
//	        this.listGymName();
//	       
//	        return listMarker;
//
//	    }

	    public static double distanceRelative(double lat1,
	                                          double lat2, double lon1,
	                                          double lon2) {

	        // The math module contains a function
	        // named toRadians which converts from
	        // degrees to radians.
	        lon1 = Math.toRadians(lon1);
	        lon2 = Math.toRadians(lon2);
	        lat1 = Math.toRadians(lat1);
	        lat2 = Math.toRadians(lat2);

	        // Haversine formula
	        double dlon = lon2 - lon1;
	        double dlat = lat2 - lat1;
	        double a = Math.pow(Math.sin(dlat / 2), 2)
	                + Math.cos(lat1) * Math.cos(lat2)
	                * Math.pow(Math.sin(dlon / 2), 2);

	        double c = 2 * Math.asin(Math.sqrt(a));

	        // Radius of earth in kilometers. Use 3956
	        // for miles
	        double r = 6371;

	        // calculate the result
	        return (c * r);
	    }


	    public Marker nMarker(LatLong position, String name, String address) {
	        if (position == null)
	            return null;

	        String pathUser=MarkerImageFactory.createMarkerImage("/icons/pathUser.png", "png");
	        pathUser = pathUser.replace("(", "");
	        pathUser = pathUser.replace(")", "");

	        String pathGym=MarkerImageFactory.createMarkerImage("/icons/pathGym.png", "png");
	        pathGym = pathGym.replace("(", "");
	        pathGym = pathGym.replace(")", "");
	        //Add a marker to the map
	        MarkerOptions markerOptions = new MarkerOptions();





	        if(address.equals(this.getCenter())) {
	            markerOptions.position(position)
	                    .visible(Boolean.TRUE)
	                    .title(name)
	                    .icon(pathUser);
	                    
	        }else{
	            markerOptions.position(position)
	                    .visible(Boolean.TRUE)
	                    .title(name)
	                    .icon(pathGym);


	        }

	        marker = new Marker(markerOptions);
	        return marker;
	    }
	    
	    public List<String> listGymName(){
	   
			if(listIdGym!= null) {
				for(String s: listIdGym) {
						listGymsName.add(dao.getGymEntity(Integer.parseInt(s)).getGymName());
						
				}
			}
			return listGymsName;
	    }
	    
	    public static synchronized  MapController getSingletonInstance() {
	        if (MapController.instance == null)
	            MapController.instance = new MapController();
	        return instance;
	    }

	
}
