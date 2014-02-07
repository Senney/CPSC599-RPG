package cpsc599.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
	public static final int 	NONE = -1,
								DEBUG = 0,
								INFO = 1,
								WARNING = 2,
								ERROR = 3,
								FATAL = 4;
	private static final String levelNames[] = {"DEBUG", "INFO", "WARNING", "ERROR", "FATAL"};	
	private static final DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SS");
	
	/** Getters and Setters for output stream. **/
	private static OutputStream ostream;
	public static void setOutputStream(OutputStream out) { Logger.ostream = out; }
	public static OutputStream getOutputStream() { return Logger.ostream; }
	
	/** Getters and setters for the Log Level **/
	private static int logLevel = Logger.INFO; // Default log level is INFO.
	public static void setLogLevel(int level) { Logger.logLevel = level; }	
	public static int getLogLevel() { return Logger.logLevel; }

    private static String getLevelDescriptor(int level) {
        if (level < 0 || level > levelNames.length)
            return "INVALID";
        return levelNames[level];
    }

	public static void log(int level, String message) {
		if (level < logLevel) return;
		
		final String fmt = "%s\t- [%s]\t- %s\n";
		
		String dateString = df.format(Calendar.getInstance().getTime());
		String outstring = String.format(fmt, dateString, getLevelDescriptor(level), message);
		
		try {
			ostream.write(outstring.getBytes());
		} catch (IOException ex) {
			// OH GOD THE LOGGER DIED!?!? What do we do!
		}
	}
	
	public static void debug(String message) { Logger.log(DEBUG, message); }
	public static void info(String message) { Logger.log(INFO, message); }
	public static void warn(String message) { Logger.log(WARNING, message); }
	public static void error(String message) { Logger.log(ERROR, message); }
	public static void fatal(String message) { Logger.log(FATAL, message); }
}
