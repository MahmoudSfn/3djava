/*
 * 
 */
package enstabretagne.travaux_diriges.TD_corrige.HelloWorld.SimEntity;

import java.util.Arrays;
import java.util.List;

import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.travaux_diriges.TD_corrige.HelloWorld.expertise.Film;

// TODO: Auto-generated Javadoc
/**
 * The Class StudentInit.
 */
public class StudentInit extends SimInitParameters{
	
	/** The message. */
	private String message;
	
	/** The liked films. */
	private List<Film> likedFilms;
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Gets the liked films.
	 *
	 * @return the liked films
	 */
	public List<Film> getLikedFilms() {
		return likedFilms;
	}
	
	/** The nom. */
	private String nom;
	
	/**
	 * Gets the nom.
	 *
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Instantiates a new student init.
	 *
	 * @param nom the nom
	 * @param message the message
	 * @param films the films
	 */
	public StudentInit(String nom,String message,Film...films) {
		super();
		this.nom=nom;
		this.message = message;
		likedFilms = Arrays.asList(films);
	}
	
	
}
