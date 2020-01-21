/*
 * 
 */
package enstabretagne.travaux_diriges.TD_corrige.HelloWorld.expertise;

// TODO: Auto-generated Javadoc
/**
 * The Class Film.
 */
public class Film {
	
	/** The nom. */
	String nom;
	
	/**
	 * Gets the nom.
	 *
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Instantiates a new film.
	 *
	 * @param nom the nom
	 */
	public Film(String nom) {
		super();
		this.nom = nom;
	}
	
	/** The Constant starwars. */
	public static final Film starwars = new Film("Starwars");
	
	/** The Constant sissi. */
	public static final Film sissi = new Film("Sissi");
	
	/** The Constant rambo. */
	public static final Film rambo = new Film("Rambo");
}
