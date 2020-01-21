/**
* Classe EngineInterruptReason.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core;

// TODO: Auto-generated Javadoc
/// <summary>
/// State of the simulation engine (from an external point of view)
/**
 * The Enum EngineInterruptReason.
 */
/// </summary>
public enum EngineInterruptReason {
      /// <summary>
      /// No interruption is required
      /** The By none. */
      /// </summary>
      ByNone,

      /// <summary>
      /// Engine is interrupted because there are no more <see cref="Simulation.Core.SimTimeEvent">TimeEvent</see> to process
      /// nor any active <see cref="DirectSim.Simulation.Core.SimContinuous">Continous variable</see> 
      /** The By empty. */
      /// </summary>
      ByEmpty,    // neither TimeEvent nor Continuous 

      /// <summary>
      /// Engine was interrupted by user using keyboard or mouse.
      /** The By user. */
    /// </summary>
      ByUser,     // user inturruption using keyboard or mouse

      /// <summary>
      /// Engine was interrupted because of the occurrence of a scheduled interruption.
      /** The By date. */
     /// </summary>
      ByDate,     // occurrence of a scheduled interruption

      /// <summary>
      /// Engine was interrupted because of the occurrence of a scheduled interruption.
      /** The By max date. */
     /// </summary>
      ByMaxDate,     // occurrence of a scheduled interruption

      /// <summary>
      /// Interruption was required by a SimObject (available for SimAgent only)
      /** The By object. */
     /// </summary>
      ByObject,   // required by an SimObject 

      /// <summary>
      /// Engine was stopped (pause) on user request or programmatically, by a Behavior Breakpoint
      /** The By behavior. */
   /// </summary>
      ByBehavior; // behavior breakpoint requiring user action
}

