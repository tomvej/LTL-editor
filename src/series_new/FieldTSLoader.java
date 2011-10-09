//Copyright (C) 2011 Tomáš Vejpustek
//Full copyright notice found in src/LICENSE.
package series_new;

import java.io.BufferedReader;

/**
 * Reads time series from a CSV-like file which is separated into lines and fields (columns).
 * 
 * @author Tomáš Vejpustek
 *
 */
public class FieldTSLoader extends AbstractLineTSLoader {
	private int time,conc,deriv; //field indices
	private int maxIndex;
	private String separator; //field separators

	/**
	 * Specifies field separator and indices of relevant fields.
	 * @param input Input Reader.
	 * @param fieldSeparator Separator of fields
	 * @param timeFieldIndex Index of time value
	 * @param concentrationFieldIndex Index of concentration
	 * @param derivativeFieldIndex Index of derivative
	 * @throws TSLoaderException
	 */
	public FieldTSLoader(BufferedReader input, String fieldSeparator, int timeFieldIndex, int concentrationFieldIndex, int derivativeFieldIndex) throws TSLoaderException {
		super(input);
		if (timeFieldIndex < 0) {
			throw new IllegalArgumentException("Index of time field is negative.");
		}
		if (concentrationFieldIndex < 0) {
			throw new IllegalArgumentException("Index of concentration field is negative.");
		}
		if (derivativeFieldIndex < 0) {
			throw new IllegalArgumentException("Index of derivative field is negative.");
		}
		if (fieldSeparator == null || fieldSeparator.isEmpty()) {
			throw new IllegalArgumentException("Field separator is empty.");
		}
		separator = fieldSeparator;
		time = timeFieldIndex;
		conc = concentrationFieldIndex;
		deriv = derivativeFieldIndex;
		maxIndex = Math.max(time, Math.max(conc, deriv));
	}
	
	/**
	 * Splits line according to separator given in constructor.
	 */
	private String [] splitLine(String line) {
		return line.split(separator);
	}
	
	/**
	 * Returns value of a field with particular index. Throws error when the number is too garbled.
	 */
	private double getField(String line, int index) throws TSLoaderFormatException {
		String [] fields = splitLine(line);
		try {
			return Double.valueOf(fields[index]);
		} catch (NumberFormatException nfe) {
			throw new TSLoaderFormatException("tsl_num_in", "Unintelligible decimal number.", getLineNum(), line);
		}
	}

	@Override
	protected void checkFormat(String line) throws TSLoaderFormatException {
		if (splitLine(line).length < maxIndex) {
			throw new TSLoaderFormatException("tsl_field_num", "Too few fields.", getLineNum(), line);
		}
	}

	@Override
	protected double getConcentration(String line) throws TSLoaderFormatException {
		return getField(line, conc);
	}

	@Override
	protected double getDerivative(String line) throws TSLoaderFormatException {
		return getField(line, deriv);
	}

	@Override
	protected double getTime(String line) throws TSLoaderFormatException {
		return getField(line, time);
	}
}
