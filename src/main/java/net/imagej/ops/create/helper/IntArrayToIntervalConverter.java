package net.imagej.ops.create.helper;

import net.imglib2.FinalInterval;
import net.imglib2.Interval;

import org.scijava.convert.AbstractConverter;
import org.scijava.convert.ConversionRequest;
import org.scijava.convert.Converter;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class)
public class IntArrayToIntervalConverter extends
		AbstractConverter<int[], Interval> implements
		Converter<int[], Interval> {

	@Override
	public <T> T convert(Object src, Class<T> dest) {

		int[] input = (int[]) src;
		long[] convert = new long[input.length];
		for (int i = 0; i < input.length; i++) {
			convert[i] = input[i];
		}

		return (T) new FinalInterval(convert);
	}

	@Override
	public Class<Interval> getOutputType() {
		return Interval.class;
	}

	@Override
	public Class<int[]> getInputType() {
		return int[].class;
	}

	@Override
	public boolean supports(ConversionRequest request) {
		Class<?> destClass = (request.destClass() != null) ? request.destClass() : request.destType().getClass();
		
		return request.sourceClass() == int[].class
				&& Interval.class.isAssignableFrom(destClass);
	}
}
