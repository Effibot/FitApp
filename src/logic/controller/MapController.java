package logic.controller;

import java.util.ArrayList;
import java.util.List;

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.util.MarkerImageFactory;

import logic.maputil.Geocode;
import logic.model.dao.GymDAO;
import logic.model.dao.SessionDAO;
import logic.model.dao.UserDAO;
import logic.model.entity.Gym;
import logic.model.entity.Session;
import logic.model.entity.User;

public class MapController {
	 private static MapController instance = null;
	    private LatLong base;
	    private Marker newMarker;
	    private LatLong posit;
	    private List<Marker> listMarker = new ArrayList<>();
	    private List<String> listGymsName = new ArrayList<>();
	    private SessionDAO dao = SessionDAO.getInstance();
	    private GymDAO daoGym = GymDAO.getInstance();
	    private UserDAO userDAO = UserDAO.getInstance();
	    private Marker marker;
	    private List<Integer> listIdBookedSession= new ArrayList<>();
	    private List<Session> listBookedSession = new ArrayList<>();
	    private String center;
	    protected MapController() {

	    }

	    


	    public List<Marker> getListMarker() {
	        return listMarker;
	    }

	    public List<Session> getListIdGym() {
	        return listIdGym;
	    }
	    

	    
	    public void setListIdGym(List<Session> listIdGym2) {
	        this.listIdGym = listIdGym2;
	    }

	    private List<Session> listIdGym = new ArrayList<>();

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

	    public void startGeocode(String data, String timeStart, double distance,String  baseAddress, int event, int userId){
	    	
	    	listIdBookedSession= dao.getBookedSessionById(userId);
	    	System.out.println("SONO QUI");
	        for(Integer bookedId:listIdBookedSession) {
	        	System.out.println(bookedId);
	        	Session bookedSessionEntitySession = dao.getBookedSessionEntity(bookedId);
	        	listBookedSession.add(bookedSessionEntitySession);
	        }
	        System.out.println("FUORI DAL CICLO"+ listIdBookedSession);
	        for (Session s: listBookedSession) {
				System.out.println(s.getGym());
			}
	        this.setListMarker(this.distance(data,timeStart,distance,baseAddress, event));
	    }
	    

	    public List<Marker> distance(String data, String timeStart, double dist, String baseAddress, int event) {
	        LatLong endPoint;
	    
	        double distance = dist;
	        if(event == 0) {
	        	listIdGym = dao.getEventList(data, timeStart);
	        }else {

	        	listIdGym = dao.getGymListEvent(data, timeStart,event);

	        }
	        for(Session idGym:listIdGym) {
	        	for(Session singleSession:listBookedSession) {
	        		if (idGym.getSessionId()== singleSession.getSessionId()) {

						listIdGym.remove(idGym);
					}
	        	}
	        }
	        
	        this.setCenter(baseAddress);


	        Geocode pos = Geocode.getSingletonInstance();
	        pos.getLocation(baseAddress);
	        base = pos.getCoordinates();
			Marker baseMarker = this.nMarker(base, "You are Here!", baseAddress, null);
	        listMarker.add(baseMarker);
	        System.out.println(listIdGym.size());
	        for(int i = listIdGym.size()-1; i>=0;--i) {
	        	Session tempList;
	        	tempList = listIdGym.get(i);
	        	

	            Gym gymEntity = daoGym.getGymEntityById(Integer.parseInt(tempList.getGym()));
	            pos.getLocation(gymEntity.getStreet());
	            endPoint = pos.getCoordinates();
	            double relativeDistance = distanceRelative(base.getLatitude(), endPoint.getLatitude(), base.getLongitude(), endPoint.getLongitude());
	            if (Double.compare(relativeDistance, distance)<0) {
					tempList.setCourseName(dao.getCourseById(tempList.getCourseId()));
					newMarker = nMarker(endPoint, gymEntity.getGymName(), gymEntity.getStreet(),
							tempList.getCourseName());
	                listMarker.add(newMarker);
	                tempList.setGym(gymEntity.getGymName());
	                tempList.setCourseName(dao.getCourseById(tempList.getCourseId()));
					
	            } 
	            else{
	            	
	            	listIdGym.remove(tempList);
	


	            }
	        }
	
	        this.setListIdGym(listIdGym);
	    

	        return listMarker;

	    }

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


		public Marker nMarker(LatLong position, String name, String address, String courseName) {
	        if (position == null)
	            return null;

	        String pathUser = MarkerImageFactory.createMarkerImage("/icons/pathUser.png", "png");
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
						.title(name + "\t" + courseName)
	                    .icon(pathGym);


	        }

	        marker = new Marker(markerOptions);
	        return marker;
	    }
	    

	    
	    public static synchronized  MapController getSingletonInstance() {
	        if (MapController.instance == null)
	            MapController.instance = new MapController();
	        return instance;
	    }

		public void wipeAll() {
			this.listGymsName.clear();
			this.listIdGym.clear();
			this.listMarker.clear();
		}

		public String getUserStreet(int i) {
			User user = userDAO.getUserEntity(i);
			return user.getMyPosition();
		}

	




	
}
