package org.genivi.commonapi.cmdline.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * That program take 2 parameters: - The name of a folder where some JAR files
 * can be found. - The name of a main class to be loaded and executed The
 * program loads the main class using a classloader which can load any class
 * from the JAR files mentioned above, and activates it by calling its "go"
 * method.
 *
 * You can use that program to get the same effect as when agregating many jars
 * into one.
 *
 * @author Jacques Guillou (pelagicore)
 */
public class Main {

	public static class CustomURLConnection extends URLConnection {

		protected CustomURLConnection(URL url, InputStream stream) {
			super(url);
			m_stream = stream;
		}

		@Override
		public void connect() throws IOException {
			// Do your job here. As of now it merely prints "Connected!".
			System.out.println("Connected!");
		}

		public InputStream getInputStream() throws IOException {
			return m_stream;
		}

		private InputStream m_stream;
	}

	public static class CustomURLStreamHandler extends URLStreamHandler {

		public CustomURLStreamHandler(URLClassLoader classLoader) {
			m_classLoader = classLoader;
		}

		@Override
		protected URLConnection openConnection(URL url) throws IOException {
			String f = url.getFile();
			if (!f.startsWith("/"))
				throw new IOException("Bad URL " + url.toString());
			f = f.substring(f.indexOf('/') + 1);

			if (!f.startsWith("plugin/"))
				throw new IOException("Bad URL " + url.toString());
			f = f.substring(f.indexOf('/') + 1);

			f = f.substring(f.indexOf('/') + 1); // remove plugin name/ID

			InputStream s = m_classLoader.getResourceAsStream(f);
			return new CustomURLConnection(url, s);
		}

		private URLClassLoader m_classLoader;

	}

	public static class CustomURLStreamHandlerFactory implements
			URLStreamHandlerFactory {

		public CustomURLStreamHandlerFactory(URLClassLoader classLoader) {
			m_classLoader = classLoader;
		}

		@Override
		public URLStreamHandler createURLStreamHandler(String protocol) {
			if ("platform".equals(protocol)) {
				return new CustomURLStreamHandler(m_classLoader);
			}

			return null;
		}

		private URLClassLoader m_classLoader;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		String generatorsPath = args[0];
		if (generatorsPath == null)
			generatorsPath = ".";

		String mainClassName = args[1];

		ArrayList<File> files = new ArrayList<File>();
		if (new File(generatorsPath).exists()) {
			for (File jarFile : new File(generatorsPath).listFiles()) {
				if (jarFile.getName().endsWith(".jar")) {
					files.add(jarFile);
				}
			}
		}

		// Sort the list to use the most recent JARs
		Collections.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				if (o1.lastModified() == o2.lastModified())
					return 0;
				return (o1.lastModified() < o2.lastModified()) ? 1 : -1;
			}
		});

		ArrayList<URL> urls = new ArrayList<URL>();

		for (File jarFile : files)
			urls.add(jarFile.toURI().toURL());

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

			URL.setURLStreamHandlerFactory(new CustomURLStreamHandlerFactory(
					classLoader));

			// System.out.println("Loading main class : " + mainClassName);

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

}
