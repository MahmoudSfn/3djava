package underwaterExplorer.BasicMovement.SimEntity.Bouee.Representation3D;

import enstabretagne.monitor.interfaces.IMovable;
import javafx.scene.paint.Color;

public interface IBoueeRepresentation3D extends IMovable{
	Color getColor();
	double getSize();
	int getType();
	boolean detected();
	void setDetected();


}
