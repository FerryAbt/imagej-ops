package net.imagej.ops.create;

import net.imagej.ops.OutputOp;
import net.imglib2.img.ImgFactory;
import net.imglib2.type.Type;

public abstract class FactoryHeuristic<T extends Type<T>> implements
		OutputOp<ImgFactory<T>> {

}
