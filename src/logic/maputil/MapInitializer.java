package logic.maputil;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

public class MapInitializer implements MapComponentInitializedListener {
	private GoogleMapView view;
	private static final String STYLE ="[{'featureType':'landscape','stylers':[{'saturation':-100},{'lightness':65},{'visibility':'on'}]},"
			+ "{'featureType':'poi','stylers':[{'saturation':-100},{'lightness':51},{'visibility':'simplified'}]},"
			+ "{'featureType':'road.highway','stylers':[{'saturation':-100},{'visibility':'simplified'}]},"
			+ "{\"featureType\":\"road.arterial\",\"stylers\":[{\"saturation\":-100},{\"lightness\":30},{\"visibility\":\"on\"}]},"
			+ "{\"featureType\":\"road.local\",\"stylers\":[{\"saturation\":-100},{\"lightness\":40},{\"visibility\":\"on\"}]},"
			+ "{\"featureType\":\"transit\",\"stylers\":[{\"saturation\":-100},{\"visibility\":\"simplified\"}]},"
			+ "{\"featureType\":\"administrative.province\",\"stylers\":[{\"visibility\":\"off\"}]},"
			+ "{\"featureType\":\"water\",\"elementType\":\"labels\",\"stylers\":[{\"visibility\":\"on\"},"
			+ "{\"lightness\":-25},{\"saturation\":-100}]},"
			+ "{\"featureType\":\"water\",\"elementType\":\"geometry\",\"stylers\":[{\"hue\":\"#ffff00\"},{\"lightness\":-25},{\"saturation\":-97}]}]";
	public GoogleMapView getView() {
		return view;
	}

	public void setView(GoogleMapView view) {
		this.view = view;
	}

	private GoogleMap map;

	public MapInitializer() {
		super();
		this.view = new GoogleMapView();
		this.view.setKey("AIzaSyDP-NfD5FVlNeLw52M7Ff_HPa8K3MByAa8");
		this.view.addMapInitializedListener(this);
	}

	@Override
	public void mapInitialized() {
		LatLong center = new LatLong(47.606189, -122.335842);
		view.addMapReadyListener(() -> { // This call will fail unless the map is completely ready.
		});
		MapOptions options = new MapOptions();
		options.center(center).zoom(9).overviewMapControl(false).panControl(false).rotateControl(false)
				.scaleControl(false).streetViewControl(false).zoomControl(false).mapType(MapTypeIdEnum.TERRAIN)
				.styleString(STYLE);

		setMap(view.createMap(options, false));
	}

	public GoogleMap getMap() {
		return map;
	}

	public void setMap(GoogleMap map) {
		this.map = map;
	}
}
