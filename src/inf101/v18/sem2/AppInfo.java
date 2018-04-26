package inf101.v18.sem2;

import java.util.Arrays;
import java.util.List;

public class AppInfo {
	/**
	 * Your application name.
	 */
	public static final String APP_NAME = "Connect4";
	/**
	 * The main class, for starting the application
	 */
	public static final Class<?> APP_MAIN_CLASS = inf101.v18.sem2.Main.class; // e.g., inf101.v18.sem2.Main.class
	/**
	 * Your name.
	 */
	public static final String APP_DEVELOPER = "Joakim Algrøy (jal043)";
	/**
	 * A short description.
	 */
	public static final String APP_DESCRIPTION = "Semesteroppgave2";
	/**
	 * List of extra credits (e.g. for media sources)
	 */
	public static final List<String> APP_EXTRA_CREDITS = Arrays.asList(
			"HAL 9000 red eye graphic by Cryteria @ Wikimedia (CC BY 3.0)",
			"Earth, Moon and Mars graphic by hameed (hameed.deviantart.com) (CC Attribution)",
			"Jupiter image by NASA (svs.gsfc.nasa.gov, public domain)",
			"Milky Way image by FelixMittermeier @ pixabay (CC0)",
			"Techniques for falling animation borrowed from inf101.v18.lab1"
			);
	/**
	 * Help text. Could be used for an in-game help page, perhaps.
	 * <p>
	 * Use <code>\n</code> for new lines, <code>\f<code> between pages (if multi-page).
	 */
	public static final String APP_HELP = "Help:\n";
}
