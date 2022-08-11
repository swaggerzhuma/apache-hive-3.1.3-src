/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.hive.hcatalog.data.transfer;

import java.util.Map;

/**
 * This is a base class for 
 * {@link ReadEntity.Builder} / {@link WriteEntity.Builder}.
 * Many fields in them are common, so this class
 * contains the common fields.
 */

abstract class EntityBase {

  String region;
  String tableName;
  String dbName;
  Map<String, String> partitionKVs;

  /**
   * Common methods for {@link ReadEntity} and {@link WriteEntity}
   */

  abstract static class Entity extends EntityBase {

    public String getRegion() {
      return region;
    }

    public String getTableName() {
      return tableName;
    }

    public String getDbName() {
      return dbName;
    }

    public Map<String, String> getPartitionKVs() {
      return partitionKVs;
    }
  }
}
