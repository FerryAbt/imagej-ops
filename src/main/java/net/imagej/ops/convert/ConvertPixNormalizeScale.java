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

package net.imagej.ops.convert;

import java.util.List;

import net.imagej.ops.Op;
import net.imagej.ops.OpService;
import net.imagej.ops.normalize.NormalizeRealType;
import net.imglib2.IterableInterval;
import net.imglib2.type.numeric.RealType;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * @author Martin Horn
 */
@Plugin(type = Op.class, name = Convert.NAME)
public class ConvertPixNormalizeScale<I extends RealType<I>, O extends RealType<O>>
	extends ConvertPixScale<I, O>
{

	@Parameter
	private OpService ops;

	@Override
	public void checkInput(final I inType, final O outType) {
		outMin = outType.getMinValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void checkInput(IterableInterval<I> in) {
		List<I> minmax = (List<I>) ops.run("minmax", in);
		I inType = in.firstElement().createVariable();
		factor =
			NormalizeRealType.normalizationFactor(minmax.get(0).getRealDouble(),
				minmax.get(1).getRealDouble(), inType.getMinValue(), inType
					.getMaxValue());

		inMin = minmax.get(0).getRealDouble();

	}

	@Override
	public boolean conforms() {
		// only conforms if an input source has been provided and the scale factor
		// was calculated
		return factor != 0;
	}

}
