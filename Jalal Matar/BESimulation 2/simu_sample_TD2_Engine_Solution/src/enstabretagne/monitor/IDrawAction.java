/**
* Classe IDrawAction.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.monitor;

import javafx.scene.Group;

public interface IDrawAction {
	ObjTo3DMappingSettings getGeneral3DSettings();
	void init(Group world, Object obj);
	void update();
}

