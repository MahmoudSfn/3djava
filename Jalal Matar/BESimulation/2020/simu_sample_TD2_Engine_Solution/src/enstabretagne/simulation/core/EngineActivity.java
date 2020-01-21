/**
* Classe EngineActivity.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core;

// TODO: Auto-generated Javadoc
/**
 * The Class EngineActivity.
 */
public final class EngineActivity {
    // ----------------------------------------------------------------
    // States where engine is stopped
    // ----------------------------------------------------------------

    /**
     * Instantiates a new engine activity.
     *
     * @param activity the activity
     * @param name the name
     */
    public EngineActivity(int activity,String name) {
		engineActivityValue = activity;
		this.name=name;
	}
    
    /** The name. */
    private String name;
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName(){
    	return name;
    }
    
    /** The engine activity value. */
    private int engineActivityValue;
    
    /**
     * Engine activity value.
     *
     * @return the int
     */
    public int engineActivityValue()
    {
    	return engineActivityValue;
    }

	/// <summary>
    /// Unknown value used in journal message when no engine is attached to
    /** The Constant Unknown. */
	/// </summary>
    public static final EngineActivity Unknown = new EngineActivity(0x0000,"Inconnu");        //None

    /// <summary>
    /// The engine is stopped and not even initialized. No operations
    /// are permitted (permission is <c>None</c>)
    /** The Constant NotInitialized. */
    /// </summary>
    public static final EngineActivity NotInitialized = new EngineActivity(0x0001,"Not Initialized");       //None

    /// <summary>
    /// The engine is initialized. Any operation is permitted (permission is <c>ReadWrite</c>)
    /** The Constant Initializing. */
    /// </summary>
    public static final EngineActivity Initializing=new EngineActivity(0x0002,"Initializing");       //ReadWrite

    /// <summary>
    /// The engine is Finalizing. Any operation is permitted (permission is <c>ReadWrite</c>)
    /** The Constant Finalizing. */
    /// </summary>
    public static final EngineActivity Finalizing=new EngineActivity(0x0004,"Finalizing");       //ReadWrite

    // ----------------------------------------------------------------
    // States where engine is paused
    // ----------------------------------------------------------------
    /// <summary>
    /// The engine is paused. Any operation is permitted (permission is <c>ReadWrite</c>)
    /** The Constant PausedWaiting. */
    /// </summary>
    public static final EngineActivity PausedWaiting=new EngineActivity(0x0008,"Pause");       //ReadWrite

    // ----------------------------------------------------------------
    // States where engine is running
    // ----------------------------------------------------------------
    /// <summary>
    /// The engine is running and processing a <see cref="T:DirectSim.Simulation.Core.SimTimeEvent"/>.
    /// Any operation is permitted (permission is <c>ReadWrite</c>)
    /** The Constant TimeEvent. */
    /// </summary>
    public static final EngineActivity  TimeEvent=new EngineActivity(0x0010,"Time Events");       //ReadWrite

    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	return name;
    }

}

