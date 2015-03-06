package com.brynwyl.letho.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.matthiasmann.twl.BoxLayout;
import de.matthiasmann.twl.FPSCounter;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

public class UIControl {
	public static final int GUI_ID_GAME = 0;
	public static final int GUI_ID_MAIN_MENU = 1;
	public static final int GUI_ID_WORLD_SELECTION = 2;
	public static final int GUI_ID_PROGRESS_SELECTION = 3;

	public static Logger log;
	public static boolean isGuiOpen = true;
	public static int oldGui = -1;
	public static int currentGui = 1;

	public static Widget root;
	public static GUI gui;
	public static ThemeManager theme;
	public static BoxLayout mainMenu;

	public static void init() {
		log = LogManager.getLogger("UIControl");
		log.info("Init UI Control");

		try {
			root = new Widget() {
				@Override
				protected void layout() {

				}
			};

			LWJGLRenderer renderer = new LWJGLRenderer();

			buildGui();

			gui = new GUI(root, renderer);

			theme = ThemeManager.createThemeManager(
					UIControl.class.getResource("/gui/theme/Letho-ui.xml"),
					renderer);
			gui.applyTheme(theme);
		} catch (Throwable t) {
			log.error("Error during setup!", t);
		}
	}

	private static void buildGui() {
		// FPS Counter
		FPSCounter fps = new FPSCounter();
		fps.setText("030.00");
		fps.setTheme("/fpscounter");
		root.add(fps);
		fps.setPosition(root.getRight() + fps.getWidth(), root.getBottom()
				+ fps.getHeight() + 10);

		buildMainMenu();
		root.add(mainMenu);
	}

	private static void buildMainMenu() {
		mainMenu = new BoxLayout();
		mainMenu.setTheme("/widget");
	}

	private static void removeChildren() {
		while (root.getNumChildren() > 1) {
			root.removeChild(1);
		}
	}

	public static void glLoop() {
		if (currentGui != oldGui) {
			switch (currentGui) {
			case GUI_ID_GAME:
				removeChildren();
				break;
			case GUI_ID_MAIN_MENU:
				removeChildren();
				root.add(mainMenu);
				break;
			}

			oldGui = currentGui;
		}

		gui.update();
	}

	public static void shutdown() {
		gui.destroy();
		theme.destroy();
	}
}
