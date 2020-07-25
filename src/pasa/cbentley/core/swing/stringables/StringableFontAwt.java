package pasa.cbentley.core.swing.stringables;

import java.awt.Font;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.swing.ctx.SwingCoreCtx;

//#mdebug
public class StringableFontAwt extends StringableAbstract implements IStringable {

   protected final Font font;

   public StringableFontAwt(SwingCoreCtx scc, Font font) {
      super(scc);
      this.font = font;
   }

   public void toString(Dctx dc) {
      scc.toSCD().d(font, dc);
   }

   public void toString1Line(Dctx dc) {
      scc.toSCD().d1(font, dc);
   }

}
//#enddebug
