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

package imagej.ops.commands.threshold;

import imagej.command.Command;
import imagej.ops.Op;
import imagej.ops.OpService;
import imagej.ops.slicer.Slicewise;
import imagej.ops.threshold.GlobalThresholdMethod;
import net.imglib2.Axis;
import net.imglib2.meta.ImgPlus;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.numeric.RealType;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * TODO: should actually live in a different package!! OR: can this be
 * auto-generated?? (e.g. based on other plugin annotations)#
 * 
 * @author Martin Horn
 */
@Plugin(type = Command.class, menuPath = "Image > Threshold > Apply Threshold")
public class GlobalThresholder<T extends RealType<T>> implements Command {

    @Parameter
    private GlobalThresholdMethod<T> method;

    @Parameter
    private OpService ops;

    // should not be Dataset, DisplayService, ...
    @Parameter
    private ImgPlus<T> in;

    // needs to be created by the pre-processor!
    @Parameter(type = ItemIO.BOTH)
    private ImgPlus<BitType> out;

    // we need another widget for this!!
    @Parameter
    private Axis[] axes;

    @Override
    public void run() {

        Op threshold = ops.op("threshold", out, in, method);

        // TODO actually map axes to int array
        ops.run(Slicewise.class, out, in, threshold, new int[]{0, 1});

    }
}
