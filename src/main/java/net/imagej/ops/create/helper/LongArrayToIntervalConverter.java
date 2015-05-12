package net.imagej.ops.create.helper;

import net.imglib2.FinalInterval;
import net.imglib2.Interval;

import org.scijava.convert.AbstractConverter;
import org.scijava.convert.ConversionRequest;
import org.scijava.convert.Converter;

public class LongArrayToIntervalConverter extends
		AbstractConverter<long[], Interval> implements
		Converter<long[], Interval> {

	@Override
	public <T> T convert(Object src, Class<T> dest) {
		long[] input = (long[]) src;
		return (T) new FinalInterval(input);
	}

	@Override
	public Class<Interval> getOutputType() {
		return Interval.class;
	}

	@Override
	public Class<long[]> getInputType() {
		return long[].class;
	}

	@Override
	public boolean supports(ConversionRequest request) {
		Class<?> destClass = (request.destClass() != null) ? request.destClass() : request.destType().getClass();
		
		return request.sourceClass() == long[].class
				&& Interval.class.isAssignableFrom(destClass);
	}
}
