package learn.dynamicClassLoading;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import learn.jms.MyLogger;

public class MyClassLoader extends URLClassLoader{

	private static final String PROTOCOL_PREFIX = "file:";
	private static final String OP_DIR = "/home/gauravabbi/learning/dcl/op/";
	private static final String JAR_FILTER = ".jar";
	private String schemaName;
	private final SchemaBuilder schemaBuilder;

	//TODO:these constructors might not be required.
//	public MyClassLoader(URL[] urls) {
//		super(urls);
//	}
//
	
	
//	public MyClassLoader(URL[] urls, ClassLoader parent,
//			URLStreamHandlerFactory factory) {
//		super(urls, parent, factory);
//	}
//
//	public MyClassLoader(URL[] urls, ClassLoader parent) {
//		super(urls, parent);
//	}
	
	public MyClassLoader(String jarPaths,ClassLoader parent) {
		super(new URL[0], parent);
		this.schemaBuilder = new SchemaBuilder(OP_DIR);
		String[] paths = jarPaths.split(";");
		List<URL> urlList = new ArrayList<URL>();
		
		for (String path : paths) {
			urlList.addAll(getUrlsForPath(path));
		}
		
		for (URL url : urlList) {
			this.addURL(url);
		}
	}

	private Collection<? extends URL> getUrlsForPath(String path){
		File pathFile = new File(path);
		if(!pathFile.exists()){
			System.out.println("Invalid Path does not exist["+pathFile+"]");
		}
		
		if(!pathFile.isDirectory()){
			//its a file, only jars will be added
			if(path.endsWith(JAR_FILTER));
			URL url = getURL(pathFile);
			return Arrays.asList(new URL[]{url});
		}
		
		return getListForDirectory(pathFile);
	}

	private Collection<? extends URL> getListForDirectory(File directory){
		String[] files = directory.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(JAR_FILTER);
			}
		});
		
		List<URL> urlList = new ArrayList<URL>();
		for (String fileName : files) {
			File jarFile = new File(fileName);
			urlList.add(getURL(jarFile));
		}
		
		return urlList;
	}

	private URL getURL(File pathFile){
		try {
			return pathFile.toURI().toURL();
		} catch (MalformedURLException e) {
			MyLogger.LOGGER.error("Error While creating URL from file["+pathFile+"]", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Order has been changed
	 * <li> find if already loaded by this class loader</li>
	 * <li> Try to find in this class loader.</li>
	 * <li> try to find in parent</li>
	 * 
	 * <b>Original delegation model is</b>
	 * 
	 * <li> find if already loaded by this class loader </li>
	 * <li>	try to find in parent </li>
	 * <li>	Try to find in this class loader. </li>
	 */
	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve)
	throws ClassNotFoundException {
		Class<?> clazz = null;
		try{
			clazz = findLoadedClass(name);
			//here order has been changed, looking here first instead of calling to parent
			if(clazz == null){
				clazz = findClass(name);
			}
			
		}catch (ClassNotFoundException e) {
			MyLogger.LOGGER.error("MyClassloader failed to load class", e);
		}
		
		if(clazz == null){
			clazz = getParent().loadClass(name);
		}
		return clazz;
	}
	
	
	@Override
	public URL findResource(String name) {
		if(name.equals(schemaName)){
			//check if already loaded
			URL schemaUrl = super.findResource(PROTOCOL_PREFIX+OP_DIR+name);
			if(schemaUrl == null){
				schemaUrl = schemaBuilder.createSchema(name);
				this.addURL(schemaUrl);
			}
			return schemaUrl;
		}
		return super.findResource(name);
	}

	
	public void setSchemaName(String schemaName){
		this.schemaName = schemaName;
	}
}
