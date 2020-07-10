package me.tigahz.schematicbrush.brush;

import java.io.File;
import java.util.List;
import java.util.Random;

public class Brush {

   private String name;
   private List<File> files;

   public Brush(String name, List<File> files) {
      this.name = name;
      this.files = files;
   }

   public String getName() {
      return name;
   }

   public List<File> getFiles() {
      return files;
   }

   public File getRandom() {
      return files.get(new Random().nextInt(files.size()));
   }

}
