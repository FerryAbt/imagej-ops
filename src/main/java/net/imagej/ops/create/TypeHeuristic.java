package net.imagej.ops.create;

import net.imagej.ops.AbstractOutputFunction;
import net.imglib2.Interval;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

public abstract class TypeHeuristic<T extends RealType<T> & NativeType<T>>
	extends AbstractOutputFunction<Interval, T> {

}
