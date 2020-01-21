package enstabretagne.travaux_diriges.TD_corrige.EngineSimple.Framework;

import java.util.ArrayList;
import java.util.List;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.SortedList;

public class SimuEngine {
	
	private SortedList<SimEvent> echeancier;
	private LogicalDateTime start;
	private LogicalDateTime end;
	private LogicalDateTime currentDate;
	protected List<EntiteSimulee> mesEntitesSimulees; 
	
	protected LogicalDateTime getCurrentDate()
	{
		return currentDate;
	}
	
	public SimuEngine() {
		echeancier = new SortedList<>();
		mesEntitesSimulees = new ArrayList<>();
	}
	
	public void initSimulation(LogicalDateTime start, LogicalDateTime end) {
		this.start=start;
		currentDate = this.start;
		this.end = end;
		
		for(EntiteSimulee e:mesEntitesSimulees)
		{
			e.Init();
		}
	}
	
	protected void Post(SimEvent ev) {
		echeancier.add(ev);
	}
	
	public void simulate()
	{
		while(hasANextEvent())
		{
			SimEvent ev = echeancier.first();
			echeancier.remove(ev);
			currentDate = ev.getDateOccurence();
			ev.process();
		}
	}
	
	private boolean hasANextEvent() {
		for(SimEvent e:echeancier)
		{
			if(e.getDateOccurence().compareTo(end)<=0)
				return true;
		}
		return false;
	}
	
}
