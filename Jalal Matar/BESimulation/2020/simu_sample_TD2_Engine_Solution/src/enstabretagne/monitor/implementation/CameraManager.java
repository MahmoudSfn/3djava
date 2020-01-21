package enstabretagne.monitor.implementation;

import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class CameraManager {

	public CameraManager() {
	}

	/**
	 * Camera to render the scene
	 */
	final PerspectiveCamera camera = new PerspectiveCamera(true);

	public PerspectiveCamera getCamera() {
		return camera;
	}

	/**
	 * Group to perform transformation on the camera
	 */
	final Group cameraGroup = new Group();

	public Group getCameraGroup() {
		return cameraGroup;
	}

	/**
	 * Value of the near clip of the camera
	 */
	private static final double CAMERA_NEAR_CLIP = 0.1;
	/**
	 * Value of the far clip of the camera
	 */
	private static final double CAMERA_FAR_CLIP = 10000.0;

//    final DrawnGroup drawnGroup = new DrawnGroup();

	private Scale sc;
	/**
	 * Initialize the camera and perform the axis rotation of the world group
	 */
	Point3D pointVue;
	Point3D dir;
	Point3D axyz;

	Rotate ry;
	Rotate rz;
	Translate t;

	protected void buildCamera(Group root, Point3D cameraPos, Point3D directionOfView) {
		root.getChildren().add(cameraGroup);

		cameraGroup.getChildren().add(camera);

		camera.setFarClip(CAMERA_FAR_CLIP);
		camera.setNearClip(CAMERA_NEAR_CLIP);
		t = new Translate();
		
		ry = new Rotate();
		{
			ry.setAxis(Rotate.Y_AXIS);
		}
		rz = new Rotate();
		{
			rz.setAxis(Rotate.Z_AXIS);
		}

		//fondamental ci-dessous: réorienter la caméra qui par défaut est orientée vers le haut selon l'axe z et l'axe des x et y ne sont pas non plus bien orientés 
		ry.setAngle(90);
		rz.setAngle(-90);

		facteurVitesse = 3;

		moveAbsoluteByDirection(cameraPos, directionOfView);

	}

	void moveRelative(double dx, double dy, double dz, double dax, double day, double daz) {
		axyz = axyz.add(new Point3D(dax, day, daz));

		Point3D newPos = new Point3D(pointVue.getX() + dx, pointVue.getY() + dy, pointVue.getZ() + dz);

		moveAbsoluteByRotation(newPos, axyz);

	}

	void moveAbsoluteByDirection(Point3D cameraPos, Point3D directionOfView) {
		Point3D axyz = XYZRotator2.computeRotationXYZ(directionOfView);
		moveAbsoluteByRotation(cameraPos, axyz);
	}

	void moveAbsoluteByRotation(Point3D cameraPos, Point3D angleOfView) {

		// mise à jour de l'état de positionnement camera
		axyz = angleOfView;
		pointVue = cameraPos;
		dir = XYZRotator2.getDirectionFromAngle(axyz);

		Affine a = XYZRotator2.getTransformByAngle(angleOfView);
		moveAbsoluteByTranslation(cameraPos);

		cameraGroup.getTransforms().setAll(t, a, ry, rz);

	}
	
	void moveAbsoluteByTranslation(Point3D cameraPos) {
		t.setX(cameraPos.getX());
		t.setY(cameraPos.getY());
		t.setZ(cameraPos.getZ());		
	}

	public EventHandler<KeyEvent> getKeyBoardHandler() {
		return new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				handleKeyboard(event);
			}

		};
	}

	public EventHandler<MouseEvent> getMousePressedHandler() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				handleMousePressed(event);
			}

		};
	}

	protected void handleMousePressed(MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			mousePosX = event.getSceneX();
			mousePosY = event.getSceneY();
		}
	}

	public EventHandler<MouseEvent> getMouseDraggedHandler() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				handleMouseDragged(event);
			}

		};
	}

	/**
	 * Old position of the mouse along x-axis of the screen
	 */
	private double mouseOldX;
	/**
	 * Old position of the mouse along y-axis of the screen
	 */
	private double mouseOldY;
	/**
	 * Current position of the mouse along x-axis of the screen
	 */
	private double mousePosX;
	/**
	 * Current position of the mous along y-axis of the screen
	 */
	private double mousePosY;
	/**
	 * Delta of mouse position along x-axis
	 */
	private double mouseDeltaX;
	/**
	 * Delta of mouse position along y-axis
	 */
	private double mouseDeltaY;

	public void handleMouseDragged(MouseEvent mEvent) {

		mouseOldX = mousePosX;
		mouseOldY = mousePosY;

		mousePosX = mEvent.getSceneX();
		mousePosY = mEvent.getSceneY();

		double sensibiliteSouris = 5;

		mouseDeltaX = (mousePosX - mouseOldX) / sensibiliteSouris;
		mouseDeltaY = (mousePosY - mouseOldY) / sensibiliteSouris;

		if (mEvent.isPrimaryButtonDown()) {
			moveRelative(0, 0, 0, 0, mouseDeltaY, -mouseDeltaX);
		}
	}

	double facteurVitesse;

	public void avancerHorizontal(boolean devant) {
		double delta;
		if (devant)
			delta = facteurVitesse;
		else
			delta = -facteurVitesse;

		Point3D avantDirHor = new Point3D(dir.getX(), dir.getY(), 0);
		avantDirHor = avantDirHor.normalize().multiply(delta);

		moveRelative(avantDirHor.getX(), avantDirHor.getY(), 0, 0, 0, 0);
	}

	public void monter(boolean devant) {
		double delta;
		if (devant)
			delta = facteurVitesse;
		else
			delta = -facteurVitesse;

		Point3D avantVert = new Point3D(0, 0, 1);
		avantVert = avantVert.normalize().multiply(delta);

		moveRelative(0, 0, avantVert.getZ(), 0, 0, 0);
	}

	public void lateral(boolean gauche) {
		double delta;
		Affine a = XYZRotator2.getTransformByAngle(axyz);
		Point3D lateralDir = a.transform(Rotate.Y_AXIS);
		if (gauche)
			delta = facteurVitesse;
		else
			delta = -facteurVitesse;

		Point3D lateralDirHor = new Point3D(lateralDir.getX(), lateralDir.getY(), 0);
		lateralDirHor = lateralDirHor.normalize().multiply(delta);

		moveRelative(lateralDirHor.getX(), lateralDirHor.getY(), 0, 0, 0, 0);

	}

	public void avancer(boolean devant) {
		double delta;
		if (devant)
			delta = facteurVitesse;
		else
			delta = -facteurVitesse;

		Point3D avantDir = new Point3D(dir.getX(), dir.getY(), dir.getZ());
		avantDir = avantDir.normalize().multiply(delta);

		moveRelative(avantDir.getX(), avantDir.getY(), avantDir.getZ(), 0, 0, 0);
	}

	@SuppressWarnings("incomplete-switch")
	public void handleKeyboard(KeyEvent event) {

		switch (event.getCode()) {
		case Z:
			avancerHorizontal(true);
			break;
		case S:
			avancerHorizontal(false);
			break;
		case Q:
			lateral(true);
			break;
		case D:
			lateral(false);
			break;
		case R:
			avancer(true);
			break;
		case F:
			avancer(false);
			break;
		case T:
			monter(true);
			break;
		case G:
			monter(false);
			break;
		}
	}

	public void zoomOn(double zoomFactor) {
		sc.setX(zoomFactor);
		sc.setY(zoomFactor);
		sc.setZ(zoomFactor);
	}

}
