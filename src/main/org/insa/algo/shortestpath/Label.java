package org.insa.algo.shortestpath;

import org.insa.graph.*;

public class Label implements Comparable<Label> 
{
	private int id;
	private double cout;
	private Arc arcVersPrecedent;
	private boolean marquage;
	
	public Label( int init_id, double init_cout, Arc init_precedent, boolean init_marquage)
	{
		this.cout = init_cout;
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
		return cout;
	}

	public void setCout(double cout) {
		this.cout = cout;
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
	
	public int compareTo(Label o)
	{
		int result;
		double i;
		i =  this.getCout() - o.getCout();
		if(i<0)
		{
			result = -1;
		}
		if(i==0)
		{
			result = 0;
		}
		else
		{
			result = 1;
		}
		return result;
	}
}