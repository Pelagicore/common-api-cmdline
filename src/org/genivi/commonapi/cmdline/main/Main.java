package org.genivi.commonapi.cmdline.main;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

/**
 * That class simply loads
 * 
 * @author Jacques Guillou (pelagicore)
 */
public class Main {

	// prepare class for logging....
	private static final Logger logger = Logger.getLogger(Main.class);

	private static final String HELP = "h";
	private static final String GENERATORS_PATH = "g";

	/**
	 * @param args
	 */
	public void main(String[] args) throws Exception {
		Options options = StandaloneGen.getOptions();

		// create the parser
		CommandLineParser parser = new GnuParser();
		CommandLine line = null;
		try {
			line = parser.parse(options, args);
		} catch (final ParseException exp) {
			logger.error(exp.getMessage());

			StandaloneGen.printHelp(options);
			System.exit(-1);
		}

		if (line.hasOption(HELP) || StandaloneGen.checkCommandLineValues(line) == false) {
			StandaloneGen.printHelp(options);
			System.exit(-1);
		}

		String generatorsPath = line.getOptionValue(GENERATORS_PATH);
		if (generatorsPath.equals(null))
			generatorsPath = ".";

		ArrayList<URL> urls = new ArrayList<URL>();
		for (File jarFile : new File(generatorsPath).listFiles()) {
			if (jarFile.getName().endsWith(".jar")) {
				urls.add(jarFile.toURI().toURL());
				logger.debug("Found generator JAR : " + jarFile);
			}
		}

		URL[] urlArray = new URL[urls.size()];

		new Main(urls.toArray(urlArray), args);

		logger.info("FrancaStandaloneGen done.");
		System.exit(0);
	}

	public Main(URL[] urls, String[] args) {
		URLClassLoader classLoader = new URLClassLoader(urls, this.getClass()
				.getClassLoader());

		try {
			Class c = classLoader.loadClass(StandaloneGen.class.getName());
			StandaloneGen generator = (StandaloneGen) c.newInstance();
			System.exit(generator.go(args));
//			generator.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	StandaloneGen generator;

}
