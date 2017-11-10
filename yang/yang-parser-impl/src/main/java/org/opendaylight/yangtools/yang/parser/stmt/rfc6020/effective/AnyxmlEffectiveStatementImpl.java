/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.parser.stmt.rfc6020.effective;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.model.api.AnyXmlSchemaNode;
import org.opendaylight.yangtools.yang.model.api.DerivableSchemaNode;
import org.opendaylight.yangtools.yang.model.api.MustDefinition;
import org.opendaylight.yangtools.yang.model.api.meta.EffectiveStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.AnyxmlEffectiveStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.AnyxmlStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.MandatoryEffectiveStatement;
import org.opendaylight.yangtools.yang.parser.spi.meta.StmtContext;

public class AnyxmlEffectiveStatementImpl extends AbstractEffectiveDataSchemaNode<AnyxmlStatement>
        implements AnyxmlEffectiveStatement, AnyXmlSchemaNode, DerivableSchemaNode {

    private final Collection<MustDefinition> mustConstraints;
    private final AnyXmlSchemaNode original;
    private final boolean mandatory;

    public AnyxmlEffectiveStatementImpl(
            final StmtContext<QName, AnyxmlStatement, EffectiveStatement<QName, AnyxmlStatement>> ctx) {
        super(ctx);
        this.original = (AnyXmlSchemaNode) ctx.getOriginalCtx().map(StmtContext::buildEffective).orElse(null);
        final MandatoryEffectiveStatement mandatoryStmt = firstEffective(MandatoryEffectiveStatement.class);
        mandatory = mandatoryStmt == null ? false : mandatoryStmt.argument().booleanValue();
        mustConstraints = ImmutableSet.copyOf(allSubstatementsOfType(MustDefinition.class));
    }

    @Override
    public boolean isMandatory() {
        return mandatory;
    }

    @Override
    public Collection<MustDefinition> getMustConstraints() {
        return mustConstraints;
    }

    @Override
    public Optional<AnyXmlSchemaNode> getOriginal() {
        return Optional.ofNullable(original);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(getQName());
        result = prime * result + Objects.hashCode(getPath());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        AnyxmlEffectiveStatementImpl other = (AnyxmlEffectiveStatementImpl) obj;
        return Objects.equals(getQName(), other.getQName()) && Objects.equals(getPath(), other.getPath());
    }

    @Override
    public String toString() {
        return AnyxmlEffectiveStatementImpl.class.getSimpleName() + "["
                + "qname=" + getQName()
                + ", path=" + getPath()
                + "]";
    }
}
