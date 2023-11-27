package pasa.cbentley.core.swing.ctx;

import java.awt.EventQueue;

import javax.swing.JFrame;

import pasa.cbentley.core.j2se.ctx.J2seCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src5.ctx.C5Ctx;
//#debug
import pasa.cbentley.core.swing.stringables.StringableJFrame;
import pasa.cbentley.core.swing.stringables.SwingCoreDebug;

/**
 * Provides core service of Swing
 * 
 * 
 * @author Charles Bentley
 *
 */
public class SwingCoreCtx extends J2seCtx {

   private static final int CTX_ID = 348;

   private Thread           edt;

   //#debug
   private SwingCoreDebug   scd;

   public SwingCoreCtx(C5Ctx c5) {
      super(c5);

      //#debug
      scd = new SwingCoreDebug(this);
   }

   public int getCtxID() {
      return CTX_ID;
   }

   /**
    * Call this from outside the AWT thread.
    * @param run
    */
   public void edtJoin(Runnable run) {
      EventQueue.invokeLater(run);

      try {
         //invoke this method and wait until it finishes in the EDT thread.
         EventQueue.invokeAndWait(new Runnable() {

            public void run() {
               //save the EDT reference
               edt = Thread.currentThread();
            }
         });

         //this is the key. wait for the EDT to die. which will happen when the user will close the Frames.
         if (edt != null) {
            edt.join();
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   //#mdebug
   public SwingCoreDebug toSCD() {
      return scd;
   }

   public StringableJFrame toStrGetJFrame(JFrame f) {
      return new StringableJFrame(this, f);
   }

   public void toString(Dctx dc) {
      dc.root(this, SwingCoreCtx.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, SwingCoreCtx.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
