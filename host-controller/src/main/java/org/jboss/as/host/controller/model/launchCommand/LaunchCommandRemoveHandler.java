/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.jboss.as.host.controller.model.launchCommand;


import java.util.Locale;
import org.jboss.as.controller.AbstractRemoveStepHandler;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.descriptions.DescriptionProvider;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.REMOVE;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.DESCRIPTION;
import org.jboss.as.controller.descriptions.common.InterfaceDescription;
import org.jboss.as.controller.AttributeDefinition;

import org.jboss.dmr.ModelNode;

import java.util.ResourceBundle;

/**
 * Handler for the Launch Command resource remove operation.
 *
 * @author John O'Hara (c) 2011 Red Hat Inc.
 */
public class LaunchCommandRemoveHandler extends AbstractRemoveStepHandler implements DescriptionProvider {

    public static final String OPERATION_NAME = REMOVE;

    public static final LaunchCommandRemoveHandler INSTANCE = new LaunchCommandRemoveHandler();

    private static final String RESOURCE_NAME = InterfaceDescription.class.getPackage().getName() + ".LocalDescriptions";

    /**
     * Create the InterfaceRemoveHandler
     */
    protected LaunchCommandRemoveHandler() {
    }

    @Override
    public ModelNode getModelDescription(Locale locale) {
        final ResourceBundle bundle = getResourceBundle(locale);
        final ModelNode root = new ModelNode();
        root.get(OPERATION_NAME).set(ADD);
        root.get(DESCRIPTION).set(bundle.getString("launch-command.remove"));
        // Add the interface criteria attributes
/*
        for(final AttributeDefinition def : ROOT_ATTRIBUTES) {
            def.addOperationParameterDescription(bundle, "launch-command", root);
        }
*/
        return root;
//        return InterfaceDescription.getInterfaceRemoveOperation(locale);
    }

    protected String getInterfaceName(ModelNode operation) {
        final ModelNode opAddr = operation.require(OP_ADDR);
        PathAddress address = PathAddress.pathAddress(opAddr);
        String name = address.getLastElement().getValue();
        return name;
    }

    private static ResourceBundle getResourceBundle(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return ResourceBundle.getBundle(RESOURCE_NAME, locale);
    }
}
