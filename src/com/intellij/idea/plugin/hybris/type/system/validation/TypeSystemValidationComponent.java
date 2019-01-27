/*
 * This file is part of "hybris integration" plugin for Intellij IDEA.
 * Copyright (C) 2014-2016 Alexander Bartash <AlexanderBartash@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.intellij.idea.plugin.hybris.type.system.validation;

import com.intellij.idea.plugin.hybris.type.system.validation.impl.ItemsXMLChangedListener;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.project.ProjectManager;


/**
 * Created 19:56 11 January 2015
 *
 * @author Alexander Bartash <AlexanderBartash@gmail.com>
 */
public class TypeSystemValidationComponent implements ApplicationComponent {

    protected final ItemsXMLChangedListener itemsXMLChangedListener = new ItemsXMLChangedListener();

    @Override
    public void initComponent() {
        ApplicationManager.getApplication().getMessageBus().connect().subscribe(
            ProjectManager.TOPIC, this.itemsXMLChangedListener
        );
    }
}
