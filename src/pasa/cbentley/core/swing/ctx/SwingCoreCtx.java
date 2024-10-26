package pasa.cbentley.core.swing.ctx;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import pasa.cbentley.core.j2se.ctx.J2seCoreCtx;
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
public class SwingCoreCtx extends J2seCoreCtx {

   private static final int CTX_ID = 101;

   private Thread           edt;

   //#debug
   private SwingCoreDebug   scd;

   public SwingCoreCtx(C5Ctx c5) {
      this(null,c5);
   }
   public SwingCoreCtx(IConfigSwingCore config, C5Ctx c5) {
      super((config == null) ? new ConfigSwingCoreDef() : config, c5);

      //#debug
      scd = new SwingCoreDebug(this);
      
      //#debug
      toDLog().pCreate("", this, SwingCoreCtx.class, "Created@43", LVL_05_FINE, true);

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

   public int getCtxID() {
      return CTX_ID;
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

   public boolean toString(Dctx dc, Object o) {
      if(o instanceof KeyEvent) {
         
      }
      if (o instanceof ComponentEvent) {
         toStringComponentEvent(dc, (ComponentEvent) o);
         return true;
      }
      return false;
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, SwingCoreCtx.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   public boolean toString1Line(Dctx dc, Object o) {
      if (o instanceof ComponentEvent) {
         toStringComponentEvent1Line(dc, (ComponentEvent) o);
         return true;
      }
      return false;
   }

   private void toStringComponentEvent(Dctx dc, ComponentEvent o) {
      dc.root(o, ComponentEvent.class, SwingCoreCtx.class, 57);

      Component co = o.getComponent();
      dc.nl();

   }

   private void toStringComponentEvent1Line(Dctx dc, ComponentEvent o) {
      dc.root1Line(o, ComponentEvent.class, SwingCoreCtx.class, 57);

      Component co = o.getComponent();

      dc.append("[");
      dc.append(co.getX());
      dc.append(",");
      dc.append(co.getY());
      dc.append(" - ");
      dc.append(co.getWidth());
      dc.append(",");
      dc.append(co.getHeight());
      dc.append("]");

   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
