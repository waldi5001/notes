package de.fk.notes;

import javax.swing.tree.DefaultMutableTreeNode;

import de.fk.notes.model.Folder;

public class TreeData {

   public static DefaultMutableTreeNode getTreeNode(Folder rootFolder) {
      var rootNode = toNode(rootFolder);
      addChildren(rootNode, rootFolder);
      return rootNode;
   }

   private static DefaultMutableTreeNode toNode(Folder folder) {
      return new UserObjectEqualMutableTreeNode(folder);
   }

   private static void addChildren(DefaultMutableTreeNode parent, Folder folder) {
      for (Folder child : folder.getChildren()) {
         var node = toNode(child);
         parent.add(node);
         addChildren(node, child);
      }
   }

   public static class UserObjectEqualMutableTreeNode extends DefaultMutableTreeNode {

      private static final long serialVersionUID = 1L;

      public UserObjectEqualMutableTreeNode(Object userObject) {
         super(userObject, true);
      }

      @Override
      public boolean equals(Object obj) {
         return this.getUserObject().equals(((UserObjectEqualMutableTreeNode) obj).getUserObject());
      }

      @Override
      public String toString() {
         return ((Folder) getUserObject()).getDescription();
      }
   }

}
