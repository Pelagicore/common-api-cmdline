package org.genivi.commonapi.cmdline.main;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.franca.core.dsl.FrancaIDLVersion;

/**
 * Runner for any standalone generator/transformation from Franca files.
 * 
 * @author Klaus Birken (itemis)
 * @author Jacques Guillou (pelagicore)
 */
public class Main {

	// prepare class for logging....
	private static final Logger logger = Logger.getLogger(Main.class);

	private static final String TOOL_VERSION = "0.3.0";
	private static final String FIDL_VERSION = FrancaIDLVersion.getMajor()
			+ "." + FrancaIDLVersion.getMinor();

	private static final String HELP = "h";
	private static final String FIDLFILE = "f";
	private static final String OUTDIR = "o";
	private static final String GENERATORS_PATH = "g";

	private static final String VERSIONSTR = "FrancaStandaloneGen "
			+ TOOL_VERSION + " (Franca IDL version " + FIDL_VERSION + ").";

	private static final String HELPSTR = "commonAPICodeGen CodeGeneratorIDs [OPTIONS]\nCodeGeneratorID can be one of the following : \n\t- api\n\t- someip\n\t- qt\n\t- dbus";
	private static final String EXAMPLE_HELPSTR = "\ncommonAPICodeGen -f MyDeploymentFile-dbus.fdepl -o /tmp/gen core dbus [OPTIONS]\nCodeGeneratorID can be one of the following : \n\t- api\n\t- someip\n\t- api\n\t- qt\n\t- dbus";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Options options = getOptions();

		// create the parser
		CommandLineParser parser = new GnuParser();
		CommandLine line = null;
		try {
			line = parser.parse(options, args);
		} catch (final ParseException exp) {
			logger.error(exp.getMessage());

			printHelp(options);
			System.exit(-1);
		}

		if (line.hasOption(HELP) || checkCommandLineValues(line) == false) {
			printHelp(options);
			System.exit(-1);
		}

		String generatorsPath = line.getOptionValue(GENERATORS_PATH);
		if (generatorsPath.equals(null))
			generatorsPath = ".";

		ArrayList<URL> urls = new ArrayList<URL>();
		for (File jarFile : new File(generatorsPath).listFiles()) {
			if (jarFile.getName().endsWith(".jar")) {
				urls.add(jarFile.toURI().toURL());
				logger.trace("Found generator JAR : " + jarFile);
			}
		}

		URL[] urlArray = new URL[urls.size()];

		new Main(urls.toArray(urlArray), args);

		logger.info("FrancaStandaloneGen done.");
		// System.exit(0);
	}

	public Main(URL[] urls, String[] args) {
		URLClassLoader classLoader = new URLClassLoader(urls, this.getClass()
				.getClassLoader());

		try {
			Class c = classLoader.loadClass(StandaloneGen.class.getName());
			StandaloneGen generator = (StandaloneGen) c.newInstance();
			generator.main(args);
//			generator.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(HELPSTR, options);
		formatter.printHelp(EXAMPLE_HELPSTR, options);
	}

	StandaloneGen generator;

	@SuppressWarnings("static-access")
	private static Options getOptions() {
		// String[] set = LogFactory.getLog(getClass()).
		final Options options = new Options();

		// optional
		// Option optVerbose = OptionBuilder.withArgName("verbose")
		// .withDescription("Print Out Verbose Information").hasArg(false)
		// .isRequired(false).create(VERBOSE);
		// options.addOption(optVerbose);
		Option optHelp = OptionBuilder.withArgName("help")
				.withDescription("Print Usage Information").hasArg(false)
				.isRequired(false).create(HELP);
		options.addOption(optHelp);

		// required
		Option optInputFidl = OptionBuilder
				.withArgName("Franca deployment file")
				.withDescription(
						"Input file in Franca deployment (fdepl) format.")
				.hasArg().isRequired().withValueSeparator(' ').create(FIDLFILE);
		// optInputFidl.setType(File.class);
		options.addOption(optInputFidl);

		Option optOutputDir = OptionBuilder
				.withArgName("output directory")
				.withDescription(
						"Directory where the generated files will be stored")
				.hasArg().isRequired().withValueSeparator(' ').create(OUTDIR);
		options.addOption(optOutputDir);

		Option optGeneratorsPath = OptionBuilder
				.withArgName("generators path")
				.withDescription(
						"Directory containing the JAR file for the various generators")
				.hasArg().isRequired().withValueSeparator(' ')
				.create(GENERATORS_PATH);
		options.addOption(optGeneratorsPath);

		return options;
	}

	private static boolean checkCommandLineValues(CommandLine line) {
		if (line.hasOption(FIDLFILE)) {
			String fidlFile = line.getOptionValue(FIDLFILE);
			File fidl = new File(fidlFile);
			if (fidl.exists()) {
				return true;
			} else {
				logger.error("Cannot open Franca IDL file '" + fidlFile + "'.");
			}
		}
		return false;
	}

}
