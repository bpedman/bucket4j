/*
 *
 * Copyright 2015-2018 Vladimir Bukhtoyarov
 *
 *       Licensed under the Apache License, Version 2.0 (the "License");
 *       you may not use this file except in compliance with the License.
 *       You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */

package io.github.bucket4j.grid.infinispan;


import io.github.bucket4j.Extension;
import io.github.bucket4j.grid.GridBucketState;
import io.github.bucket4j.grid.ProxyManager;

import io.github.bucket4j.serialization.SerializationHandle;
import org.infinispan.functional.FunctionalMap.ReadWriteMap;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

/**
 * The extension of Bucket4j library addressed to support <a href="https://ignite.apache.org/">Apache ignite</a> in-memory computing platform.
 *
 * Use this extension only if you need in asynchronous API, else stay at {@link io.github.bucket4j.grid.jcache.JCache}
 */
public class Infinispan implements Extension<InfinispanBucketBuilder> {

    /**
     * {@inheritDoc}
     *
     * @return new instance of {@link InfinispanBucketBuilder}
     */
    @Override
    public InfinispanBucketBuilder builder() {
        return new InfinispanBucketBuilder();
    }

    /**
     * Creates {@link InfinispanProxyManager} for specified cache.
     *
     * @param readWriteMap cache for storing state of buckets
     * @param <K> type of keys in the cache
     * @return {@link ProxyManager} for specified cache.
     */
    public <K extends Serializable> ProxyManager<K> proxyManagerForMap(ReadWriteMap<K, GridBucketState> readWriteMap) {
        return new InfinispanProxyManager<>(readWriteMap);
    }

    @Override
    public Collection<SerializationHandle<?>> getSerializers() {
        return Arrays.asList(SerializableFunctionAdapter.SERIALIZATION_HANDLE);
    }

}
