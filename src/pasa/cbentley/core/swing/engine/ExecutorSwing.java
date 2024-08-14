package pasa.cbentley.core.swing.engine;

import javax.swing.SwingUtilities;

import pasa.cbentley.core.j2se.engine.ExecutorJ2se;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.swing.ctx.SwingCoreCtx;

public class ExecutorSwing extends ExecutorJ2se {

   protected final SwingCoreCtx cusc;

   public ExecutorSwing(SwingCoreCtx scc) {
      super(scc);
      cusc = scc;
   }

   public void executeMainLater(Runnable run) {
      SwingUtilities.invokeLater(run);
   }

   public boolean isMainThread() {
      return SwingUtilities.isEventDispatchThread();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ExecutorSwing.class, 39);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ExecutorSwing.class, 39);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
