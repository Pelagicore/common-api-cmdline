package org.genivi.commonapi.cmdline.main;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * That class implements a main() method which gets 2 parameter: - The name of a
 * folder where JAR files are going to be found - The name of a main class which
 * is loaded using a classloader giving access to the classes located in the
 * JARs mentioned above
 * 
 * After loading that main class,
 * 
 * @author Jacques Guillou (pelagicore)
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		String generatorsPath = args[0];
		if (generatorsPath == null)
			generatorsPath = ".";

		String mainClassName = args[1];

		ArrayList<URL> urls = new ArrayList<URL>();
		for (File jarFile : new File(generatorsPath).listFiles()) {
			if (jarFile.getName().endsWith(".jar")) {
				urls.add(jarFile.toURI().toURL());
//				System.out.println("Found generator JAR : " + jarFile);
			}
		}

		URL[] urlArray = new URL[urls.size()];
		urls.toArray(urlArray);

		String argsWithoutPluginsPath[] = new String[args.length - 2];
		System.arraycopy(args, 2, argsWithoutPluginsPath, 0,
				argsWithoutPluginsPath.length);

		int returnCode = -1;

		try {

			URLClassLoader classLoader = new URLClassLoader(urlArray,
					new ClassLoader() {
					});

			System.out.println("Loading main class : " + mainClassName);

			Class<LaunchableWithArgs> c = (Class<LaunchableWithArgs>) classLoader
					.loadClass(mainClassName);

			Thread.currentThread().setContextClassLoader(classLoader);

			LaunchableWithArgs generator = c.newInstance();

			returnCode = generator.go(argsWithoutPluginsPath);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		System.exit(returnCode);
	}

	// public Main(URL[] urls, String[] args) {
	//
	// // ClassLoader thisClassLoader = this.getClass().getClassLoader();
	// //
	// // ClassLoader myClassLoader = new ClassLoader() {
	// // public Class<?> findClass(String name) {
	// // // System.out.println("Loading class " + name);
	// // // if (name.equals(MAIN_CLASS_NAME)) return null;
	// //
	// // return null;
	// // }
	// //
	// // public URL findResource(String name) {
	// // System.out.println("Loading resource " + name);
	// // return null;
	// // }
	// // };
	// //
	// // URLClassLoader classLoaderOld = new URLClassLoader(urls,
	// this.getClass().getClassLoader());
	//
	// }

}
