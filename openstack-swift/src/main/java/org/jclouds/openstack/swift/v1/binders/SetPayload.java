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
package org.jclouds.openstack.swift.v1.binders;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.io.BaseEncoding.base16;
import static com.google.common.net.HttpHeaders.ETAG;
import static com.google.common.net.HttpHeaders.TRANSFER_ENCODING;

import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequest.Builder;
import org.jclouds.io.Payload;
import org.jclouds.rest.Binder;

public class SetPayload implements Binder {
   @SuppressWarnings("unchecked")
   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object input) {
      Builder<?> builder = request.toBuilder();
      Payload payload = Payload.class.cast(input);
      Long contentLength = payload.getContentMetadata().getContentLength();
      if (contentLength != null && contentLength >= 0) {
         checkArgument(contentLength <= 5l * 1024 * 1024 * 1024, "maximum size for put object is 5GB, %s",
               contentLength);
      } else {
         builder.replaceHeader(TRANSFER_ENCODING, "chunked").build();
      }
      byte[] md5 = payload.getContentMetadata().getContentMD5();
      if (md5 != null) {
         // Swift will validate the md5, if placed as an ETag header
         builder.replaceHeader(ETAG, base16().lowerCase().encode(md5));
      }
      return (R) builder.payload(payload).build();
   }
}
