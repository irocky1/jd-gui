/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package org.jd.gui.service.treenode;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.jd.gui.api.API;
import org.jd.gui.api.feature.ContainerEntryGettable;
import org.jd.gui.api.feature.UriGettable;
import org.jd.gui.api.model.Container;
import org.jd.gui.view.data.TreeNodeBean;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class JspFileTreeNodeFactoryProvider extends TextFileTreeNodeFactoryProvider {
    protected static final ImageIcon ICON = new ImageIcon(HtmlFileTreeNodeFactoryProvider.class.getClassLoader().getResource("org/jd/gui/images/html_obj.gif"));

    @Override public String[] getSelectors() { return appendSelectors("*:file:*.jsp", "*:file:*.jspf"); }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends DefaultMutableTreeNode & ContainerEntryGettable & UriGettable> T make(API api, Container.Entry entry) {
        int lastSlashIndex = entry.getPath().lastIndexOf("/");
        String name = entry.getPath().substring(lastSlashIndex+1);
        return (T)new TreeNode(entry, new TreeNodeBean(name, "Location: " + entry.getUri().getPath(), ICON));
    }

    protected static class TreeNode extends TextFileTreeNodeFactoryProvider.TreeNode {
        public TreeNode(Container.Entry entry, Object userObject) { super(entry, userObject); }

        // --- PageCreator --- //
        @Override
        @SuppressWarnings("unchecked")
        public <T extends JComponent & UriGettable> T createPage(API api) {
            return (T)new TextFileTreeNodeFactoryProvider.Page(entry) {
                @Override public String getSyntaxStyle() { return SyntaxConstants.SYNTAX_STYLE_JSP; }
            };
        }
    }
}
