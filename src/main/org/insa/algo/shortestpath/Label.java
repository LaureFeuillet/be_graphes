package org.insa.algo.shortestpath;

import org.insa.graph.*;

public class Label 
{
	private double cout;
	private Node precedent;
	private boolean marquage;
	
	public Label(double init_cout, Node init_precedent, boolean init_marquage)
	{
		this.cout = init_cout;
		this.precedent = init_precedent;
		this.marquage = init_marquage;
	}

	public double getCout() {
		return cout;
	}

	public void setCout(double cout) {
		this.cout = cout;
	}

	public Node getPrecedent() {
		return precedent;
	}

	public void setPrecedent(Node precedent) {
		this.precedent = precedent;
	}

	public boolean isMarquage() {
		return marquage;
	}

	public void setMarquage(boolean marquage) {
		this.marquage = marquage;
	}
	
}