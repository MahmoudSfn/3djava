/*
 * 
 */
package enstabretagne.travaux_diriges.TD_corrige.HelloWorld.Scenarios;

import java.util.Map;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.components.implementation.SimEntity;
import enstabretagne.simulation.components.implementation.SimScenario;
import enstabretagne.simulation.core.implementation.SimEvent;
import enstabretagne.travaux_diriges.TD_corrige.HelloWorld.SimEntity.Student;
import enstabretagne.travaux_diriges.TD_corrige.HelloWorld.SimEntity.StudentFeatures;
import enstabretagne.travaux_diriges.TD_corrige.HelloWorld.SimEntity.StudentInit;

// TODO: Auto-generated Javadoc
/**
 * The Class StudentScenario.
 */
public class StudentScenario extends SimScenario{

	/**
	 * Instantiates a new student scenario.
	 *
	 * @param id the id
	 * @param features the features
	 * @param start the start
	 * @param end the end
	 */
	public StudentScenario(ScenarioId id, SimFeatures features, LogicalDateTime start, LogicalDateTime end) {
		super(id, features, start, end);
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimScenario#initializeSimEntity(enstabretagne.simulation.components.data.SimInitParameters)
	 */
	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		super.initializeSimEntity(init);
		
		for(Map.Entry<StudentInit, StudentFeatures> entry : ((StudentScenarioFeatures) getFeatures()).getStudents().entrySet())
		{
			double nextStudentArrivaldelay = getEngine().getRandomGenerator().nextUniform(2, 3);
			Post(new StudentArrival(entry.getKey(), entry.getValue()),LogicalDuration.ofSeconds(nextStudentArrivaldelay));
		}
	}
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimScenario#AfterActivate(enstabretagne.simulation.components.IEntity, boolean)
	 */
	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		super.AfterActivate(sender, starting);
		
	}
	
	/**
	 * The Class StudentArrival.
	 */
	class StudentArrival extends SimEvent {
		
		/** The init. */
		StudentInit init;
		
		/** The feat. */
		StudentFeatures feat;
		
		/**
		 * Instantiates a new student arrival.
		 *
		 * @param init the init
		 * @param feat the feat
		 */
		public StudentArrival(StudentInit init, StudentFeatures feat) {
			this.init = init;
			this.feat = feat;
		}
		
		/* (non-Javadoc)
		 * @see enstabretagne.simulation.core.ISimEvent#Process()
		 */
		@Override
		public void Process() {
			SimEntity e=createChild(Student.class,init.getNom(), feat);			
			e.initialize(init);
			e.activate();
		}
		
	}

}
