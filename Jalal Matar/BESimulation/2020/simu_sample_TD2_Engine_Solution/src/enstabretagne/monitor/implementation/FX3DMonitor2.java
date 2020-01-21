package enstabretagne.monitor.implementation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import enstabretagne.base.logger.Logger;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.monitor.IMonitor;
import enstabretagne.monitor.Monitor3DMessages;
import enstabretagne.monitor.Monitor3DSettings;
import enstabretagne.monitor.ObjTo3DMappingSettings;
import enstabretagne.monitor.interfaces.IMovable;
import enstabretagne.monitor.Contrat3D;
import enstabretagne.monitor.IDrawAction;
import enstabretagne.monitor.IExperiencePlan;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.components.data.SimScenarioInit;
import enstabretagne.simulation.components.implementation.ScenariiSettings;
import enstabretagne.simulation.components.implementation.SimScenario;
import enstabretagne.simulation.components.messages.ScenariiMessages;
import enstabretagne.simulation.core.IScenario;
import enstabretagne.simulation.core.IScenarioInstance;
import enstabretagne.simulation.core.ISimEngine;
import enstabretagne.simulation.core.ISimObject;
import enstabretagne.simulation.core.implementation.SimEngine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class FX3DMonitor2 extends Application implements IMonitor {

	// mapping entre un type d'entité et le constructeur sans argument d'une
	// représentation 3D
	protected HashMap<Class<?>, Constructor<?>> drawActionsMapping;
	protected HashMap<Class<?>, ObjTo3DMappingSettings> generalRepresentation3DSettings;
	protected HashMap<ISimObject, IDrawAction> objectRepresentation;
	protected List<EventHandler<KeyEvent>> keyHandlers;
	protected List<EventHandler<MouseEvent>> mousePressedHandlers;
	protected List<EventHandler<MouseEvent>> mouseDraggedHandlers;

	protected Stage FX3DMonitor2Stage;
	protected boolean requestStop;

	private boolean goon;
	private LogicalDuration updateDelay;
	private long timeSpeedFactor;
	private LogicalDuration RT_updateDelay;
	private int fps;

	/** The xp plan. */
	private ExperiencePlan xpPlan;

	/**
	 * Gets the xp plan.
	 *
	 * @return the xp plan
	 */
	public ExperiencePlan getXpPlan() {
		return xpPlan;
	}

	/** The current scenario. */
	private SimScenario currentScenario;

	/** The scenario index. */
	private int scenarioIndex;

	/** The current seed. */
	private long currentSeed;

	/** The current replique number. */
	protected long currentRepliqueNumber;

	/**
	 * Gets the current replique number.
	 *
	 * @return the current replique number
	 */
	public long getCurrentRepliqueNumber() {
		return currentRepliqueNumber;
	}

	/**
	 * Gets the current seed.
	 *
	 * @return the current seed
	 */
	public long getCurrentSeed() {
		return currentSeed;
	}

	/**
	 * Sets the current seed.
	 *
	 * @param currentSeed the new current seed
	 */
	protected void setCurrentSeed(long currentSeed) {
		this.currentSeed = currentSeed;
	}

	/** The engine. */
	ISimEngine engine;

	/**
	 * Gets the current scenario.
	 *
	 * @return the current scenario
	 */
	public SimScenario getCurrentScenario() {
		return currentScenario;
	}

	/**
	 * Gets the engine.
	 *
	 * @return the engine
	 */
	public ISimEngine getEngine() {
		return engine;
	}
	Point3D defaultDir;

	/**
	 * Instantiates a new abstract monitor.
	 */
	public FX3DMonitor2() {
		super();
		keyHandlers = new ArrayList<EventHandler<KeyEvent>>();
		mousePressedHandlers = new ArrayList<EventHandler<MouseEvent>>();
		mouseDraggedHandlers = new ArrayList<EventHandler<MouseEvent>>();

		camManager = new CameraManager();
		keyHandlers.add(camManager.getKeyBoardHandler());
		mouseDraggedHandlers.add(camManager.getMouseDraggedHandler());
		mousePressedHandlers.add(camManager.getMousePressedHandler());

		drawActionsMapping = new HashMap<>();
		objectRepresentation = new HashMap<>();
		generalRepresentation3DSettings = new HashMap<>();

		if (Monitor3DSettings.settings.frameparseconde == 0)
			Logger.Fatal(null, "Constructeur FX3DMonitor2", "Monitor3DSettings.settings.frameparseconde est 0");

		fps = (int) Monitor3DSettings.settings.frameparseconde;

		RT_updateDelay = LogicalDuration.ofSeconds(1.0 / fps);
		timeSpeedFactor = 1;
		updateDelay = RT_updateDelay.mult(timeSpeedFactor);

		requestStop = false;
		defaultDir = new Point3D(-1,-1,-1).multiply(100);
		
		engine = new SimEngine();
		Logger.setDateProvider(engine);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see enstabretagne.monitor.IMonitor#loadExperiencePlan(enstabretagne.monitor.
	 * IExperiencePlan)
	 */
	@Override
	public void loadExperiencePlan(IExperiencePlan experiencePlan) {
		xpPlan = (ExperiencePlan) experiencePlan;
		scenarioIndex = -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see enstabretagne.monitor.IMonitor#selectNextScenario()
	 */
	@Override
	public IScenario selectNextScenario() {
		if (xpPlan.getListeScenarios().size() > 0 && scenarioIndex + 1 < xpPlan.getListeScenarios().size()) {
			scenarioIndex++;
			return selectScenario();
		}
		return null;
	}

	/**
	 * Select scenario.
	 *
	 * @return the i scenario
	 */
	private IScenario selectScenario() {
		currentScenario = (SimScenario) xpPlan.getListeScenarios().get(scenarioIndex);
		return currentScenario;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * enstabretagne.monitor.IMonitor#selectScenario(enstabretagne.simulation.core.
	 * IScenario)
	 */
	@Override
	public void selectScenario(IScenario sc) {
		int index = xpPlan.getListeScenarios().indexOf(sc);
		if (index >= 0) {
			scenarioIndex = index;
			selectScenario();
		}
	}

	public class UnTest {
		String ss;
		int g;

		public UnTest(String s, int g) {
			ss = s;
			this.g = g;
		}

		@Override
		public String toString() {
			return ss;
		}
	}

	ObservableList<ISimObject> options;
	ComboBox<ISimObject> comboBox;
	TextField isoLogicalTime;
	TextField timeSpeedFactorTF;
	IMovable toFollow;
	CheckBox cb;

	private void stopAll() {
		simuLoop.stop();
		goon = false;
		engine.deactivateSimulation();

		engine.terminate(true);

		ClearGUI();

	}
	public Parent createUI() {
		HBox buttonBox = new HBox();

		Button b;
		buttonBox.getChildren().add(b = new Button("Start"));
		b.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				goon = true;
				simuLoop.play();
			}
		});
		;

		buttonBox.getChildren().add(b = new Button("Pause"));
		b.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				simuLoop.pause();
				goon = false;
			}
		});
		;

		buttonBox.getChildren().add(b = new Button("Stop"));
		b.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				stopAll();
				
				Logger.CloseAndReinit();

				run(false);
			}
		});
		;

		buttonBox.getChildren().add(b = new Button("Speed up x5"));
		b.setOnAction(new EventHandler<ActionEvent>(	) {
			
			@Override
			public void handle(ActionEvent event) {
				timeSpeedFactor *= 5;
				updateDelay = RT_updateDelay.mult(timeSpeedFactor);
				timeSpeedFactorTF.setText(""+timeSpeedFactor);
				
			}
		});
		buttonBox.getChildren().add(b = new Button("Slow down /5"));
		b.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				timeSpeedFactor /= 5;
				if(timeSpeedFactor==0) timeSpeedFactor = 1;
				updateDelay = RT_updateDelay.mult(timeSpeedFactor);
				timeSpeedFactorTF.setText(""+timeSpeedFactor);
				
			}
		});
		
		timeSpeedFactorTF = new TextField();
		timeSpeedFactorTF.setMaxWidth(40);
		timeSpeedFactorTF.setText(""+timeSpeedFactor);
		timeSpeedFactorTF.setDisable(true);
		buttonBox.getChildren().add(timeSpeedFactorTF);
		
		buttonBox.getChildren().add(b = new Button("AFAP To"));
		b.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (!LogicalDateTime.EstBienStructuree(isoLogicalTime.getText())) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Structure de la date");
					alert.setHeaderText("Mauvaise saisie:" + isoLogicalTime.getText());
					alert.setContentText("Le format attendu est '" + LogicalDateTime.wellFormedDateSample + "'");

					alert.showAndWait();
				} else {
					LogicalDateTime dateToReach = new LogicalDateTime(isoLogicalTime.getText());
					if (dateToReach.compareTo(engine.SimulationDate()) < 0) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Valeur la date");
						alert.setHeaderText("La date saisie est inférieure à la date en cours");
						alert.setContentText("Date saisie = '" + isoLogicalTime.getText() + "'\n"
								+ "Date minimale à saisir = '" + engine.SimulationDate() + "'");
						isoLogicalTime.setText(engine.SimulationDate().toString());
						alert.showAndWait();
					} else {

						simuLoop.pause();
						goon = true;
						LogicalDuration oldUpDateDelay = updateDelay;
						updateDelay = dateToReach.soustract(engine.SimulationDate());
						realTimeLoop(updateDelay);

						updateDelay = oldUpDateDelay;
						goon = false;

					}
				}
			}
		});
		;

		Label isoLogicalTimeLabel = new Label("ISO Time");
		isoLogicalTimeLabel = new Label("ISO Time");
		buttonBox.getChildren().add(isoLogicalTimeLabel);
		isoLogicalTime = new TextField();
		buttonBox.getChildren().add(isoLogicalTime);

		options = FXCollections.observableArrayList();

		comboBox = new ComboBox<ISimObject>(options);
		comboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent ev) {

				toFollow = (IMovable) comboBox.getValue();
				if (toFollow != null) {
					Point3D p = toFollow.getPosition();


					camManager.moveAbsoluteByDirection(p.subtract(defaultDir),defaultDir);
				}
			}
		});

		buttonBox.getChildren().add(comboBox);

		cb = new CheckBox("Suivre");

		cb.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (cb.isSelected())
					toFollow = (IMovable) comboBox.getValue();
				else
					toFollow = null;
			}
		});

		buttonBox.getChildren().add(cb);

		return buttonBox;
	}

	public void ClearGUI() {
		options.clear();
		entitiesWorld.getChildren().clear();
		objectRepresentation.clear();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FX3DMonitor2Stage = primaryStage;
		Logger.Information(this, "Start", "Ici");
		FX3DMonitor2Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				Logger.Detail(null, "simuLoop.onCloseRequest", "Fermeture de la boucle de simulation JFX");
				if (goon) {
					goon = false;

					arg0.consume();
				}

			}

		});

		root = new Group();
		double width = 1024;
		double height = 768;
		SubScene subScene = new SubScene(root, width, height, true, SceneAntialiasing.BALANCED);

		AmbientLight light = new AmbientLight();
		root.getChildren().add(light);

		Parent ui = createUI();
		StackPane combined = new StackPane(subScene, ui);
		scene = new Scene(combined, width, height, true);
		world = new Group();
		root.getChildren().add(world);

		entitiesWorld = new Group();
		world.getChildren().add(entitiesWorld);

		environmentWorld = new Group();
		world.getChildren().add(environmentWorld);

		subScene.setCamera(camManager.getCamera());
		Point3D initPos = new Point3D(30, 30, 30);
		camManager.buildCamera(root,initPos,initPos.multiply(-1));
		initHandlers(scene);

		FX3DMonitor2Stage.setScene(scene);
		FX3DMonitor2Stage.setTitle("Moniteur Graphique");
		FX3DMonitor2Stage.show();

		load3DConfiguration();
		loadExperiencePlanFromSettings();
		runPlanRT();
	}

	private void initHandlers(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				for (EventHandler<KeyEvent> eh : keyHandlers)
					eh.handle(event);

			}

		});

		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				for (EventHandler<MouseEvent> eh : mousePressedHandlers)
					eh.handle(event);
			}
		});
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				for (EventHandler<MouseEvent> eh : mouseDraggedHandlers)
					eh.handle(event);
			}
		});
	}

	CameraManager camManager;
	/**
	 * main group of the display
	 */
	Group root;
	Scene scene;
	/**
	 * Group containing the 3D shapes
	 */
	Group world;

	Group environmentWorld;
	Group entitiesWorld;

	/*
	 * (non-Javadoc)
	 * 
	 * @see enstabretagne.monitor.IMonitor#exit()
	 */
	@Override
	public void exit() {
	}

	/** The rate logical time over real time. */
	private double rateLogicalTimeOverRealTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see enstabretagne.monitor.IMonitor#getLogicalTimeSpeed()
	 */
	@Override
	public double getLogicalTimeSpeed() {
		return rateLogicalTimeOverRealTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see enstabretagne.monitor.IMonitor#setLogicalTimeSpeed(double)
	 */
	@Override
	public void setLogicalTimeSpeed(double rateLogicalTimeOverRealTime) {
		if (rateLogicalTimeOverRealTime == Double.MAX_VALUE) {
			this.rateLogicalTimeOverRealTime = rateLogicalTimeOverRealTime;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see enstabretagne.monitor.IMonitor#run(boolean)
	 */
	@Override
	public void run(boolean firststart) {
		if (getCurrentScenario() != null) {
			if (firststart)
				engine.AddSimObjectAddedListener(this::newObjListener);

			engine.init(getCurrentScenario(), new SimScenarioInit(getCurrentSeed()));

			engine.activateSimulation();
			simulateRealTime2();
		}

	}

	private Timeline simuLoop;

	public void simulateRealTime2() {
		simuLoop = new Timeline();
		simuLoop.setCycleCount(Timeline.INDEFINITE);

		double keyframeDuration = 1.0 / fps;
		KeyFrame kf = new KeyFrame(Duration.seconds(keyframeDuration), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				realTimeLoop(updateDelay);
			}
		});

		simuLoop.getKeyFrames().add(kf);

		goon = true;
		realTimeLoop(LogicalDuration.ZERO);
		goon = false;
	}

	public void realTimeLoop(LogicalDuration ld) {
		getCurrentScenario().InterruptIn(ld);
		if (goon) {
			engine.simulate();

			for (IDrawAction dr : objectRepresentation.values()) {
				dr.update();
			}

			isoLogicalTime.setText(engine.SimulationDate().toString());
			if (toFollow != null) {
				Point3D p = toFollow.getPosition();
//				camManager.moveAbsoluteByDirection(p.subtract(defaultDir),defaultDir);
				camManager.moveAbsoluteByTranslation(p.subtract(defaultDir));//,defaultDir);

			}

		} else {
			stopAll();
//			Logger.CloseAndReinit();
			Logger.Terminate();
			simuLoop.stop();
			FX3DMonitor2Stage.close();

		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	void newObjListener(ISimObject obj) {
		Logger.Detail(obj, "Monitor3D.newObjListener",
				obj.getName() + " configure son type pour une représentation 3D.");
		for (Class c : drawActionsMapping.keySet()) {
			if (c.isAssignableFrom(obj.getClass())) {
				Constructor cnst = drawActionsMapping.get(c);
				ObjTo3DMappingSettings setting3D = generalRepresentation3DSettings.get(c);
				try {
					IDrawAction dwA = (IDrawAction) cnst.newInstance(setting3D);
					objectRepresentation.put(obj, dwA);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		if (IEntity.class.isAssignableFrom(obj.getClass())) {
			IEntity sime = (IEntity) obj;
			sime.OnInitialized().add(this::newObjInitialized);
		}
	}

	void newObjInitialized(IEntity obj, SimInitParameters init) {
		IDrawAction act = objectRepresentation.get(obj);

		if (IMovable.class.isAssignableFrom(obj.getClass())) {
			System.out.println(obj.getName() + "==" + Monitor3DSettings.settings.entiteASuivre);
			if (obj.getName().equals(Monitor3DSettings.settings.entiteASuivre)) {
				comboBox.setValue(obj);
				cb.fire();
			}
			options.add(obj);
			options.sort(new Comparator<ISimObject>() {

				@Override
				public int compare(ISimObject arg0, ISimObject arg1) {
					return arg0.getName().compareTo(arg1.getName());
				}
			});

		}

		if (act != null) {
			Logger.Detail(obj, "Monitor3D.newObjInitialized", obj.getName() + " s'initialise en 3D.");
			ObjTo3DMappingSettings setting = act.getGeneral3DSettings();
			if (setting.level4Trasparency == 1) {
				objectRepresentation.get(obj).init(environmentWorld, obj);
			} else {
				objectRepresentation.get(obj).init(entitiesWorld, obj);

			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see enstabretagne.monitor.IMonitor#runPlan()
	 */
	public void runPlanRT() {

		if (selectNextScenario() != null) {
			setCurrentSeed(getXpPlan().getInitialSeed());
			Logger.Detail(null, "runPlan", "début exécution");
			run(true);
		}

	}

	@Override
	public void runPlan() {

		Logger.Detail(null, "runPlan", "essai d'exécution");
		while (selectNextScenario() != null) {
			for (currentRepliqueNumber = 0; currentRepliqueNumber < getXpPlan()
					.getNombreRepliques(); currentRepliqueNumber++) {
				setCurrentSeed(getXpPlan().getInitialSeed() + currentRepliqueNumber);// on prend le numéro de réplique
																						// comme germe initial de la
				// réplique
				Logger.Detail(null, "runPlan", "début exécution");
				run(true);
				Logger.Detail(null, "runPlan", "fin exécution");
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see enstabretagne.monitor.IMonitor#pause()
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see enstabretagne.monitor.IMonitor#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	public void load3DConfiguration() {
		for (ObjTo3DMappingSettings s : Monitor3DSettings.settings.mappingClasse2Representation3D) {
			Class<?> entite = null;
			Class<?> repres3D = null;
			try {
				entite = Class.forName(s.classeObjetAMapper);
			} catch (ClassNotFoundException e) {
				Logger.Fatal(null, "load3DConfiguration", Monitor3DMessages.typeEntiteNonTrouve, s.classeObjetAMapper);
			}
			try {
				repres3D = Class.forName(s.Representation3DAMapper);
			} catch (ClassNotFoundException e) {
				Logger.Fatal(null, "load3DConfiguration", Monitor3DMessages.representation3DNonTrouve,
						s.Representation3DAMapper);
			}

			if (!IEntity.class.isAssignableFrom(entite))
				Logger.Fatal(null, "load3DConfiguration", Monitor3DMessages.typeEntitePasSimEntity,
						s.classeObjetAMapper);

			if (!IDrawAction.class.isAssignableFrom(repres3D))
				Logger.Fatal(null, "load3DConfiguration", Monitor3DMessages.type3DPasDrawAction,
						s.Representation3DAMapper);

			Constructor<?> c3D = null;
			try {
				c3D = repres3D.getConstructor(ObjTo3DMappingSettings.class);
			} catch (NoSuchMethodException e) {
				Logger.Fatal(null, "load3DConfiguration", Monitor3DMessages.type3DPasConstructeurAvec3DSettings,
						s.Representation3DAMapper);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Annotation a3D = repres3D.getAnnotation(Contrat3D.class);
			if (a3D == null)
				Logger.Fatal(null, "load3DConfiguration", Monitor3DMessages.PasContrat, s.Representation3DAMapper);
			else {
				Class<?> cl3D = ((Contrat3D) a3D).contrat();
				if (!cl3D.isAssignableFrom(entite))
					Logger.Fatal(null, "load3DConfiguration", Monitor3DMessages.Obj3DPasBonContrat3D, entite.getName(),
							c3D.getName());

			}

			drawActionsMapping.put(entite, c3D);
			generalRepresentation3DSettings.put(entite, s);
			Logger.Detail(null, "load3DConfiguration", "Association %s avec %s ", entite.getName(), c3D.getName());
		}
	}

	@Override
	public void loadExperiencePlanFromSettings() {
		List<IScenario> scens = new ArrayList<>();
		try {
			for (String s : ScenariiSettings.settings.scenarioInstanceClassNames) {
				Class<?> c = Class.forName(s);
				if (IScenarioInstance.class.isAssignableFrom(c)) {
					IScenarioInstance scenI = (IScenarioInstance) c.getConstructor().newInstance();
					IScenario scen = scenI.getScenarioInstance(ScenariiSettings.settings.germeInitial);
					Logger.Detail(null, "loadExperiencePlanFromSettings", "Scénario trouvé : " + scen.getName());
					scens.add(scen);
				} else {
					Logger.Fatal(null, "loadExperiencePlanFromSettings", ScenariiMessages.ScenariiNotAScenario);
				}
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExperiencePlan xp = new ExperiencePlan(ScenariiSettings.settings.nbRepliques,
				ScenariiSettings.settings.germeInitial, scens);
		loadExperiencePlan(xp);
	}

}
