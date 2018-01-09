/*
 *  Copyright (c) 2017 Otávio Santana and others
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the Eclipse Public License v1.0
 *   and Apache License v2.0 which accompanies this distribution.
 *   The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *   and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 *   You may elect to redistribute this code under either of these licenses.
 *
 *   Contributors:
 *
 *   Otavio Santana
 */
package org.jnosql.artemis.graph;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.apache.tinkerpop.gremlin.process.traversal.Order.incr;

final class DefaultVertexTraversalOrder extends AbstractVertexTraversal implements VertexTraversalOrder {

    private final String property;

    DefaultVertexTraversalOrder(Supplier<GraphTraversal<?, ?>> supplier, Function<GraphTraversal<?, ?>,
            GraphTraversal<Vertex, Vertex>> flow, VertexConverter converter, String property) {
        super(supplier, flow, converter);
        this.property = property;
    }

    @Override
    public VertexTraversal asc() {
        return new DefaultVertexTraversal(supplier,
                flow.andThen(g -> flow.andThen(g -> g.order().by(property, incr))),
                converter);
    }

    @Override
    public VertexTraversal desc() {
        return new DefaultVertexTraversal(supplier,
                flow.andThen(g -> flow.andThen(g -> g.order().by(property, incr)))
                , converter);
    }
}