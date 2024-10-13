package pasa.cbentley.core.swing.ctx;

import pasa.cbentley.core.j2se.ctx.IConfigJ2seCore;
import pasa.cbentley.core.src4.ctx.ConfigAbstract;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;

public class ConfigSwingCoreDef extends ConfigAbstract implements IConfigSwingCore, IConfigJ2seCore {

   public ConfigSwingCoreDef() {
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ConfigSwingCoreDef.class, 20);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigSwingCoreDef.class, 20);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
