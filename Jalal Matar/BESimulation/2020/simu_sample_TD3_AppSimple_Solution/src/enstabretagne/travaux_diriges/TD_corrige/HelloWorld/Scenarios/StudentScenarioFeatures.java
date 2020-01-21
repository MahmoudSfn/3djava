/*
 * 
 */
package enstabretagne.travaux_diriges.TD_corrige.HelloWorld.Scenarios;

import java.util.HashMap;

import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.travaux_diriges.TD_corrige.HelloWorld.SimEntity.StudentFeatures;
import enstabretagne.travaux_diriges.TD_corrige.HelloWorld.SimEntity.StudentInit;

// TODO: Auto-generated Javadoc
/**
 * The Class StudentScenarioFeatures.
 */
public class StudentScenarioFeatures extends SimFeatures {
	
	/** The students. */
	private HashMap<StudentInit,StudentFeatures> students;
	
	/**
	 * Gets the students.
	 *
	 * @return the students
	 */
	public HashMap<StudentInit,StudentFeatures> getStudents() {
		return students;
	}

	/**
	 * Instantiates a new student scenario features.
	 *
	 * @param scenName the scen name
	 * @param students the students
	 */
	public StudentScenarioFeatures(String scenName,HashMap<StudentInit,StudentFeatures> students) {
		super(scenName);
		this.students=students;
	}
}
