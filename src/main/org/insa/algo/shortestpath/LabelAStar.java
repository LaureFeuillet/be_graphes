package org.insa.algo.shortestpath;

import org.insa.graph.*;

public class LabelAStar implements Comparable<LabelAStar> 
{
	private int id;
	private double cout;
	private Arc arcVersPrecedent;
	private boolean marquage;
	private double estimation;
	
	public LabelAStar( int init_id, double init_cout, Arc init_precedent, boolean init_marquage, double init_estimation)
	{
		this.cout = init_cout;
		this.estimation = init_estimation;
		this.arcVersPrecedent = init_precedent;
		this.marquage = init_marquage;
		this.id = init_id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int x) {
		this.id =  x;
	}
	
	public double getCout() {
		return this.cout;
	}

	public void setCout(double cout) {
		this.cout = cout;
	}
	
	public void setEstimation(double estimation) {
		this.estimation = estimation;
	}

	public double getEstimation() {
		return this.estimation;
	}
	
	public Arc getPrecedent() {
		return arcVersPrecedent;
	}

	public void setPrecedent(Arc precedent) {
		this.arcVersPrecedent = precedent;
	}

	public boolean isMarquage() {
		return marquage;
	}

	public void setMarquage(boolean marquage) {
		this.marquage = marquage;
	}
	
	public int compareTo(LabelAStar o)
	{
		int result;
		double i;
		i =  (this.getCout() + this.getEstimation()) - (o.getCout() + o.getEstimation());
		if(i<0)
		{
			result = -1;
		}
		else if(i==0)
		{
			result = 0;
		}
		else
		{
			result = 1;
		}
		return result;
	}
	
	public String toString()
	{
		return "id = " + this.id + "  cout = " +this.cout+ "   estimation = " +this.estimation;
	}
}