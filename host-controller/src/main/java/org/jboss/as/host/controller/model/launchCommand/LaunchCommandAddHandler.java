/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.host.controller.model.launchCommand;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.BOOT_TIME;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.VALUE;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.LAUNCH_COMMAND_PREFIX;

import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;

import org.jboss.as.controller.descriptions.DescriptionProvider;
import org.jboss.as.controller.descriptions.common.CommonDescriptions;
import org.jboss.as.controller.operations.validation.ModelTypeValidator;
import org.jboss.as.controller.operations.validation.ParametersValidator;
import org.jboss.as.controller.operations.validation.StringLengthValidator;
import org.jboss.as.controller.OperationStepHandler;
import org.jboss.as.controller.PathAddress;

import org.jboss.as.controller.operations.common.Util;

import java.util.Locale;

/**
 * {@code OperationHandler} for the launch command add operation.
 *
 * @author John O'Hara
 */
//public final class LaunchCommandAddHandler extends AbstractAddStepHandler {
public final class LaunchCommandAddHandler implements OperationStepHandler, DescriptionProvider {
    public static final String OPERATION_NAME = ADD;

    public static final LaunchCommandAddHandler INSTANCE = new LaunchCommandAddHandler(true);

    public static ModelNode getOperation(ModelNode address, String value) {
        return getOperation(address, value, null);
    }

    public static ModelNode getOperation(ModelNode address, String value, Boolean boottime) {
        ModelNode op = Util.getEmptyOperation(OPERATION_NAME, address);
        if (value == null) {
            op.get(VALUE).set(new ModelNode());
        } else {
            op.get(VALUE).set(value);
        }
        if (boottime != null) {
            op.get(BOOT_TIME).set(boottime);
        }
        return op;
    }

/*
*/
    private final ParametersValidator validator = new ParametersValidator();
    private final boolean useBoottime;
//    private final boolean useBoottime = true;

    /**
     * Create the SystemPropertyAddHandler
     *
     * @param useBoottime {@code true} if the system property resource should support the "boot-time" attribute
     */
    public LaunchCommandAddHandler(boolean useBoottime) {
        this.useBoottime = useBoottime;
        validator.registerValidator(VALUE, new StringLengthValidator(0, true, true));
        if (useBoottime) {
        }
    }

    @Override
    public void execute(OperationContext context, ModelNode operation) throws OperationFailedException {
         validator.validate(operation);

        final String commandPrefix = LAUNCH_COMMAND_PREFIX;
        final String value = operation.hasDefined(VALUE) ? operation.get(VALUE).asString() : null;

        final String name = LAUNCH_COMMAND_PREFIX;
        final ModelNode model = context.createResource(PathAddress.EMPTY_ADDRESS).getModel();
        model.get(VALUE).set(value);

/*
        final boolean applyToRuntime = processEnvironment != null && processEnvironment.isRuntimeSystemPropertyUpdateAllowed(name, value, context.isBooting());
        final boolean reload = !applyToRuntime && context.getProcessType().isServer();

        final ModelNode model = context.createResource(PathAddress.EMPTY_ADDRESS).getModel();
        if (value == null) {
            model.get(VALUE).set(new ModelNode());
        } else {
            model.get(VALUE).set(value);
        }
        if (useBoottime) {
            boolean boottime = !operation.hasDefined(BOOT_TIME) || operation.get(BOOT_TIME).asBoolean();
            model.get(BOOT_TIME).set(boottime);
        }

        if (applyToRuntime) {
            final String setValue = value != null ? context.resolveExpressions(operation.require(VALUE)).asString() : null;
            SecurityActions.setSystemProperty(name, setValue);
            if (processEnvironment != null) {
                processEnvironment.systemPropertyUpdated(name, setValue);
            }
        } else if (reload) {
            context.reloadRequired();
        }

        context.completeStep(new OperationContext.RollbackHandler() {
            @Override
            public void handleRollback(OperationContext context, ModelNode operation) {
                if (reload) {
                    context.revertReloadRequired();
                }
                if (processEnvironment != null) {
                    SecurityActions.clearSystemProperty(name);
                    if (processEnvironment != null) {
                        processEnvironment.systemPropertyUpdated(name, null);
                    }
                }
            }
        });
*/
context.completeStep();
    }

    protected boolean requiresRuntimeVerification() {
        return false;
    }

    public ModelNode getModelDescription(Locale locale) {
        return CommonDescriptions.getAddSystemPropertyOperation(locale, useBoottime);
    }
}
