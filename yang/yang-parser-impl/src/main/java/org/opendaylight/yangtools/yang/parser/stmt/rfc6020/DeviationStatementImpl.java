/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.parser.stmt.rfc6020;

import static org.opendaylight.yangtools.yang.parser.spi.SubstatementValidator.MAX;

import javax.annotation.Nonnull;
import org.opendaylight.yangtools.yang.model.api.Rfc6020Mapping;
import org.opendaylight.yangtools.yang.model.api.meta.EffectiveStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.DeviationStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.SchemaNodeIdentifier;
import org.opendaylight.yangtools.yang.parser.spi.SubstatementValidator;
import org.opendaylight.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.opendaylight.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.opendaylight.yangtools.yang.parser.spi.meta.StmtContext;
import org.opendaylight.yangtools.yang.parser.stmt.rfc6020.effective.DeviationEffectiveStatementImpl;

public class DeviationStatementImpl extends AbstractDeclaredStatement<SchemaNodeIdentifier> implements DeviationStatement {
    private static final SubstatementValidator SUBSTATEMENT_VALIDATOR = SubstatementValidator.builder(Rfc6020Mapping
            .DEVIATION)
            .add(Rfc6020Mapping.DESCRIPTION, 0, 1)
            .add(Rfc6020Mapping.DEVIATE, 0, MAX)
            .add(Rfc6020Mapping.REFERENCE, 0, 1)
            .build();

    protected DeviationStatementImpl(final StmtContext<SchemaNodeIdentifier, DeviationStatement, ?> context) {
        super(context);
    }

    public static class Definition extends AbstractStatementSupport<SchemaNodeIdentifier,DeviationStatement,EffectiveStatement<SchemaNodeIdentifier,DeviationStatement>> {

        public Definition() {
            super(Rfc6020Mapping.DEVIATION);
        }

        @Override
        public SchemaNodeIdentifier parseArgumentValue(final StmtContext<?, ?, ?> ctx, final String value) {
            return Utils.nodeIdentifierFromPath(ctx, value);
        }

        @Override
        public DeviationStatement createDeclared(final StmtContext<SchemaNodeIdentifier, DeviationStatement, ?> ctx) {
            return new DeviationStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<SchemaNodeIdentifier, DeviationStatement> createEffective(
                final StmtContext<SchemaNodeIdentifier, DeviationStatement, EffectiveStatement<SchemaNodeIdentifier, DeviationStatement>> ctx) {
            return new DeviationEffectiveStatementImpl(ctx);
        }

        @Override
        public void onFullDefinitionDeclared(StmtContext.Mutable<SchemaNodeIdentifier, DeviationStatement,
                EffectiveStatement<SchemaNodeIdentifier, DeviationStatement>> stmt) {
            super.onFullDefinitionDeclared(stmt);
            SUBSTATEMENT_VALIDATOR.validate(stmt);
        }
    }

    @Nonnull @Override
    public SchemaNodeIdentifier getTargetNode() {
        return argument();
    }
}
