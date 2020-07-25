package pasa.cbentley.core.swing.stringables;

import javax.swing.JFrame;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.swing.ctx.SwingCoreCtx;

//#mdebug
public class StringableJFrame extends StringableAbstract implements IStringable {

   protected final JFrame ob;

   public StringableJFrame(SwingCoreCtx scc, JFrame ob) {
      super(scc);
      this.ob = ob;
   }

   public void toString(Dctx dc) {
      scc.toSCD().d(ob, dc);
   }

   public void toString1Line(Dctx dc) {
      scc.toSCD().d1(ob, dc);
   }

}
//#enddebug
