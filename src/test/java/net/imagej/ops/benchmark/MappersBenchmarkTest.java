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

package net.imagej.ops.benchmark;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import net.imagej.ops.MathOps;
import net.imagej.ops.Op;
import net.imagej.ops.map.MapI;
import net.imagej.ops.map.MapII2II;
import net.imagej.ops.map.MapII2RAI;
import net.imagej.ops.map.ParallelMap;
import net.imagej.ops.map.ParallelMapI2I;
import net.imagej.ops.map.ParallelMapI2R;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.NumericType;
import net.imglib2.type.numeric.integer.ByteType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Benchmarking various implementations of mappers. Benchmarked since now:
 * {@link MapII2RAI}, {@link MapII2II},
 * {@link ParallelMapI2R}, {@link ParallelMapI2I}
 * 
 * @author Christian Dietz
 */
@BenchmarkOptions(benchmarkRounds = 20, warmupRounds = 1)
public class MappersBenchmarkTest extends AbstractOpBenchmark {

	private Img<ByteType> in;
	private Img<ByteType> out;
	private Op addConstant;
	private Op addConstantInplace;

	/** Needed for JUnit-Benchmarks */
	@Rule
	public TestRule benchmarkRun = new BenchmarkRule();

	@Before
	public void initImg() {
		in = generateByteTestImg(true, 1000, 1000);
		out = generateByteTestImg(false, 1000, 1000);

		addConstant = ops.op(MathOps.Add.class, null, NumericType.class, new ByteType((byte) 5));
		addConstantInplace = ops.op(AddConstantInplace.class, NumericType.class, new ByteType((byte) 5));
	}

	@Test
	public void pixelWiseTestMapper() {
		ops.run(new MapII2RAI<ByteType, ByteType>(), out, in, addConstant);
	}

	@Test
	public void pixelWiseTestMapperII() {
		ops.run(new MapII2II<ByteType, ByteType>(), out, in, addConstant);
	}

	@Test
	public void pixelWiseTestThreadedMapper() {
		ops.run(new ParallelMapI2R<ByteType, ByteType>(), out, in, addConstant);
	}

	@Test
	public void pixelWiseTestThreadedMapperII() {
		ops.run(new ParallelMapI2I<ByteType, ByteType>(),
			out, in, addConstant, out);
	}

	@Test
	public void pixelWiseTestMapperInplace() {
		ops.run(new MapI<ByteType>(), in, addConstantInplace);
	}

	@Test
	public void pixelWiseTestThreadedMapperInplace() {
		ops.run(new ParallelMap<ByteType>(), in.copy(), addConstantInplace);
	}
}
