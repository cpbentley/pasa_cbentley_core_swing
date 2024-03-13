package pasa.cbentley.core.swing.tasks;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.swing.ctx.SwingCoreCtx;

public class TaskGroup extends TaskAbstract {

   protected final IntToObjects c;

   public TaskGroup(SwingCoreCtx scc) {
      super(scc);
      this.c = new IntToObjects(scc.getUC());

   }

   public void addTask(TaskAbstract task) {
      c.add(task);
   }
   
   public void run() {
      for (int index = 0; index < c.getLength(); index++) {
         TaskAbstract task = (TaskAbstract) c.getObjectAtIndex(index);
         task.run();
      }
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, TaskGroup.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nlLvl(c, "IntToObjects");
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, TaskGroup.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
   

}
