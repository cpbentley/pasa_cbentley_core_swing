package pasa.cbentley.core.swing.ctx;

import java.awt.EventQueue;

import javax.swing.JFrame;

import pasa.cbentley.core.j2se.ctx.J2seCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src5.ctx.C5Ctx;
import pasa.cbentley.core.swing.stringables.StringableJFrame;
import pasa.cbentley.core.swing.stringables.SwingCoreDebug;

public class SwingCoreCtx extends J2seCtx {

   private SwingCoreDebug scd;

   public SwingCoreCtx(C5Ctx c5) {
      super(c5);

      //#debug
      scd = new SwingCoreDebug(this);
   }

   public SwingCoreDebug toSCD() {
      return scd;
   }

   private Thread edt;


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
   public void toString(Dctx dc) {
      dc.root(this, SwingCoreCtx.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, SwingCoreCtx.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   public StringableJFrame toStrGetJFrame(JFrame f) {
      return new StringableJFrame(this, f);
   }
   //#enddebug

}
