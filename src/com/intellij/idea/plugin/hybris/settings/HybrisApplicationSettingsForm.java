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

package com.intellij.idea.plugin.hybris.settings;

import com.intellij.idea.plugin.hybris.common.utils.HybrisI18NBundleUtils;
import com.intellij.openapi.ui.InputValidatorEx;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.AddEditDeleteListPanel;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBLabel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created 21:58 29 March 2015
 *
 * @author Alexander Bartash <AlexanderBartash@gmail.com>
 */
public class HybrisApplicationSettingsForm {

    private JCheckBox enableFoldingCheckBox;
    private JCheckBox useSmartFoldingCheckBox;
    private JPanel mainPanel;
    private JPanel junkDirectoriesPanel;
    private JLabel impexLabel;
    private JLabel projectImportLabel;
    private JCheckBox groupModulesCheckBox;
    private JTextField groupCustomTextField;
    private JTextField groupNonHybrisTextField;
    private JTextField groupCustomUnusedTextField;
    private JTextField groupHybrisTextField;
    private JTextField groupHybrisUnusedTextField;
    private JCheckBox hideEmptyMiddleFoldersCheckBox;
    private JLabel projectTreeViewSettingsLabel;
    private JCheckBox defaultPlatformInReadOnly;
    private JTextField groupPlatformTextField;
    private JCheckBox followSymlink;
    private JPanel typeSystemDiagramStopWords;
    private JCheckBox scanThroughExternalModule;
    private JCheckBox excludeTestSources;
    private JPanel extensionsResourcesToExclude;
    private JCheckBox warnIfGeneratedItemsAreOutOfDateCheckBox;

    private MyListPanel junkListPanel;
    private MyListPanel tsdListPanel;
    private MyListPanel extensionsResourcesToExcludeListPanel;

    public void setData(final HybrisApplicationSettings data) {
        enableFoldingCheckBox.setSelected(data.isFoldingEnabled());
        useSmartFoldingCheckBox.setSelected(data.isUseSmartFolding());
        junkListPanel.setMyList(data.getJunkDirectoryList());
        tsdListPanel.setMyList(data.getTsdStopTypeList());
        extensionsResourcesToExcludeListPanel.setMyList(data.getExtensionsResourcesToExcludeList());
        groupModulesCheckBox.setSelected(data.isGroupModules());
        groupCustomTextField.setText(data.getGroupCustom());
        groupNonHybrisTextField.setText(data.getGroupNonHybris());
        groupCustomUnusedTextField.setText(data.getGroupOtherCustom());
        groupHybrisTextField.setText(data.getGroupHybris());
        groupHybrisUnusedTextField.setText(data.getGroupOtherHybris());
        groupPlatformTextField.setText(data.getGroupPlatform());
        hideEmptyMiddleFoldersCheckBox.setSelected(data.isHideEmptyMiddleFolders());
        defaultPlatformInReadOnly.setSelected(data.isDefaultPlatformInReadOnly());
        followSymlink.setSelected(data.isFollowSymlink());
        scanThroughExternalModule.setSelected(data.isScanThroughExternalModule());
        excludeTestSources.setSelected(data.isExcludeTestSources());
        warnIfGeneratedItemsAreOutOfDateCheckBox.setSelected(data.isWarnIfGeneratedItemsAreOutOfDate());
    }

    public void getData(final HybrisApplicationSettings data) {
        data.setFoldingEnabled(enableFoldingCheckBox.isSelected());
        data.setUseSmartFolding(useSmartFoldingCheckBox.isSelected());
        data.setJunkDirectoryList(junkListPanel.getMyList());
        data.setTsdStopTypeList(tsdListPanel.getMyList());
        data.setExtensionsResourcesToExcludeList(extensionsResourcesToExcludeListPanel.getMyList());
        data.setGroupModules(groupModulesCheckBox.isSelected());
        data.setGroupCustom(groupCustomTextField.getText());
        data.setGroupOtherCustom(groupCustomUnusedTextField.getText());
        data.setGroupHybris(groupHybrisTextField.getText());
        data.setGroupOtherHybris(groupHybrisUnusedTextField.getText());
        data.setGroupNonHybris(groupNonHybrisTextField.getText());
        data.setGroupPlatform(groupPlatformTextField.getText());
        data.setHideEmptyMiddleFolders(hideEmptyMiddleFoldersCheckBox.isSelected());
        data.setDefaultPlatformInReadOnly(defaultPlatformInReadOnly.isSelected());
        data.setFollowSymlink(followSymlink.isSelected());
        data.setScanThroughExternalModule(scanThroughExternalModule.isSelected());
        data.setExcludeTestSources(excludeTestSources.isSelected());
        data.setWarnIfGeneratedItemsAreOutOfDate(warnIfGeneratedItemsAreOutOfDateCheckBox.isSelected());
    }

    public boolean isModified(final HybrisApplicationSettings data) {
        if (enableFoldingCheckBox.isSelected() != data.isFoldingEnabled()) {
            return true;
        }
        if (useSmartFoldingCheckBox.isSelected() != data.isUseSmartFolding()) {
            return true;
        }
        if (!junkListPanel.getMyList().equals(data.getJunkDirectoryList())) {
            return true;
        }
        if (!tsdListPanel.getMyList().equals(data.getTsdStopTypeList())) {
            return true;
        }
        if (!extensionsResourcesToExcludeListPanel.getMyList().equals(data.getExtensionsResourcesToExcludeList())) {
            return true;
        }
        if (groupModulesCheckBox.isSelected() != data.isGroupModules()) {
            return true;
        }
        if (!StringUtil.equals(groupCustomTextField.getText(), data.getGroupCustom())) {
            return true;
        }
        if (!StringUtil.equals(groupCustomUnusedTextField.getText(), data.getGroupOtherCustom())) {
            return true;
        }
        if (!StringUtil.equals(groupHybrisTextField.getText(), data.getGroupHybris())) {
            return true;
        }
        if (!StringUtil.equals(groupHybrisUnusedTextField.getText(), data.getGroupOtherHybris())) {
            return true;
        }
        if (!StringUtil.equals(groupPlatformTextField.getText(), data.getGroupPlatform())) {
            return true;
        }
        if (!StringUtil.equals(groupNonHybrisTextField.getText(), data.getGroupNonHybris())) {
            return true;
        }
        if (hideEmptyMiddleFoldersCheckBox.isSelected() != data.isHideEmptyMiddleFolders()) {
            return true;
        }
        if (defaultPlatformInReadOnly.isSelected() != data.isDefaultPlatformInReadOnly()) {
            return true;
        }
        if (scanThroughExternalModule.isSelected() != data.isScanThroughExternalModule()) {
            return true;
        }
        if (followSymlink.isSelected() != data.isFollowSymlink()) {
            return true;
        }
        if (excludeTestSources.isSelected() != data.isExcludeTestSources()) {
            return true;
        }
        if (warnIfGeneratedItemsAreOutOfDateCheckBox.isSelected() != data.isWarnIfGeneratedItemsAreOutOfDate()) {
            return true;
        }
        return false;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        impexLabel = new JBLabel();
        impexLabel.setBorder(IdeBorderFactory.createTitledBorder(HybrisI18NBundleUtils.message(
            "hybris.import.settings.impex.title"), false));
        projectImportLabel = new JBLabel();
        projectImportLabel.setBorder(IdeBorderFactory.createTitledBorder(HybrisI18NBundleUtils.message(
            "hybris.import.settings.project.title")));
        junkListPanel = new MyListPanel("hybris.import.settings.junk.directory.name", "hybris.import.settings.junk.directory.popup.add.title", "hybris.import.settings.junk.directory.popup.add.text", "hybris.import.settings.junk.directory.popup.edit.title", "hybris.import.settings.junk.directory.popup.edit.text", new ArrayList<String>());
        junkDirectoriesPanel = junkListPanel;
        tsdListPanel = new MyListPanel("hybris.import.settings.tsv.diagram.name", "hybris.import.settings.tsv.diagram.popup.add.title", "hybris.import.settings.tsv.diagram.popup.add.text", "hybris.import.settings.tsv.diagram.popup.edit.title", "hybris.import.settings.tsv.diagram.popup.edit.text", new ArrayList<String>());
        typeSystemDiagramStopWords = tsdListPanel;
        extensionsResourcesToExcludeListPanel = new MyListPanel("hybris.import.settings.exclude.resources.name", "hybris.import.settings.exclude.resources.popup.add.title", "hybris.import.settings.exclude.resources.popup.add.text", "hybris.import.settings.exclude.resources.popup.edit.title", "hybris.import.settings.exclude.resources.popup.edit.text", new ArrayList<String>());
        extensionsResourcesToExclude = extensionsResourcesToExcludeListPanel;

        projectTreeViewSettingsLabel = new JBLabel();
        projectTreeViewSettingsLabel.setBorder(IdeBorderFactory.createTitledBorder(HybrisI18NBundleUtils.message(
            "hybris.project.view.tree.settings")));
    }

    public void createComponent() {
        groupModulesCheckBox.addChangeListener(changeEvent -> {
            groupCustomTextField.setEnabled(groupModulesCheckBox.isSelected());
            groupCustomUnusedTextField.setEnabled(groupModulesCheckBox.isSelected());
            groupHybrisTextField.setEnabled(groupModulesCheckBox.isSelected());
            groupHybrisUnusedTextField.setEnabled(groupModulesCheckBox.isSelected());
            groupPlatformTextField.setEnabled(groupModulesCheckBox.isSelected());
            groupNonHybrisTextField.setEnabled(groupModulesCheckBox.isSelected());
        });
    }

    private static class MyListPanel extends AddEditDeleteListPanel<String> {
        private static final long serialVersionUID = -6339262026248471671L;
        private final String addTitle;
        private final String addText;
        private final String editTitle;
        private final String editText;

        public MyListPanel(final String title, final String addTitle, final String addText, final String editTitle, final String editText, final List<String> initialList) {
            super(HybrisI18NBundleUtils.message(title), initialList);
            this.addTitle = addTitle;
            this.addText = addText;
            this.editTitle = editTitle;
            this.editText = editText;
        }

        public void setMyList(@Nullable java.util.List<String> itemList) {
            myListModel.clear();
            for (String itemToAdd : itemList) {
                super.addElement(itemToAdd);
            }
        }

        @Nullable
        @Override
        protected String findItemToAdd() {
            return showEditDialog(
                "",
                HybrisI18NBundleUtils.message(addTitle),
                HybrisI18NBundleUtils.message(addText)
            );
        }

        @Nullable
        @Override
        protected String editSelectedItem(@NotNull final String item) {
            return showEditDialog(
                item,
                HybrisI18NBundleUtils.message(editTitle),
                HybrisI18NBundleUtils.message(editText)
            );
        }

        @Nullable
        private String showEditDialog(
            @NotNull final String initialValue,
            @NotNull final String title,
            @NotNull final String message
        ) {
            return Messages.showInputDialog(
                this,
                message,
                title,
                Messages.getQuestionIcon(),
                initialValue,
                new InputValidatorEx() {

                    @Override
                    public boolean checkInput(@NotNull String inputString) {
                        return !StringUtil.isEmpty(inputString);
                    }

                    @Override
                    public boolean canClose(@NotNull String inputString) {
                        return !StringUtil.isEmpty(inputString) && (!myListModel.contains(inputString) || initialValue.equals(
                            inputString));
                    }

                    @Nullable
                    @Override
                    public String getErrorText(@NotNull String inputString) {
                        if (!checkInput(inputString)) {
                            return "directory name string cannot be empty";
                        }
                        if (!canClose(inputString)) {
                            return "duplicities are not allowed (nor make any sense)";
                        }
                        return null;
                    }
                }
            );
        }

        @NotNull
        public java.util.List<String> getMyList() {
            return Collections.list(myListModel.elements());
        }
    }
}
