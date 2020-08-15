package logic.calendarutility;

import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.RequestEvent;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recurrenceset.RecurrenceRuleAdapter;
import org.dmfs.rfc5545.recurrenceset.RecurrenceSet;
import org.dmfs.rfc5545.recurrenceset.RecurrenceSetIterator;
import java.io.IOException;

import logic.factory.calendarviewfactory.CalendarViewFactory;
import logic.factory.calendarviewfactory.CalendarViewType;
import logic.view.calendarview.CalendarView;
import logic.controller.MainController;
import logic.factory.alertfactory.AlertFactory;

public class CalendarInitializer {

	private MonthPage monthPage;
	private DayPage dayPage;
	private Entries entries;
	private static final String gym = "gym";
	private CalendarSource calendarSource;
	private Calendars cal;
	private CalendarViewFactory  calendarViewFactory = CalendarViewFactory.getInstance();

	public CalendarInitializer() {

		MainController ctrl = MainController.getInstance();
		this.entries = Entries.getSingletonInstance();
		this.monthPage = new MonthPage();


		cal = Calendars.getSingletonInstance(); 
		calendarSource = new CalendarSource(gym+ctrl.getId());
		calendarSource.getCalendars().addAll(cal.getAvailableCalendar());


		// Entry en = entries.setEntry(2020,8,21,18,15);
		//  cal.getCalendar(4).addEntry(en);
		monthPage.getCalendarSources().addAll(calendarSource);
		this.multiplesEntries();

		this.doubleClickEntry();

		this.rightClickEntry();



	}

	private void rightClickEntry() {
		monthPage.setEntryContextMenuCallback(param -> {
			MenuItem item1 = new MenuItem("Information");
			MenuItem item2 = new MenuItem("Delete this");
			MenuItem item3 = new MenuItem("Delete All");
			MenuItem item4 = new MenuItem("Open gym's event in map");
			MenuItem item5 = new MenuItem("Send e-mail");
			Entry contextEntry = param.getEntry();
			System.out.println(param.getEntry());
			item1.setOnAction(event -> {
				try {
					Stage reviewStage = new Stage();
					reviewStage.initStyle(StageStyle.TRANSPARENT);
					reviewStage.initModality(Modality.APPLICATION_MODAL);
					reviewStage.setMinWidth(335);
					reviewStage.setMinHeight(150);
					CalendarView calendarView = calendarViewFactory.createView(CalendarViewType.REWIES);
					/*
					 * FXMLLoader rootFXML = new
					 * FXMLLoader(getClass().getResource("/logic/fxml/reviewGym.fxml")); Parent root
					 * = rootFXML.load(); ReviewViewController reviewViewController =
					 * rootFXML.getController(); reviewViewController.setView(contextEntry);
					 */
//					ReviewViewController reviewViewController = (ReviewViewController) calendarView.getController();
//					reviewViewController.setView(contextEntry);
					Scene scene = new Scene(calendarView.getRoot());
					reviewStage.setScene(scene);
					reviewStage.showAndWait();
				} catch (IOException e) {
					AlertFactory.getInstance().createAlert(e);
				}
			});
			item2.setOnAction(event -> {
				if (contextEntry.isRecurrence() && !contextEntry.getTitle().contains("New Entry")) {


					if (contextEntry.getRecurrenceRule() == null) {
						contextEntry.removeFromCalendar();
					} else {
						try {
							int currentDayOfMonth = 0;
							int currentMonth = 0;

							String oldRule;
							if (contextEntry.getRecurrenceSourceEntry() != null || contextEntry.getStartDate().getDayOfMonth() != contextEntry.getEndDate().getDayOfMonth()) {
								currentDayOfMonth = contextEntry.getRecurrenceSourceEntry().getStartDate().getDayOfMonth();
								oldRule = contextEntry.getRecurrenceSourceEntry().getRecurrenceRule();
								if (contextEntry.getRecurrenceSourceEntry().getStartDate().getMonth() != contextEntry.getStartDate().getMonth()) {
									currentMonth = contextEntry.getRecurrenceSourceEntry().getStartDate().getMonthValue();
								} else {
									currentMonth = contextEntry.getStartDate().getMonthValue();
								}

							} else {
								currentDayOfMonth = contextEntry.getStartDate().getDayOfMonth();
								oldRule = contextEntry.getRecurrenceRule();


							}
							System.out.println("THIS IS OLD RULE" + oldRule);
							RecurrenceRule oldRRule = new RecurrenceRule(oldRule.replace("RRULE:", ""));
							Entries correctEntry = Entries.getSingletonInstance();

							RecurrenceRule exRRule = new RecurrenceRule("FREQ=YEARLY;INTERVAL=1;BYMONTHDAY=" + contextEntry.getStartDate().getDayOfMonth() + ";BYMONTH=" + contextEntry.getStartDate().getMonthValue() + ";COUNT=1");
							RecurrenceSet recurrenceSet = new RecurrenceSet();
							recurrenceSet.addInstances(new RecurrenceRuleAdapter(oldRRule));
							recurrenceSet.addExceptions(new RecurrenceRuleAdapter(exRRule));
							int currentYear = contextEntry.getStartDate().getYear();
							DateTime start = new DateTime(currentYear, currentMonth - 1, currentDayOfMonth);
							RecurrenceSetIterator recurrenceSetIterator = recurrenceSet.iterator(start.getTimeZone(), start.getTimestamp());
							int maxInstances = 30;
							int dayToRemove = contextEntry.getStartDate().getDayOfMonth();
							int monthToRemove = contextEntry.getStartDate().getMonthValue();
							System.out.println("DAYTOREMOVE=" + dayToRemove);

							String tempCalendar = contextEntry.getCalendar().getName();
							System.out.println("ONLY CURR CALENDAR:" + tempCalendar);
							int tempHour = contextEntry.getStartTime().getHour();
							int tempMin = contextEntry.getStartTime().getMinute();
							contextEntry.removeFromCalendar();

							while (recurrenceSetIterator.hasNext() && (!oldRRule.isInfinite() || maxInstances-- > 0)) {
								// get the next instance of the recurrence set
								DateTime nextInstance = new DateTime(start.getTimeZone(), recurrenceSetIterator.next());
								// do something with nextInstance
								System.out.println("NEXTINSTANCE DAY" + nextInstance.getDayOfMonth() + "\t\tDAYTOREMOVE" + dayToRemove + "\t\t MONTH TO REMOVE:" + (monthToRemove - 1) + "\t\tNEXTINSTANCE MONTH" + nextInstance.getMonth());
								if (nextInstance.getDayOfMonth() != dayToRemove && nextInstance.getMonth() == monthToRemove - 1) {

									System.out.println("SETTO NUVOA ENTRY");
									correctEntry.setEntryCalendar(nextInstance.getYear(), nextInstance.getMonth() + 1, nextInstance.getDayOfMonth(), tempHour, tempMin, cal.getCalendarById(tempCalendar));

								} else if (nextInstance.getDayOfMonth() != dayToRemove && nextInstance.getMonth() != monthToRemove - 1) {
									correctEntry.setEntryCalendar(nextInstance.getYear(), nextInstance.getMonth() + 1, nextInstance.getDayOfMonth(), tempHour, tempMin, cal.getCalendarById(tempCalendar));

								}
							}
						} catch (InvalidRecurrenceRuleException e) {
							AlertFactory.getInstance().createAlert(e);
						}
					}


				} else if (!contextEntry.isRecurrence() && !contextEntry.getTitle().contains("New Entry")) {
					param.getEntry().removeFromCalendar();
				} else {
					contextEntry.removeFromCalendar();
				}

			});
			item3.setOnAction(event -> contextEntry.getCalendar().clear());

			//Email sender
			item5.setOnAction(event -> {
				try {

					Stage emlStage = new Stage();
					emlStage.initStyle(StageStyle.TRANSPARENT);
					emlStage.initModality(Modality.APPLICATION_MODAL);
					emlStage.setMinWidth(335);
					emlStage.setMinHeight(150);

					CalendarView calendarView = calendarViewFactory.createView(CalendarViewType.EMAIL);
					//EmailViewController emailViewController = rootFXML.getController();
					//emailViewController.setEvent(contextEntry.getCalendar().getName(), contextEntry.getStartTime(), ctrl.getId());
					//EmailViewController emailViewController = (EmailViewController) calendarView.getController();

					Scene scene = new Scene(calendarView.getRoot());
					emlStage.setScene(scene);
					emlStage.showAndWait();

				} catch (IOException e) {
					AlertFactory.getInstance().createAlert(e);
				}
			});

			ContextMenu rBox = new ContextMenu();
			rBox.getItems().addAll(item1, item2, item3, item4, item5);


			return rBox;
		});		
	}

	private void doubleClickEntry() {
		monthPage.setEntryDetailsPopOverContentCallback(param -> {
			try {
				CalendarView calendarView = calendarViewFactory.createView(CalendarViewType.MAINPOPUP);
			
				
				/*
				 * PopupViewController popupViewController = (PopupViewController)
				 * calendarView.getController(); popupViewController.setParam(param);
				 * popupViewController.setSelectedEvent();
				 * popupViewController.setDetailsPopup();
				 */
				 
				
				HBox box = new HBox();
				box.getChildren().add(calendarView.getRoot());

				return box;
			} catch (IOException e) {
				AlertFactory.getInstance().createAlert(e);
			}

			return null;
		});		
	}

	private void multiplesEntries() {
		monthPage.addEventFilter(RequestEvent.REQUEST_DATE, event -> {
			try {
			Stage stage = new Stage();
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.initModality(Modality.APPLICATION_MODAL);
			/*
			 * FXMLLoader rootFXML = new
			 * FXMLLoader(getClass().getResource("/logic/fxml/fullDay.fxml")); Parent root =
			 * rootFXML.load(); FullDayViewController fullDayViewController =
			 * rootFXML.getController();
			 * fullDayViewController.setCalendarSource(calendarSource);
			 */
			CalendarView calendarView = calendarViewFactory.createView(CalendarViewType.FULLDAY);
			/*
			 * FullDayViewController fullDayViewController = (FullDayViewController)
			 * calendarView.getController();
			 * fullDayViewController.setCalendarSource(calendarSource);
			 */
			
			Scene scene = new Scene(calendarView.getRoot());
			stage.setScene(scene);
			stage.showAndWait();
			} catch (IOException e) {
				AlertFactory.getInstance().createAlert(e);
			}

		});		
	}

	public MonthPage getMonthPage() {
		return monthPage;
	}

	public void setMonthPage(MonthPage monthPage) {
		this.monthPage = monthPage;
	}






}
