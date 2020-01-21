/*
 * 
 */
package enstabretagne.simulation.components.data;

// TODO: Auto-generated Javadoc
/**
 * The Class SimScenarioInit.
 */
public class SimScenarioInit extends SimInitParameters  {
	
	/** The seed. */
	private long seed;
	
	/** The replique num. */
	private long repliqueNum;
	
	
	/**
	 * Gets the seed.
	 *
	 * @return the seed
	 */
	public long getSeed() {
		return seed;
	}



	/**
	 * Instantiates a new sim scenario init.
	 *
	 * @param seed the seed
	 */
	public SimScenarioInit(long seed) {
		this(seed,0);
	}



	/**
	 * Gets the replique num.
	 *
	 * @return the replique num
	 */
	public long getRepliqueNum() {
		return repliqueNum;
	}



	/**
	 * Instantiates a new sim scenario init.
	 *
	 * @param seed the seed
	 * @param repliqueNum the replique num
	 */
	public SimScenarioInit(long seed, long repliqueNum) {
		super();
		this.seed = seed;
		this.repliqueNum = repliqueNum;
	}
	
	
	

}
