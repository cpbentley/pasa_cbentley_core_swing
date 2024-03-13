package pasa.cbentley.core.swing.tasks;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.swing.ctx.SwingCoreCtx;

/**
 * {@link ActionListener} redirect to {@link Runnable#run()}
 * @author Charles Bentley
 *
 */
public abstract class TaskAbstract implements IStringable, Runnable, ActionListener {

   protected final SwingCoreCtx scc;

   public TaskAbstract(SwingCoreCtx scc) {
      this.scc = scc;
   }

   public void actionPerformed(ActionEvent e) {
      this.run();
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, TaskAbstract.class, "@line5");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, TaskAbstract.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return scc.getUC();
   }

   //#enddebug

}
