package pasa.cbentley.core.swing.tasks;

import javax.swing.JFrame;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.swing.ctx.SwingCoreCtx;

public class TaskShowFrame extends TaskAbstract {

   protected final JFrame frame;

   public TaskShowFrame(SwingCoreCtx scc, JFrame frame) {
      super(scc);
      this.frame = frame;

   }

   public void run() {
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setVisible(true);
      
      //#debug
      toDLog().pFlow("Frame is Visible", scc.toStrGetJFrame(frame), TaskShowFrame.class, "run", LVL_05_FINE, true);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, TaskShowFrame.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, TaskShowFrame.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
