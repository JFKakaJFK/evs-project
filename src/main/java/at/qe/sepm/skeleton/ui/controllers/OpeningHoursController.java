package at.qe.sepm.skeleton.ui.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.context.annotation.Scope;



@Controller
@Scope("request")
public class OpeningHoursController {
	
	private Set<Calendar> openingHours = new HashSet<Calendar>();
	private Date startTime; 
	private Date endTime;
	private Date startPause;
	private Date endPause;
	
	public void doReloadHours(){
		
		//TODO
		
	}
	
	public void doSaveHours(){
		//TODO
	}

	public Set<Calendar> getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(Set<Calendar> openingHours) {
		this.openingHours = openingHours;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartPause() {
		return startPause;
	}

	public void setStartPause(Date startPause) {
		this.startPause = startPause;
	}

	public Date getEndPause() {
		return endPause;
	}

	public void setEndPause(Date endPause) {
		this.endPause = endPause;
	}
	
	
}