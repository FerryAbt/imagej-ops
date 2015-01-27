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
package net.imagej.ops.features.sets;

import java.util.ArrayList;
import java.util.List;

import net.imagej.ops.AbstractOutputFunction;
import net.imagej.ops.Contingent;
import net.imagej.ops.OpService;
import net.imagej.ops.features.AbstractFeatureSet;
import net.imagej.ops.features.FeatureSet;
import net.imagej.ops.features.zernike.ZernikeComputer;
import net.imagej.ops.features.zernike.ZernikeMoment;
import net.imglib2.IterableInterval;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.util.Pair;
import net.imglib2.util.ValuePair;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * {@link FeatureSet} containing Zernike Moments {@link Feature}s
 * 
 * @author Andreas Graumann, University of Konstanz
 * 
 * @param <I>
 */
@Plugin(type = FeatureSet.class, label = "Zernike Moment Features")
public class ZernikeFeatureSet<T extends RealType<T>> extends
        AbstractFeatureSet<IterableInterval<T>> implements Contingent {

    @Parameter
    private OpService ops;

    @Parameter(label = "Compute Magnitude")
    private boolean computeMagnitude;

    @Parameter(label = "Compute Phase")
    private boolean computePhase;

    @Parameter(label = "Order Min", min = "1", max = "10", stepSize = "1", initializer = "2")
    private int orderMin;

    @Parameter(label = "Oder Max", min = "1", max = "10", stepSize = "1", initializer = "6")
    private int orderMax;

    private ZernikeComputer m_op;

    @Override
    public boolean conforms() {
        // something to compute?
        if (!computeMagnitude && !computePhase) {
            return false;
        }

        // dimension must be 2
        if (!(getInput().numDimensions() == 2)) {
            return false;
        }

        return true;
    }

    @Override
    protected List<Pair<String, DoubleType>> safeCompute(
            IterableInterval<T> input, List<Pair<String, DoubleType>> output) {
        output.clear();

        // get ZernikeComputer
        if (m_op == null) {
            try {
                m_op = ops.op(ZernikeComputer.class, input, orderMin, orderMax);
            } catch (Exception e) {
                throw new IllegalStateException(
                        "Can not find suitable op! Error message: "
                                + e.getMessage());
            }
        }

        // run zernike computer
        m_op.run();

        for (ZernikeMoment moment : m_op.getAllZernikeMoments()) {
            if (computeMagnitude) {
                String featureName = "Zernike Magnitude of order "
                        + moment.getN() + " and repitition " + moment.getM();
                DoubleType featureValue = new DoubleType(moment.getMagnitude());
                output.add(new ValuePair<String, DoubleType>(featureName,
                        featureValue));
            }
            if (computePhase) {

                String featureName = "Zernike Phase of order " + moment.getN()
                        + " and repitition " + moment.getM();
                DoubleType featureValue = new DoubleType(moment.getPhase());

                output.add(new ValuePair<String, DoubleType>(featureName,
                        featureValue));
            }
        }

        return output;
    }

    @Override
    protected void init() {
        // TODO Auto-generated method stub

    }
}
