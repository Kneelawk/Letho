package com.brynwyl.letho.ui;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import com.brynwyl.letho.opengl.GLControl;
import com.brynwyl.letho.util.ImageInfo;

import de.matthiasmann.twl.BoxLayout;
import de.matthiasmann.twl.BoxLayout.Direction;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.FPSCounter;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ListBox;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

public class UIControl {
	/* ***GUI MODES*** */
	public static final int GUI_ID_GAME = 0;
	public static final int GUI_ID_MAIN_MENU = 1;
	public static final int GUI_ID_WORLD_SELECTION = 2;
	public static final int GUI_ID_PROGRESS_SELECTION = 3;
	public static final int GUI_ID_SETTINGS = 4;

	/* ***THEMES*** */
	public static final String THEME_PANEL = "/widget";
	public static final String THEME_TITLE = "/title-label";
	public static final String THEME_FPS_COUNTER = "/fpscounter";
	public static final String THEME_BUTTON = "/button";
	public static final String THEME_LABEL = "/label";
	public static final String THEME_WORLD_LIST_BOX = "/world-listbox";

	/* ***TITLES*** */
	public static final String TITLE = "Letho";
	public static final String LOAD_WORLD_TITLE = "Load a world:";
	public static final String SETTINGS_TITLE = "Settings";

	/* ***UI OBJECTS*** */
	public static final String LOAD_WORLD_MENU_BUTTON = "Load world...";
	public static final String SETTINGS_MENU_BUTTON = "Settings...";
	public static final String QUIT_MENU_BUTTON = "Quit";
	public static final String CANCEL_BUTTON = "Cancel";
	public static final String PLAY_WORLD_BUTTON = "Play world...";
	public static final String IMPORT_WORLD_BUTTON = "Import world...";
	public static final String EXPORT_WORLD_BUTTON = "Export world...";
	public static final String REFRESH_WORLDS_BUTTON = "Refresh world list";
	public static final String REMOVE_WORLD_BUTTON = "Remove world...";

	public static Logger log;
	public static boolean isGuiOpen = true;
	public static int oldGui = 1;
	public static int currentGui = 1;
	public static float bgPos = 0f;

	public static Widget root;
	public static GUI gui;
	public static ThemeManager theme;
	public static ImageInfo menubg;
	public static FPSCounter fpsCounter;
	public static BoxLayout mainMenu;
	public static BoxLayout worldSelect;
	public static BoxLayout settings;

	public static void init() {
		log = LogManager.getLogger("UIControl");
		log.info("Init UI Control");

		try {
			root = new Widget() {
				@Override
				protected void layout() {
					if (getNumChildren() > 1) {
						centerWidget(this, getChild(1));
					}
				}
			};

			LWJGLRenderer renderer = new LWJGLRenderer();

			buildGui();

			gui = new GUI(root, renderer);

			mainMenu.requestKeyboardFocus();

			theme = ThemeManager.createThemeManager(
					UIControl.class.getResource("/gui/theme/Letho-ui.xml"),
					renderer);
			gui.applyTheme(theme);
		} catch (Throwable t) {
			log.error("Error during setup!", t);
		}
	}

	private static void buildGui() throws IOException {
		// background
		menubg = ImageInfo
				.loadTextureResource("/textures/backgrounds/castle_background.png");
		menubg.registerGlTex();

		// root size
		root.setSize(GLControl.DISPLAY_WIDTH, GLControl.DISPLAY_HEIGHT);

		// FPS Counter
		fpsCounter = new FPSCounter();
		fpsCounter.setText("030.00");
		fpsCounter.setTheme(THEME_FPS_COUNTER);
		root.add(fpsCounter);
		fpsCounter.setPosition(root.getX() + fpsCounter.getWidth(), root.getY()
				+ fpsCounter.getHeight() + 10);

		buildMainMenu();
		buildWorldSelect();
		buildSettings();
	}

	private static void buildMainMenu() throws IOException {
		mainMenu = new BoxLayout(Direction.VERTICAL);
		mainMenu.setTheme(THEME_PANEL);

		root.add(mainMenu);

		Label title = new Label(TITLE);
		title.setTheme(THEME_TITLE);
		mainMenu.add(title);

		Button selectWorld = new Button(LOAD_WORLD_MENU_BUTTON);
		selectWorld.setTheme(THEME_BUTTON);
		mainMenu.add(selectWorld);
		selectWorld.addCallback(new Runnable() {
			@Override
			public void run() {
				// TODO refresh worlds
				currentGui = GUI_ID_WORLD_SELECTION;
			}
		});

		Button settings = new Button(SETTINGS_MENU_BUTTON);
		settings.setTheme(THEME_BUTTON);
		mainMenu.add(settings);
		settings.addCallback(new Runnable() {
			@Override
			public void run() {
				currentGui = GUI_ID_SETTINGS;
			}
		});

		Button quit = new Button(QUIT_MENU_BUTTON);
		quit.setTheme(THEME_BUTTON);
		mainMenu.add(quit);
		quit.addCallback(new Runnable() {
			@Override
			public void run() {
				GLControl.requestStop();
			}
		});
		centerWidget(root, mainMenu);
	}

	private static void buildWorldSelect() {
		worldSelect = new BoxLayout(Direction.VERTICAL);
		worldSelect.setTheme(THEME_PANEL);

		Label title = new Label(LOAD_WORLD_TITLE);
		title.setTheme(THEME_LABEL);
		worldSelect.add(title);

		ListBox<String> worldList = new ListBox<String>();
		worldList.setTheme(THEME_WORLD_LIST_BOX);
		worldSelect.add(worldList);

		BoxLayout buttonBox = new BoxLayout(Direction.HORIZONTAL);
		buttonBox.setTheme(THEME_PANEL);
		worldSelect.add(buttonBox);

		Button cancel = new Button(CANCEL_BUTTON);
		cancel.setTheme(THEME_BUTTON);
		buttonBox.add(cancel);
		cancel.addCallback(new Runnable() {
			@Override
			public void run() {
				currentGui = GUI_ID_MAIN_MENU;
			}
		});

		Button playWorld = new Button(PLAY_WORLD_BUTTON);
		playWorld.setTheme(THEME_BUTTON);
		buttonBox.add(playWorld);

		Button importWorld = new Button(IMPORT_WORLD_BUTTON);
		importWorld.setTheme(THEME_BUTTON);
		buttonBox.add(importWorld);
		importWorld.addCallback(new Runnable() {
			@Override
			public void run() {
				// TODO copy world into worlds dir
				// TODO refresh worlds
			}
		});

		buttonBox = new BoxLayout(Direction.HORIZONTAL);
		buttonBox.setTheme(THEME_PANEL);
		worldSelect.add(buttonBox);

		Button exportWorld = new Button(EXPORT_WORLD_BUTTON);
		exportWorld.setTheme(THEME_BUTTON);
		buttonBox.add(exportWorld);

		Button refreshWorlds = new Button(REFRESH_WORLDS_BUTTON);
		refreshWorlds.setTheme(THEME_BUTTON);
		buttonBox.add(refreshWorlds);
		refreshWorlds.addCallback(new Runnable() {
			@Override
			public void run() {
				// TODO refresh worlds
			}
		});

		Button removeWorld = new Button(REMOVE_WORLD_BUTTON);
		removeWorld.setTheme(THEME_BUTTON);
		buttonBox.add(removeWorld);
		removeWorld.addCallback(new Runnable() {
			@Override
			public void run() {
				// TODO show deletion confirmation dialog
				// TODO refresh worlds
			}
		});
	}

	private static void buildSettings() {
		settings = new BoxLayout(Direction.VERTICAL);
		settings.setTheme(THEME_PANEL);

		Label settingsTitle = new Label(SETTINGS_TITLE);
		settingsTitle.setTheme(THEME_LABEL);
		settings.add(settingsTitle);

		Button cancel = new Button(CANCEL_BUTTON);
		cancel.setTheme(THEME_BUTTON);
		settings.add(cancel);
		cancel.addCallback(new Runnable() {
			@Override
			public void run() {
				currentGui = GUI_ID_MAIN_MENU;
			}
		});
	}

	private static void centerWidget(Widget parent, Widget child) {
		child.adjustSize();
		child.setPosition(
				parent.getInnerX()
						+ (parent.getInnerWidth() - child.getWidth()) / 2,
				parent.getInnerY()
						+ (parent.getInnerHeight() - child.getHeight()) / 2);
	}

	private static void removeChildren() {
		root.removeAllChildren();
		root.add(fpsCounter);
	}

	public static void glLoop(short delta) {
		if (currentGui != GUI_ID_GAME) {
			GL11.glColor4f(1f, 1f, 1f, 1f);
			menubg.bind();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex2f(bgPos, 0f);
			GL11.glTexCoord2f(2f, 0f);
			GL11.glVertex2f(bgPos + GLControl.DISPLAY_HEIGHT * 2f
					* menubg.width / menubg.height, 0f);
			GL11.glTexCoord2f(2f, 1f);
			GL11.glVertex2f(bgPos + GLControl.DISPLAY_HEIGHT * 2f
					* menubg.width / menubg.height, GLControl.DISPLAY_HEIGHT);
			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex2f(bgPos, GLControl.DISPLAY_HEIGHT);
			GL11.glEnd();
			bgPos -= 0.05f * delta;
			if (bgPos <= -(GLControl.DISPLAY_HEIGHT * menubg.width / menubg.height))
				bgPos = 0f;
		}

		if (currentGui != oldGui) {
			switch (currentGui) {
			case GUI_ID_GAME:
				removeChildren();
				break;
			case GUI_ID_MAIN_MENU:
				removeChildren();
				root.add(mainMenu);
				mainMenu.requestKeyboardFocus();
				break;
			case GUI_ID_WORLD_SELECTION:
				removeChildren();
				root.add(worldSelect);
				worldSelect.requestKeyboardFocus();
				break;
			case GUI_ID_SETTINGS:
				removeChildren();
				root.add(settings);
				settings.requestKeyboardFocus();
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
