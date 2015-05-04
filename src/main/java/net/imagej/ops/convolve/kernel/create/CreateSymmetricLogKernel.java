/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2014 - 2015 Board of Regents of the University of
 * Wisconsin-Madison, University of Konstanz and Brian Northan.
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

package net.imagej.ops.convolve.kernel.create;

import net.imagej.ops.Op;
import net.imagej.ops.Ops;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.ComplexType;

import org.scijava.Priority;
import org.scijava.plugin.Plugin;

/**
 * Convenience op for generating a symmetric LOG kernel
 * 
 * @author bnorthan
 * @param <T>
 */
@Plugin(type = Op.class, name = Ops.LogKernel.NAME,
	priority = Priority.HIGH_PRIORITY)
public class CreateSymmetricLogKernel<T extends ComplexType<T>> extends
	AbstractCreateSymmetricKernel<T> implements Ops.LogKernel
{

	@Override
	public void run() {

		double[] sigmas = new double[numDimensions];

		for (int d = 0; d < numDimensions; d++) {
			sigmas[d] = sigma;
		}

		if (calibration == null) {
			calibration = new double[numDimensions];

			for (int i = 0; i < numDimensions; i++) {
				calibration[i] = 1.0;
			}
		}

		output = (Img<T>) ops.run("logkernel", outType, fac, sigmas, calibration);
	}
}
