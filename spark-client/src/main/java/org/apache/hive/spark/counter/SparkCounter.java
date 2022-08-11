/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hive.spark.counter;

import java.io.Serializable;
//已弃用
/*import org.apache.spark.Accumulator;
import org.apache.spark.AccumulatorParam;*/
//更改spark版本 新依赖
import  org.apache.spark.util.LongAccumulator;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkCounter implements Serializable {

  private String name;
  private String displayName;
  //已弃用
//  private Accumulator<Long> accumulator;
  private  LongAccumulator  accumulator;

  // Values of accumulators can only be read on the SparkContext side. This field is used when
  // creating a snapshot to be sent to the RSC client.
  private long accumValue;

  public SparkCounter() {
    // For serialization.
  }

  private SparkCounter(
      String name,
      String displayName,
      long value) {
    this.name = name;
    this.displayName = displayName;
    this.accumValue = value;
  }

  public SparkCounter(
    String name,
    String displayName,
    String groupName,
    long initValue,
    JavaSparkContext sparkContext) {

    this.name = name;
    this.displayName = displayName;
    //已弃用
//    LongAccumulatorParam longParam = new LongAccumulatorParam();
    String accumulatorName = groupName + "_" + name;
    //已弃用
//    this.accumulator = sparkContext.accumulator(initValue, accumulatorName, longParam);
    // 更改spark版本 更改累加器的获取方式
    this.accumulator = sparkContext.sc().longAccumulator(accumulatorName);
//    this.accumulator  =  JavaSparkContext.toSparkContext(sparkContext).longAccumulator(accumulatorName);
    // 更改spark版本 添加参数值
    this.accumulator.setValue(initValue);

  }

  public long getValue() {
    if (accumulator != null) {
      return accumulator.value();
    } else {
      return accumValue;
    }
  }

  public void increment(long incr) {
    accumulator.add(incr);
  }

  public String getName() {
    return name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  SparkCounter snapshot() {
    return new SparkCounter(name, displayName, accumulator.value());
  }

  //已弃用
/*  class LongAccumulatorParam implements AccumulatorParam<Long> {

    @Override
    public Long addAccumulator(Long t1, Long t2) {
      return t1 + t2;
    }

    @Override
    public Long addInPlace(Long r1, Long r2) {
      return r1 + r2;
    }

    @Override
    public Long zero(Long initialValue) {
      return 0L;
    }
  }*/

}
