/*
 * 
 */
package enstabretagne.travaux_diriges.TD_corrige.HelloWorld.SimEntity;

import java.util.List;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.components.implementation.SimEntity;
import enstabretagne.simulation.core.ISimObject;
import enstabretagne.simulation.core.implementation.SimEvent;
import enstabretagne.travaux_diriges.TD_corrige.HelloWorld.expertise.Film;

// TODO: Auto-generated Javadoc
/**
 * The Class Student.
 */
public class Student extends SimEntity{


	/**
	 * Instantiates a new student.
	 *
	 * @param name the name
	 * @param features the features
	 */
	public Student(String name, SimFeatures features) {
		super(name, features);
		
	}

	/**
	 * Gets the nom.
	 *
	 * @return the nom
	 */
	public String getNom()
	{
		return ((StudentInit) getInitParameters()).getNom();
	}
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimEntity#onParentSet()
	 */
	@Override
	public void onParentSet() {
		
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimEntity#initializeSimEntity(enstabretagne.simulation.components.data.SimInitParameters)
	 */
	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		Logger.Information(this, "initializeSimEntity", getNom() + "s'initialise.");
	}	
	

	/**
	 * Talk to.
	 *
	 * @param whoIsSpeaking the who is speaking
	 * @param message the message
	 */
	public void talkTo(Student whoIsSpeaking,String message) {
		double d = RandomGenerator().nextUniform(2, 6);//on répond entre 2 et 6 secondes.
		LogicalDuration ld = LogicalDuration.ofSeconds(d);
		Post(new RepondreEvent(whoIsSpeaking),ld);
		Logger.Information(this, "talkTo", whoIsSpeaking.getNom() + " vient de me dire : "+ message+ " et je vais lui répondre dans "+ld);
	}
	

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimEntity#BeforeTerminating(enstabretagne.simulation.components.IEntity, boolean)
	 */
	@Override
	protected void BeforeTerminating(IEntity sender, boolean restart) {
		Logger.Information(this, "BeforeTerminating", this.getName()+" est Terminating");
		super.BeforeTerminating(sender, restart);
	}
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimEntity#AfterTerminated(enstabretagne.simulation.components.IEntity, boolean)
	 */
	@Override
	protected void AfterTerminated(IEntity sender, boolean restart) {
		Logger.Information(this, "AfterTerminated", this.getName()+" est terminé");
	}
	
	/**
	 * The Class RepondreEvent.
	 */
	public class RepondreEvent extends SimEvent {
		
		/** The destinataire. */
		Student destinataire;

		/**
		 * Instantiates a new repondre event.
		 *
		 * @param destinataire the destinataire
		 */
		public RepondreEvent(Student destinataire) {
			this.destinataire = destinataire;
		}
		
		/* (non-Javadoc)
		 * @see enstabretagne.simulation.core.ISimEvent#Process()
		 */
		@Override
		public void Process() {
			Logger.Information(Owner(), "RepondreEvent.Process", getNom() + " répond à "+destinataire.getNom());
			destinataire.talkTo((Student) Owner(), ((StudentInit)getInitParameters()).getMessage());
		}
		
	}
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimEntity#AfterActivate(enstabretagne.simulation.components.IEntity, boolean)
	 */
	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		Logger.Information(this, "AfterActivate", getName() + "AfterActivate");
		List<ISimObject> friends = getEngine().requestSimObject(this::peopleILike);
		if(friends.size()>0) {
			for(ISimObject friend:friends)
				((Student) friend).talkTo(this, ((StudentInit) getInitParameters()).getMessage());
		}
		else {
			getEngine().AddSimObjectAddedListener(this::attendreNouvelArrivant);
			Logger.Information(this, "AfterActivate", "Je suis triste, il n'y a pas d'amis");
		}
	}
	
	/**
	 * Attendre nouvel arrivant.
	 *
	 * @param obj the obj
	 */
	void attendreNouvelArrivant(ISimObject obj) {
		if(obj instanceof Student){
			Student s = (Student) obj;
			s.OnActivated().add(this::accueillirNouvelArrivantActif);
		}
	}
	
	/**
	 * Accueillir nouvel arrivant actif.
	 *
	 * @param sender the sender
	 * @param starting the starting
	 */
	void accueillirNouvelArrivantActif(IEntity sender, boolean starting){
		if(sender instanceof Student){
			Student s = (Student) sender;
			Logger.Information(this, "accueillirNouvelArrivant", getName() + " accueille "+s.getName());
		}		
	}
	
	/**
	 * Do you like.
	 *
	 * @param f the f
	 * @return true, if successful
	 */
	public boolean doYouLike(Film f) {
		return ((StudentInit)getInitParameters()).getLikedFilms().contains(f);
			
	}
	
	/**
	 * People I like.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	private boolean peopleILike(ISimObject o){
		if(o==this) return false;
		
		if(o instanceof Student)
		{
			Student std = (Student) o;
			
			boolean iLike=true;
			for(Film f: ((StudentInit)getInitParameters()).getLikedFilms())
				iLike = iLike & std.doYouLike(f);

			return iLike;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimEntity#BeforeDeactivating(enstabretagne.simulation.components.IEntity, boolean)
	 */
	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		Logger.Information(this, "BeforeDeactivating", this.getName() + "BeforeDeactivating");		
		
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimEntity#BeforeActivating(enstabretagne.simulation.components.IEntity, boolean)
	 */
	@Override
	protected void BeforeActivating(IEntity sender, boolean starting) {
		Logger.Information(this, "BeforeActivating", this.getName() + "BeforeActivating");		
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimEntity#AfterDeactivated(enstabretagne.simulation.components.IEntity, boolean)
	 */
	@Override
	protected void AfterDeactivated(IEntity sender, boolean starting) {
		Logger.Information(this, "AfterDeactivated", this.getName() + "AfterDeactivated");
	}
	
}
