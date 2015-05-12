package net.imagej.ops.create;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;

import net.imagej.ops.Ops.CreateImg;
import net.imagej.ops.OutputOp;
import net.imglib2.img.Img;
import net.imglib2.type.Type;

public abstract class AbstractCreateImg<T extends Type<T>> implements CreateImg,
		OutputOp<Img<T>> {

	@Parameter(type = ItemIO.OUTPUT)
	private Img<T> output;

	@Override
	public Img<T> getOutput() {
		return output;
	}

	@Override
	public void setOutput(Img<T> output) {
		this.output = output;
	}
}
