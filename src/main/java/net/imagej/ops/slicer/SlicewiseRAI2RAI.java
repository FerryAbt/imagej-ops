/*
 * #%L
 * ImageJ OPS: a framework for reusable algorithms.
 * %%
 * Copyright (C) 2014 Board of Regents of the University of
 * Wisconsin-Madison and University of Konstanz.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package net.imagej.ops.slicer;

import net.imagej.ops.AbstractFunction;
import net.imagej.ops.Function;
import net.imagej.ops.Op;
import net.imagej.ops.OpService;
import net.imglib2.RandomAccessibleInterval;

import org.scijava.Priority;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * @author Christian Dietz
 * @author Martin Horn
 */
@Plugin(type = Op.class, name = "slicemapper",
	priority = Priority.VERY_HIGH_PRIORITY)
public class SlicewiseRAI2RAI<I, O> extends
	AbstractFunction<RandomAccessibleInterval<I>, RandomAccessibleInterval<O>>
	implements
	Slicewise<RandomAccessibleInterval<I>, RandomAccessibleInterval<O>>
{

	@Parameter
	private OpService opService;

	@Parameter
	private Function<I, O> func;

	@Parameter
	private int[] axisIndices;

	@Override
	public RandomAccessibleInterval<O> compute(RandomAccessibleInterval<I> input,
		RandomAccessibleInterval<O> output)
	{

		opService.run("map", new CroppedIterableInterval(opService, output,
			axisIndices), new CroppedIterableInterval(opService, input, axisIndices),
			func);

		return output;
	}

}
