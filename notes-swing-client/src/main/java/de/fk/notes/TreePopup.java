package de.fk.notes;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class TreePopup extends JPopupMenu {

   private static final long serialVersionUID = 1L;

   public TreePopup() {
      add(new JMenuItem("Neuer Ordner"));
      add(new JMenuItem("Ordner löschen"));
   }

}
