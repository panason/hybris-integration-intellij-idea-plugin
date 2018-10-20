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

package com.intellij.idea.plugin.hybris.common.services.impl;

import com.intellij.idea.plugin.hybris.common.services.NotificationSender;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

/**
 * @author Vlad Bozhenok <vladbozhenok@gmail.com>
 */
public class NotificationSenderImpl implements NotificationSender {

    private NotificationGroup group;
    private Project project;

    public NotificationSenderImpl(@NotNull final NotificationGroup group, @NotNull final Project project) {
        this.group = group;
        this.project = project;
    }

    @Override
    public void showInfoMessage(@NotNull final String message) {
        showMessage(message, NotificationType.INFORMATION);
    }

    @Override
    public void showWarningMessage(@NotNull final String message) {
        showMessage(message, NotificationType.WARNING);
    }

    @Override
    public void showErrorMessage(@NotNull final String message) {
        showMessage(message, NotificationType.ERROR);
    }

    private void showMessage(
        @NotNull final String message,
        @NotNull final NotificationType messageLevel
    ) {
        Validate.notNull(messageLevel);

        if (StringUtils.isEmpty(message)) {
            return;
        }

        ApplicationManager.getApplication().invokeLater(() -> {
            final Notification notification = this.group.createNotification(message, messageLevel);

            Notifications.Bus.notify(notification, project);
        });
    }
}
