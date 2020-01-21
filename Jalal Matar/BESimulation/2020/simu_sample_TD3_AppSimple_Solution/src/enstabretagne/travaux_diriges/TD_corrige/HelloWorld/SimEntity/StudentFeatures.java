/*
 * 
 */
package enstabretagne.travaux_diriges.TD_corrige.HelloWorld.SimEntity;

import enstabretagne.simulation.components.data.SimFeatures;

// TODO: Auto-generated Javadoc
/**
 * The Class StudentFeatures.
 */
public class StudentFeatures extends SimFeatures{
	
	/**
	 * Instantiates a new student features.
	 *
	 * @param id the id
	 */
	private StudentFeatures(String id){
		super(id);
	}
	
	/** The Constant defaultFeatures. */
	public static final StudentFeatures defaultFeatures = new StudentFeatures("Default");

}
