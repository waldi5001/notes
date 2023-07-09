package de.fk.notes;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.ArrayList;
import java.util.List;

public class JTableTransferHandler extends TransferHandler {

   private static final long serialVersionUID = 1L;

   @Override
   public int getSourceActions(JComponent c) {
      return MOVE;
   }

   DataFlavor tableFlavor;
   final DataFlavor[] flavors;

   public JTableTransferHandler() {
      tableFlavor = createDataFlavor(Object.class);
      flavors = new DataFlavor[]{tableFlavor};
   }

   @Override
   public boolean canImport(TransferSupport support) {
      return false;
   }

   @Override
   protected Transferable createTransferable(JComponent c) {
      JTable table = (JTable) c;
      int[] selectedRows = table.getSelectedRows();
      if (selectedRows.length > 0) {
         JTableBuilder.TableModel model = (JTableBuilder.TableModel) table.getModel();
         List<Object> selectedObjects = new ArrayList<>();
         for (int selectedRow : selectedRows) {
            selectedObjects.add(model.getData().get(selectedRow));
         }
         return new JTableTransferabel(selectedObjects);
      }
      return null;
   }

   @Override
   public boolean importData(TransferSupport support) {
      return false;
   }

   @Override
   public String toString() {
      return getClass().getName();
   }

   public class JTableTransferabel implements Transferable {

      List<Object> objects;

      public JTableTransferabel(List<Object> objects) {
         this.objects = objects;
      }

      @Override
      public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
         if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
         }
         return objects;
      }

      @Override
      public DataFlavor[] getTransferDataFlavors() {
         return flavors;
      }

      @Override
      public boolean isDataFlavorSupported(DataFlavor flavor) {
         return tableFlavor.equals(flavor);
      }
   }

   private DataFlavor createDataFlavor(Class<?> clazz) {
      String mimeType = String.format("%s;class=\"%s\"", DataFlavor.javaJVMLocalObjectMimeType, clazz.getName());
      try {
         return new DataFlavor(mimeType);
      } catch (ClassNotFoundException e) {
         throw new RuntimeException(e.getMessage(), e);
      }
   }
}
