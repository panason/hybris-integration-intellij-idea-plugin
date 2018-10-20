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

package com.intellij.idea.plugin.hybris.type.system.validation.impl;

import com.intellij.idea.plugin.hybris.common.services.NotificationSender;
import com.intellij.idea.plugin.hybris.common.services.impl.NotificationSenderImpl;
import com.intellij.idea.plugin.hybris.common.utils.HybrisI18NBundleUtils;
import com.intellij.idea.plugin.hybris.common.utils.HybrisXmlFileType;
import com.intellij.idea.plugin.hybris.type.system.validation.ItemsFileValidation;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static com.intellij.idea.plugin.hybris.common.HybrisConstants.TS_ITEMS_VALIDATION_WARN;

/**
 * @author Vlad Bozhenok <vladbozhenok@gmail.com>
 */
public class ItemsXMLChangedListener implements ProjectManagerListener {

    private static final String ITEM_XML_VALIDATION_GROUP = "Items XML validation group";

    private static final NotificationGroup NOTIFICATION_GROUP = new NotificationGroup(
        ITEM_XML_VALIDATION_GROUP, NotificationDisplayType.BALLOON, true
    );

    @Override
    public void projectOpened(final Project project) {
        final NotificationSender notifications = new NotificationSenderImpl(NOTIFICATION_GROUP, project);

        project.getMessageBus().connect().subscribe(
            FileEditorManagerListener.FILE_EDITOR_MANAGER, new ItemsXmlFileEditorManagerListener(project, notifications)
        );

        StartupManager.getInstance(project).runWhenProjectIsInitialized(() -> {
            final Collection<VirtualFile> files = FileTypeIndex.getFiles(
                HybrisXmlFileType.INSTANCE, GlobalSearchScope.projectScope(project)
            );

            if (files.stream().anyMatch(new DefaultItemsFileValidation(project)::isFileOutOfDate)) {
                notifications.showWarningMessage(
                    HybrisI18NBundleUtils.message(TS_ITEMS_VALIDATION_WARN)
                );
            }
        });
    }

    private static class ItemsXmlFileEditorManagerListener implements FileEditorManagerListener {

        private final Project project;
        private final NotificationSender notifications;
        private ItemsFileValidation validator;

        public ItemsXmlFileEditorManagerListener(
            @NotNull final Project project,
            @NotNull final NotificationSender notifications
        ) {
            this.project = project;
            this.notifications = notifications;
            validator = new DefaultItemsFileValidation(project);
        }

        @Override
        public void fileOpened(@NotNull final FileEditorManager source, @NotNull final VirtualFile file) {
            StartupManager.getInstance(project).runWhenProjectIsInitialized(() -> {
                if (this.validator.isFileOutOfDate(file)) {
                    notifications.showWarningMessage(HybrisI18NBundleUtils.message(TS_ITEMS_VALIDATION_WARN));
                }
            });
        }

        @Override
        public void selectionChanged(@NotNull final FileEditorManagerEvent event) {
            if (null == event.getNewFile()) {
                return;
            }

            StartupManager.getInstance(project).runWhenProjectIsInitialized(() -> {
                if (this.validator.isFileOutOfDate(event.getNewFile())) {
                    notifications.showWarningMessage(HybrisI18NBundleUtils.message(TS_ITEMS_VALIDATION_WARN));
                }
            });
        }
    }
}
