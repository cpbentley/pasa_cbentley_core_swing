package pasa.cbentley.core.swing.stringables;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.swing.ctx.SwingCoreCtx;

public abstract class StringableAbstract implements IStringable {
   protected final SwingCoreCtx scc;

   public StringableAbstract(SwingCoreCtx scc) {
      this.scc = scc;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, StringableAbstract.class, "@line5");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, StringableAbstract.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return scc.getUC();
   }

   //#enddebug

}
