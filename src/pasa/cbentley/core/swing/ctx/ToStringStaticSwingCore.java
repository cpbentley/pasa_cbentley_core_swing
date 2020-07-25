package pasa.cbentley.core.swing.ctx;

import java.awt.Frame;
import java.awt.event.MouseEvent;

public class ToStringStaticSwingCore {

   public static String toStringMouseEventID(MouseEvent e) {
      int id = e.getID();
      switch (id) {
         case MouseEvent.MOUSE_CLICKED:
            return "Clicked";
         case MouseEvent.MOUSE_DRAGGED:
            return "Dragged";
         case MouseEvent.MOUSE_ENTERED:
            return "Entered";
         case MouseEvent.MOUSE_EXITED:
            return "Exited";
         case MouseEvent.MOUSE_MOVED:
            return "Moved";
         case MouseEvent.MOUSE_PRESSED:
            return "Pressed";
         case MouseEvent.MOUSE_RELEASED:
            return "Released";
         case MouseEvent.MOUSE_WHEEL:
            return "Wheel";
         default:
            return "UnknownEvent" + id;
      }
   }

   public static String toStringStateWindow(int state) {
      switch (state) {
         case Frame.NORMAL:
            return "Normal";
         case Frame.MAXIMIZED_VERT:
            return "MaxVerti";
         case Frame.MAXIMIZED_HORIZ:
            return "MaxHoriz";
         case Frame.ICONIFIED:
            return "Iconified";
         case Frame.MAXIMIZED_BOTH:
            return "MaxBoth";
         default:
            return "UnknownWindowState" + state;
      }
   }
}
