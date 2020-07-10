package me.tigahz.schematicbrush.brush;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import me.tigahz.schematicbrush.SchematicBrush;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BrushUser {

   private final UUID uuid;
   private boolean isUsingPublic;
   private String publicBrush = "";
   private LocalSession session;
   private List<EditSession> sessions;
   private EditSession editSession;
   private boolean jobInit;
   private int job;
   private boolean randomRotation;

   public BrushUser(UUID uuid) {
      this.uuid = uuid;
      this.isUsingPublic = false;
      this.session = new LocalSession();
      this.sessions = new ArrayList<>();
      this.jobInit = false;
      this.randomRotation = false;
   }

   public UUID getUniqueId() {
      return uuid;
   }

   public void setBrushToPublic(String publicBrush) {
      this.isUsingPublic = true;
      this.publicBrush = publicBrush;
   }

   public void setBrushToPersonal() {
      this.isUsingPublic = false;
   }

   public boolean isUsingPublic() {
      return isUsingPublic;
   }

   public PublicBrush getPublicBrush(SchematicBrush plugin) {
      return new PublicBrush(plugin, publicBrush);
   }

   public LocalSession getLocalSession() {
      return session;
   }

   public boolean undo() {
      try {
         sessions.get(job - 1).undo(sessions.get(job - 1));
         job += -1;
         return true;
      } catch (ArrayIndexOutOfBoundsException e) {
         return false;
      }
   }

   public boolean redo() {
      try {
         sessions.get(job).redo(sessions.get(job));
         job += 1;
         return true;
      } catch (IndexOutOfBoundsException e) {
         return false;
      }
   }

   public void updateJobCount() {
      job = sessions.size();
   }

   public boolean hasJobCountInit() {
      return jobInit;
   }

   public void setJobCountInit(boolean jobInit) {
      this.jobInit = jobInit;
   }

   public int getJobCount() {
      return job;
   }

   public void setJobCount(int i) {
      job = i;
   }

   public EditSession getEditSession() {
      return editSession;
   }

   public List<EditSession> getSessions() {
      return sessions;
   }

   public boolean getRandomRotation() {
      return randomRotation;
   }

   public void setRandomRotation(boolean randomRotation) {
      this.randomRotation = randomRotation;
   }

}
