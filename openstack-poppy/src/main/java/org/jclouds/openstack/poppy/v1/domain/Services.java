/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.openstack.poppy.v1.domain;

import java.beans.ConstructorProperties;

import org.jclouds.openstack.v2_0.domain.Link;
import org.jclouds.openstack.v2_0.domain.PaginatedCollection;

import com.google.common.collect.ImmutableSet;

/**
 * A collection of Networks
 */
public class Services extends PaginatedCollection<Service> {
   public static final Services EMPTY = new Services(ImmutableSet.<Service> of(), ImmutableSet.<Link> of());

   @ConstructorProperties({"services", "links"})
   protected Services(Iterable<Service> services, Iterable<Link> servicesLinks) {
      super(services, servicesLinks);
   }
}
