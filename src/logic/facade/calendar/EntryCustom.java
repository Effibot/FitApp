package logic.facade.calendar;

import com.calendarfx.model.Entry;

import logic.entity.Session;

public class EntryCustom<T> extends Entry<T>{
	private Entry entry;
	

	private Session session;
	
	public EntryCustom(Entry<?> entry, Session session){
		this.entry = entry;
		this.session = session;
	}
	public Entry<?> getEntry() {
		return entry;
	}
	public void setEntry(Entry<?> entry) {
		this.entry = entry;
	}
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
}
