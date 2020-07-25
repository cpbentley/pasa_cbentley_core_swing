package pasa.cbentley.core.swing.ctx;

import pasa.cbentley.core.src4.ctx.IFlagsToString;

public interface IFlagsToStringSwingCore extends IFlagsToString {

   /**
    * 
    */
   public static final int TOSTRING_FLAG_3_IGNORE_FONT_ATTRIBUTES = 1 << 2;
   /**
    * When debugging fonts, we want the complete font setup
    */
   public static final int TOSTRING_FLAG_4_SHOW_FONT_ENVIRONEMT = 1 << 3;
}
