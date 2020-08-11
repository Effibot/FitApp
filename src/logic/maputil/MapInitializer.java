package logic.maputil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXListView;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.controller.MapController;
import logic.entity.Session;
import logic.factory.alertfactory.AlertFactory;
import logic.viewcontroller.GymPopupViewController;

public class MapInitializer implements MapComponentInitializedListener {
	private GoogleMapView views;
	
	private MapController search = MapController.getSingletonInstance();
	public GoogleMapView getView() {
		return views;
	}

	

	private GoogleMap map;
	ListView<Label> listCell;

	List<Marker> mark;
	private String date = null;
	private String time = null;
	private int event = 0;
	private double radius = 0;
	public MapInitializer(String date, String time, int event, double radius) {
		super();

		this.views = new GoogleMapView();
		this.views.setKey("AIzaSyDP-NfD5FVlNeLw52M7Ff_HPa8K3MByAa8");
		this.views.addMapInitializedListener(this);
		this.date = date;
		this.time = time;
		this.event = event;
		this.radius = radius;

	}

	@Override
	public void mapInitialized() {
		views.addMapReadyListener(() -> { // This call will fail unless the map is completely ready.
		});
		
		search.startGeocode(this.date, this.time, this.radius, "via principessa pignatelli 8 Ciampino", this.event);
		List<Session> listEvent = search.getListIdGym();		
		mark = new ArrayList<>();
		MapOptions mapOptions = new MapOptions();
		mapOptions.center(search.getBase())
		.mapType(MapTypeIdEnum.ROADMAP)
		.panControl(true)
		.rotateControl(false)
		.scaleControl(false)
		.streetViewControl(false)
		.zoomControl(true)
		.zoom(14);

		map = views.createMap(mapOptions);

		mark = search.getListMarker();


		map.addMarkers(mark);
		int numberElement = 1;
		for(Session s: listEvent ) {

			Label lbl = new Label(s.getGym());
			listCell.getItems().add(lbl);
			
			listCell.prefHeight(lbl.getHeight()*numberElement);
			numberElement++;

		}
		for (Marker i : mark) {
			map.addUIEventHandler(i, UIEventType.click, e -> this.startUpPopup(i,listEvent));
			}
			int numberElements = numberElement;
			listCell.setOnMouseClicked(e->{
				Label selectedItem = listCell.getSelectionModel().getSelectedItem();
				if(selectedItem!=null) {
					for(int i = 0; i<numberElements; i++) {
						if(selectedItem.getText().contentEquals(mark.get(i).getTitle())) {
							this.startUpPopup(mark.get(i), listEvent);
							break;
						} 
					}
					
					listCell.getSelectionModel().clearSelection();
				}
				
			});
		
	}



	public GoogleMap getMap() {
		return map;
	}

	public void setMap(GoogleMap map) {
		this.map = map;
	}



	public void startUpPopup(Marker i, List<Session> list) {
		try {
				if(!i.getTitle().contentEquals("You are Here!")) {
					Stage window = new Stage(); 
					window.initStyle(StageStyle.UNDECORATED);
					window.initModality(Modality.APPLICATION_MODAL); 
					window.setMinWidth(400);
					window.setMinHeight(150); 
					FXMLLoader rootFXML = new FXMLLoader(getClass().getResource("/logic/fxml/gymMapPopup.fxml"));
					Parent root = rootFXML.load(); 
					GymPopupViewController gymDialogView =rootFXML.getController();
					gymDialogView.setPopupView(i,list);
		
					Scene scene = new Scene(root);
					window.setScene(scene); 
					window.showAndWait();
				}
		} 
		catch (IOException ex) { AlertFactory.getInstance().createAlert(ex);

		}

	}

	public void setListCell(JFXListView<Label> listCell) {
		this.listCell = listCell;
	}



}
