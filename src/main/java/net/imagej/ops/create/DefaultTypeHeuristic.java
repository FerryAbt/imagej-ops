package net.imagej.ops.create;

import net.imglib2.Interval;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;

public class DefaultTypeHeuristic<T extends RealType<T> & NativeType<T>>
		extends TypeHeuristic<T> {

	@Override
	public T createOutput(Interval input) {
		return (T) new DoubleType();
	}

	@Override
	protected T safeCompute(Interval input, T output) {
		return output;
	}
}
