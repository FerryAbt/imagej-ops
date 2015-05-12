package net.imagej.ops.create;

import net.imglib2.Dimensions;
import net.imglib2.img.ImgFactory;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.img.cell.CellImgFactory;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

import org.scijava.plugin.Parameter;

public class DefaultFactoryHeuristic<T extends RealType<T> & NativeType<T>>
		extends FactoryHeuristic<T> {

	@Parameter
	private Dimensions dims;

	@Parameter
	private ImgFactory<T> output;

	@Override
	public ImgFactory<T> getOutput() {
		return output;
	}

	@Override
	public void setOutput(ImgFactory<T> output) {
		this.output = output;
	}

	@Override
	public void run() {
		double d = 1;
		for (int i = 0; i < dims.numDimensions(); i++) {
			d *= dims.dimension(i);
		}

		if (d > Integer.MAX_VALUE) {
			output = new CellImgFactory<T>();
		} else {
			output = new ArrayImgFactory<T>();
		}
	}

}
