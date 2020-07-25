package pasa.cbentley.core.swing.tasks;

import java.awt.Component;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.swing.ctx.SwingCoreCtx;

public class TaskRepaint extends TaskAbstract {

   protected final Component c;

   public TaskRepaint(SwingCoreCtx scc, Component c) {
      super(scc);
      this.c = c;

   }

   public void run() {
      c.repaint();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, TaskRepaint.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, TaskRepaint.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
   

}
