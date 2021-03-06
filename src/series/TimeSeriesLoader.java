//Copyright (C) 2011 Tomáš Vejpustek
//Full copyright notice found in src/LICENSE.  
package series;

import java.util.Map;

import exceptions.TSLoaderException;


/**
 * Wrapper for time series input (e.g. a StringReader). Expects time series data points to be in ascending time order.
 * Time interval between points need not be uniform.
 * 
 * Initiation of a <code>TimeSeriesLoader</code> is expected to be a part of its constructor.
 * 
 * @author Tomáš Vejpustek
 *
 */
public interface TimeSeriesLoader {
	
	/**
	 * Reads a single point. Used to read the points sequentially.
	 * 
	 * @return Point read or <code>null</code> if the end has been reached. 
	 * @throws TSLoaderException when IO error is encountered.
	 */
	public TimeSeriesPoint readPoint() throws TSLoaderException;
	
	/**
	 * Transforms given loader into a map of string parameters. One of them always should be "name", which determines the type of loader.
	 * @return <code>Map</code> of parameters.
	 */
	public Map<String, String> export();
}
