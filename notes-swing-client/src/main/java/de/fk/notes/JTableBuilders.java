package de.fk.notes;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;
import static java.time.format.FormatStyle.SHORT;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import de.fk.notes.model.Note;
import de.fk.notes.model.Todo;

public class JTableBuilders {

   public static final int CREATED_AT_WIDTH = 100;
   public static final int TODO_WIDTH = 50;

   public static JTableBuilder<Note> searchResultTable() {
      return JTableBuilder.get(Note.class, result -> {
            var s = new ArrayList<Note>();
            var t1 = new Note();
//            t1.setCreatedAt(OffsetDateTime.now());
            t1.setDescription("A Note");
            s.add(t1);
            var t2 = new Note();
//            t2.setCreatedAt(OffsetDateTime.now());
            t2.setDescription("Another Note");
            s.add(t2);
            result.get(s);
         })//
         .addColumn(JTableBuilder.ColumnBuilder.get(OffsetDateTime.class).add("CreatedAt").width(CREATED_AT_WIDTH).build())//
         .addColumn(JTableBuilder.ColumnBuilder.get(String.class).add("description").build())//
         .get((s, index) -> {
            if (index == 0) {
               return s.getCreatedAt().toLocalDateTime().format(ofLocalizedDateTime(SHORT));
            } else if (index == 1) {
               return s.getDescription();
            }
            return null;
         })//
         .save((toSave, asyncCallback) -> {
            asyncCallback.get(null);
         })//
         .autoRowSorter();
   }

   public static JTableBuilder<Todo> todoTable() {
      return JTableBuilder.get(Todo.class, result -> {
            var s = new ArrayList<Todo>();
            var t1 = new Todo();
//            t1.setCreatedAt(OffsetDateTime.now());
            t1.setDone(true);
            t1.setDescription("Do That");
            t1.setDoneAt(OffsetDateTime.now());
            s.add(t1);
            var t2 = new Todo();
//            t2.setCreatedAt(OffsetDateTime.now());
            t2.setDone(false);
            t2.setDescription("Milk");
            s.add(t2);
            result.get(s);
         })//
         .addColumn(JTableBuilder.ColumnBuilder.get(Boolean.class).add("done").width(TODO_WIDTH).build())//
         .addColumn(JTableBuilder.ColumnBuilder.get(OffsetDateTime.class).add("CreatedAt").width(CREATED_AT_WIDTH).build())//
         .addColumn(JTableBuilder.ColumnBuilder.get(OffsetDateTime.class).add("DoneAt").width(CREATED_AT_WIDTH).build())//
         .addColumn(JTableBuilder.ColumnBuilder.get(String.class).add("description").build())//
         .set((s, val, index) -> {
            if (index == 0) {
               s.setDone((Boolean) val);
            } else if (index == 3) {
               s.setDescription((String) val);
            }
         })//
         .get((s, index) -> {
            if (index == 0) {
               return s.isDone();
            } else if (index == 1) {
               return s.getCreatedAt().toLocalDateTime().format(ofLocalizedDateTime(SHORT));
            } else if (index == 2) {
               return s.getDoneAt() != null ? s.getDoneAt().toLocalDateTime().format(ofLocalizedDateTime(SHORT)) : null;
            } else if (index == 3) {
               return s.getDescription();
            }
            return null;
         })//
         .save((toSave, asyncCallback) -> {
            asyncCallback.get(null);
         })//
         .autoRowSorter();
   }

   public static JTableBuilder<Note> treeDetailTable() {
      return JTableBuilder.get(Note.class, result -> {
            var s = new ArrayList<Note>();
            var t1 = new Note();
//            t1.setCreatedAt(OffsetDateTime.now());
            t1.setDescription("A Note");
            s.add(t1);
            var t2 = new Note();
//            t2.setCreatedAt(OffsetDateTime.now());
            t2.setDescription("Another Note");
            s.add(t2);
            result.get(s);
         })//
         .addColumn(JTableBuilder.ColumnBuilder.get(OffsetDateTime.class).add("CreatedAt").width(CREATED_AT_WIDTH).build())//
         .addColumn(JTableBuilder.ColumnBuilder.get(String.class).add("description").build())//
         .get((s, index) -> {
            if (index == 0) {
               return s.getCreatedAt().toLocalDateTime().format(ofLocalizedDateTime(SHORT));
            } else if (index == 1) {
               return s.getDescription();
            }
            return null;
         })//
         .save((toSave, asyncCallback) -> {
            asyncCallback.get(null);
         })//
         .enableDrag()//
         .autoRowSorter();
   }
}
