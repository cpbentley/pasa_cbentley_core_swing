package pasa.cbentley.core.swing.stringables;

import java.awt.BufferCapabilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.MenuElement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.text.BadLocationException;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.swing.ctx.IFlagsToStringSwingCore;
import pasa.cbentley.core.swing.ctx.SwingCoreCtx;
import pasa.cbentley.core.swing.ctx.ToStringStaticSwingCore;

/**
 * Object parameters must never be null.
 * <br>
 * <br>
 * Must be checked before calling using {@link Dctx#nlLvlO(Object, String, pasa.cbentley.core.src4.ctx.ICtx)}
 * <br>
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public class SwingCoreDebug {

   //#mdebug

   private SwingCoreCtx scc;

   public SwingCoreDebug(SwingCoreCtx scc) {
      this.scc = scc;
   }

   public void d(AbstractButton c, Dctx dc) {
      dc.root(c, "AbstractButton");
      dc.appendVarWithSpace("text", c.getText());
      dc.nl();
      dc.appendVarWithSpace("isSelected", c.isSelected());
      dc.appendVarWithSpace("isBorderPainted", c.isBorderPainted());
      dc.appendVarWithSpace("isContentAreaFilled", c.isContentAreaFilled());
      dc.appendVarWithSpace("isFocusPainted", c.isFocusPainted());
      dc.appendVarWithSpace("isRolloverEnabled", c.isRolloverEnabled());

      this.d(c.getIcon(), dc, "Icon");
      this.d(c.getDisabledIcon(), dc, "disabledIcon");

      this.d((JButton) c, dc.newLevel());
   }

   public String d(ActionEvent e) {
      Dctx dc = new Dctx(getUC());
      d(e, dc);
      return dc.toString();
   }

   public void d(ActionEvent e, Dctx dc) {
      dc.root1Line(e, "ActionEvent");
      dc.append(" ActionCommand=" + e.getActionCommand());
      dc.append(" ID=" + e.getID());
      dc.append(" Source = " + e.getSource().getClass().getSimpleName());
   }

   public void d(BufferCapabilities bc, Dctx dc) {
      dc.root(bc, "BufferCapabilities");
      dc.nl();
      dc.appendVarWithSpace("isPageFlipping", bc.isPageFlipping());
      dc.appendVarWithSpace("isMultiBufferAvailable", bc.isMultiBufferAvailable());
      dc.appendVarWithSpace("isFullScreenRequired", bc.isFullScreenRequired());

   }

   public void d(Component c, Dctx dc) {
      dc.root(c, "Component");
      dc.appendVarWithSpace("Name", c.getName());
      dc.appendVarWithSpace("isVisible", c.isVisible());
      dc.appendVarWithSpace("isEnabled", c.isEnabled());
      dc.appendVarWithSpace("isFocusable", c.isFocusable());
      dc.appendVarWithSpace("isDisplayable", c.isDisplayable());
      dc.appendVarWithSpace("isOpaque", c.isOpaque());
      dc.appendVarWithSpace("isShowing", c.isShowing());
      dc.appendVarWithSpace("isCursorSet", c.isCursorSet());
   }

   public void d(Container c, Dctx dc) {
      dc.root(c, "Container");
      int numComps = c.getComponentCount();
      dc.appendVarWithSpace("ComponentCount", numComps);
      this.d((Component) c, dc.newLevel());
      for (int i = 0; i < numComps; i++) {
         Component child = c.getComponent(i);
         if (child instanceof IStringable) {
            dc.nlLvl((IStringable) child);
         } else if (child instanceof JComponent) {
            this.dFind((JComponent) child, dc.newLevelTab());
         }
      }
   }

   public void d(DefaultComboBoxModel model, Dctx dc) {
      dc.root(model, "DefaultComboBoxModel");
      dc.appendVarWithSpace("size", model.getSize());
      dc.appendVarWithSpace("selectedObject", model.getSelectedItem());
      for (int i = 0; i < model.getSize(); i++) {
         dc.nl();
         dc.appendVar(i + "", model.getElementAt(i));
      }
   }

   public void d(Font f, Dctx dc) {
      dc.root(f, Font.class);
      dc.nl();
      dc.appendVarWithSpace("getName", f.getName());
      dc.appendVarWithSpace("getFontName", f.getFontName());
      dc.appendVarWithSpace("getFamily", f.getFamily());
      dc.appendVarWithSpace("PostScriptName", f.getPSName());

      dc.nl();
      dc.appendVarWithSpace("Size", f.getSize());
      dc.appendVarWithSpace("Style", f.getStyle());
      dc.appendVarWithSpace("NumGlyphs", f.getNumGlyphs());
      dc.appendVarWithSpace("ItalicAngle", f.getItalicAngle());

      dc.nl();
      if (dc.hasFlagToString(scc, IFlagsToStringSwingCore.TOSTRING_FLAG_3_SHOW_FONT_ATTRIBUTES)) {
         Attribute[] availableAttributes = f.getAvailableAttributes();
         dc.appendVar("AvailableAttributes", availableAttributes.length);
         for (int i = 0; i < availableAttributes.length; i++) {
            dc.nl();
            Attribute at = availableAttributes[i];
            if (at instanceof TextAttribute) {
               TextAttribute tat = (TextAttribute) at;
               dc.appendVarWithSpace("TextAttribute toString", tat.toString());

            } else {
               dc.appendVarWithSpace("Attribute toString", at.toString());
            }
         }
      }
      if (dc.hasFlagToString(scc, IFlagsToStringSwingCore.TOSTRING_FLAG_4_SHOW_FONT_ENVIRONEMT)) {
         dFontsAll(dc.newLevel());
      }
   }

   public void d(Frame c, Dctx dc) {
      dc.root(c, "Frame");
      dc.appendVarWithSpace("Title", c.getTitle());
      dc.appendVarWithSpace("getExtendedState", c.getExtendedState());
      d((Window) c, dc.newLevel());
   }

   public String d(Graphics2D g) {
      Dctx dc = new Dctx(getUC());
      d(g, dc);
      return dc.toString();
   }

   public void d(Graphics2D g, Dctx dc) {
      dc.root(g, "Graphics2D");
      dc.appendVarWithSpace("Background", g.getBackground().toString());
      dc.appendVarWithSpace("Color", g.getColor().toString());
      dc.nl();
      Rectangle clipBounds = g.getClipBounds();
      if (clipBounds == null) {
         dc.append("ClibBounds is null");
      } else {
         dc.append("ClibBounds=" + clipBounds.x + "," + clipBounds.y + " w=" + clipBounds.width + "," + clipBounds.height);
      }
      dc.nl();
      Font font = g.getFont();
      if (font == null) {
         dc.append("font is null");
      } else {
         this.d(font, dc);
      }
      dc.nl();
      RenderingHints renderingHints = g.getRenderingHints();
      d(renderingHints, dc);

      dc.nl();
      GraphicsConfiguration deviceConfiguration = g.getDeviceConfiguration();
      d(deviceConfiguration, dc);
   }

   public void d(GraphicsConfiguration gc, Dctx dc) {
      dc.root(gc, "GraphicsConfiguration");
      if (gc == null) {
         dc.append(" = null");
         return;
      }
      Rectangle bounds = gc.getBounds();
      dc.append("bounds=" + bounds.x + "," + bounds.y + " w=" + bounds.width + "," + bounds.height);
      dc.nl();
      dc.appendVarWithSpace("ColorModel", gc.getColorModel());
      dc.nl();
      d(gc.getBufferCapabilities(), dc);

   }

   public void d(Icon c, Dctx dc) {
      dc.root(c, "Icon");
      dc.appendVarWithSpace("w", c.getIconWidth());
      dc.appendVarWithSpace("h", c.getIconHeight());
   }

   public void d(Icon c, Dctx dc, String title) {
      if (c == null) {
         dc.append(title + "is  null");
      } else {
         dc.root(c, "Icon");
         dc.appendVarWithSpace("title", title);
         dc.appendVarWithSpace("w", c.getIconWidth());
         dc.appendVarWithSpace("h", c.getIconHeight());
      }
   }

   public void d(JButton c, Dctx dc) {
      dc.root(c, "JButton");
      dc.appendVarWithSpace("isDefaultButton", c.isDefaultButton());
      dc.appendVarWithSpace("isDefaultCapable", c.isDefaultCapable());
      this.d((JComponent) c, dc.newLevel());
   }

   public void d(JComponent c, Dctx dc) {
      dc.root(c, "JComponent");
      dc.appendVarWithSpace("did", c.getName());
      dc.appendVarWithSpace("x", c.getX());
      dc.appendVarWithSpace("y", c.getY());
      dc.appendVarWithSpace("w", c.getWidth());
      dc.appendVarWithSpace("h", c.getHeight());
      dc.appendVarWithSpace("pw", c.getPreferredSize().getWidth());
      dc.appendVarWithSpace("ph", c.getPreferredSize().getHeight());
      dc.nl();
      dc.append("Located at " + c.getLocation().x + "," + c.getLocation().y);
      dc.append(" ");
      dc.append("Size is " + c.getWidth() + "," + c.getHeight());
      dc.nl();
      JPopupMenu popMenu = c.getComponentPopupMenu();
      if (popMenu == null) {
         dc.append("Component PopupMenu is null");
      } else {
         if (popMenu instanceof IStringable) {
            dc.nlLvl("Component PopupMenu", (IStringable) popMenu);
         } else {
            this.d(popMenu, dc);
         }
      }

      this.d((Container) c, dc.newLevel());
   }

   public void d(JFrame c, Dctx dc) {
      dc.root(c, "JFrame");
      d((Frame) c, dc.newLevel());
   }

   public void d(JLabel c, Dctx dc) {
      dc.root(c, "JLabel");
      dc.appendVarWithSpace("text", c.getText());
      this.d((JComponent) c, dc.newLevel());
   }

   public void d(JLayeredPane c, Dctx dc) {
      dc.root(c, "JLayeredPane");
      dc.appendVarWithSpace("lowestLayer", c.lowestLayer());
      dc.appendVarWithSpace("highestLayer", c.highestLayer());
      this.d((JComponent) c, dc.newLevel());
   }

   public void d(JPanel panel, Dctx dc) {
      dc.root(panel, "JPanel");
      this.d((JComponent) panel, dc.newLevel());
   }

   public void d(JPopupMenu e, Dctx dc) {
      dc.root1Line(e, "JPopupMenu");
      MenuElement[] menuElements = e.getSubElements();
      dc.appendVarWithSpace(" #elements", menuElements.length);
      for (int i = 0; i < menuElements.length; i++) {
         MenuElement menuElement = menuElements[i];
         this.d(menuElement, dc);
      }
   }

   public void d(JRootPane c, Dctx dc) {
      dc.root(c, "JRootPane");
      dc.appendVarWithSpace("OptimizedDrawingEnabled", c.isOptimizedDrawingEnabled());
      this.d((JComponent) c, dc.newLevel());
   }

   public void d(JScrollBar c, Dctx dc) {
      dc.root(c, "JTextPane");
      this.d((JComponent) c, dc.newLevel());
   }

   public void d(JScrollPane c, Dctx dc) {
      dc.root(c, "JScrollPane");

      JViewport viewport = c.getViewport();
      Component view = viewport.getView();

      //we want a quick view of what
      dc.appendVarWithSpace("of ", view.getClass().getSimpleName() + " named " + view.getName());

      this.d((JComponent) c, dc.newLevel());
      this.d((JViewport) viewport, dc.newLevel());
   }

   public String d(JTabbedPane t) {
      Dctx dc = new Dctx(getUC());
      d(t, dc);
      return dc.toString();
   }

   public void d(JTabbedPane e, Dctx dc) {
      dc.root(e, "JTabbedPane");
      dc.appendVarWithSpace("selectedIndex", e.getSelectedIndex());
      dc.appendVarWithSpace("tabCount", e.getTabCount());
      dc.appendVarWithSpace("tabRunCount", e.getTabRunCount());
      dc.appendVarWithSpace("tabPlacement", e.getTabPlacement());
      dc.appendVarWithSpace("tabLayoutPolicy", e.getTabLayoutPolicy());
      Component sel = e.getSelectedComponent();
      dc.nlLvlO(sel, "TabbedPaneSelectedComponent", scc);
      dc.nl();
      this.d((JComponent) e, dc);
   }

   public void d(JTextPane c, Dctx dc) {
      dc.root(c, "JTextPane");
      this.d((JComponent) c, dc.newLevel());
   }

   public void d(JViewport c, Dctx dc) {
      dc.root(c, "JViewport");
      this.d((JComponent) c, dc.newLevel());

      Rectangle viewRect = c.getViewRect();
      dc.append("ViewRect");
      dc.appendVarWithSpace("x", viewRect.getX());
      dc.appendVarWithSpace("y", viewRect.getY());
      dc.appendVarWithSpace("w", viewRect.getWidth());
      dc.appendVarWithSpace("h", viewRect.getHeight());

      Component view = c.getView();
      dFind(view, dc);
   }

   public String d(KeyEvent e) {
      Dctx dc = new Dctx(getUC());
      d(e, dc);
      return dc.toString();
   }

   public void d(KeyEvent e, Dctx dc) {
      dc.root1Line(e, "KeyEvent");
      dc.appendVarWithSpace("code", e.getKeyCode());
      dc.appendVarWithSpace("char", e.getKeyChar());
      dc.appendVarWithSpace("txt", KeyEvent.getKeyText(e.getKeyCode()));
   }

   public void d(MenuElement e, Dctx dc) {
      dc.root(e, "MenuElement");
   }

   public String d(MouseEvent e) {
      Dctx dc = new Dctx(getUC());
      d(e, dc);
      return dc.toString();
   }

   public void d(MouseEvent e, Dctx dc) {
      dc.root(e, "MouseEvent");
   }

   public String d(PropertyChangeEvent e) {
      Dctx dc = new Dctx(getUC());
      d(e, dc);
      return dc.toString();
   }

   public void d(PropertyChangeEvent e, Dctx dc) {
      dc.root(e, "PropertyChangeEvent");
      dc.appendWithSpace(e.getPropertyName());
      dc.appendVarWithSpace("id", e.getPropagationId());
      dc.appendVarWithSpace("New", e.getNewValue());
      dc.appendVarWithSpace("Old", e.getOldValue());
      dc.sameLineO1(e.getSource(), "Source", scc);
   }

   public void d(RenderingHints r, Dctx dc) {
      dc.root(r, "RenderingHints");

      dc.nl();
      Object key = RenderingHints.KEY_ANTIALIASING;
      String var = "Anti Alias Geo";
      boolean b = r.containsKey(key);
      if (b) {
         Object object = r.get(key);
         if (object == RenderingHints.VALUE_ANTIALIAS_DEFAULT) {
            dc.appendVar(var, "default");
         } else if (object == RenderingHints.VALUE_ANTIALIAS_OFF) {
            dc.appendVar(var, "off");
         } else if (object == RenderingHints.VALUE_ANTIALIAS_ON) {
            dc.appendVar(var, "on");
         } else {
            dc.appendVar(var, "error value?" + object.toString());
         }
      } else {
         dc.appendVar("Key not set for", var);
      }
      dc.nl();
      key = RenderingHints.KEY_TEXT_ANTIALIASING;
      var = "Anti Alias Text";
      b = r.containsKey(key);
      if (b) {
         Object object = r.get(key);
         if (object == RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT) {
            dc.appendVar(var, "default");
         } else if (object == RenderingHints.VALUE_TEXT_ANTIALIAS_OFF) {
            dc.appendVar(var, "off");
         } else if (object == RenderingHints.VALUE_TEXT_ANTIALIAS_ON) {
            dc.appendVar(var, "on");
         } else {
            dc.appendVar(var, "error value?" + object.toString());
         }
      } else {
         dc.appendVar("Key not set for", var);
      }
   }

   public void d(Window c, Dctx dc) {
      dc.root(c, "Window");
      dc.appendVarWithSpace("isActive", c.isActive());
      dc.appendVarWithSpace("isAutoRequestFocus", c.isAutoRequestFocus());
      dc.appendVarWithSpace("isCursorSet", c.isCursorSet());
      dc.appendVarWithSpace("isAlwaysOnTop", c.isAlwaysOnTop());
      d((Container) c, dc.newLevel());
   }

   public String d(WindowEvent e) {
      Dctx dc = new Dctx(getUC());
      d(e, dc);
      return dc.toString();
   }

   public void d(WindowEvent e, Dctx dc) {
      dc.root(e, "WindowEvent");
      dc.appendVarWithSpace("oldState", ToStringStaticSwingCore.toStringStateWindow(e.getOldState()));
      dc.appendVarWithSpace("newState", ToStringStaticSwingCore.toStringStateWindow(e.getNewState()));
      dc.nlLvlO(e.getWindow(), "Window", scc);
   }

   public void d1(AbstractButton c, Dctx dc) {
      dc.root(c, "AbstractButton");
      this.d1((JButton) c, dc.newLevel1Line());
   }

   public String d1(ActionEvent e) {
      Dctx dc = new Dctx(getUC());
      d1(e, dc);
      return dc.toString();
   }

   public void d1(ActionEvent e, Dctx dc) {
      dc.root1Line(e, "ActionEvent");
      dc.append(" ActionCommand=" + e.getActionCommand());
      dc.append(" ID=" + e.getID());
      dc.append(" Source = " + e.getSource().getClass().getSimpleName());
   }

   public void d1(BufferedImage image, Dctx dc) {
      dc.root1Line(image, "BufferedImage");
      dc.append(' ');
      dc.append(image.getWidth());
      dc.append(':');
      dc.append(image.getHeight());

   }

   public String d1(ChangeEvent e) {
      Dctx dc = new Dctx(getUC());
      d1(e, dc);
      return dc.toString();
   }

   public void d1(ChangeEvent e, Dctx dc) {
      dc.root1Line(e, "ChangeEvent");
      dc.sameLineO1(e.getSource(), "Source", scc);
   }

   public String d1(Color color) {
      Dctx dc = new Dctx(getUC());
      d1(color, dc);
      return dc.toString();
   }

   public String d1(Color color, Dctx dc) {
      dc.root1Line(color, "Color");
      if (color == null) {
         dc.append(" is null");
      } else {
         dc.appendVarWithSpace("r", color.getRed());
         dc.appendVarWithSpace("g", color.getGreen());
         dc.appendVarWithSpace("b", color.getBlue());
         dc.appendVarWithSpace("a", color.getAlpha());
      }
      return dc.toString();
   }

   public String d1(Component c) {
      Dctx dc = new Dctx(getUC());
      d1(c, dc);
      return dc.toString();
   }

   public void d1(Component c, Dctx dc) {
      dc.root(c, "Component");
      dc.appendVarWithSpace("Name", c.getName());
      dc.append(" ");
      d1(c.getBounds());
   }

   public String d1(DocumentEvent e) {
      Dctx dc = new Dctx(getUC());
      d1(e, dc);
      return dc.toString();
   }

   public void d1(DocumentEvent e, Dctx dc) {
      dc.root1Line(e, "DocumentEvent");
      dc.appendVarWithSpace("offset", e.getOffset());
      dc.appendVarWithSpace("len", e.getLength());
      String data = "";
      try {
         data = e.getDocument().getText(e.getOffset(), e.getLength());
      } catch (BadLocationException e1) {
         data = "BadLocationException";
      }
      dc.appendVarWithSpace("text", data);
   }

   public void d1(Font f, Dctx dc) {
      dc.root(f, "Font");
      dc.appendVarWithSpace("name", f.getName());
      dc.appendVarWithSpace("fontName", f.getFontName());
      dc.appendVarWithSpace("Family", f.getFamily());
      dc.appendVarWithSpace("Size", f.getSize());
      dc.appendVarWithSpace("Style", f.getStyle());
      dc.appendVarWithSpace("NumGlyphs", f.getNumGlyphs());
   }

   public String d1(ItemEvent e) {
      Dctx dc = new Dctx(getUC());
      d1(e, dc);
      return dc.toString();
   }

   public void d1(ItemEvent e, Dctx dc) {
      dc.root1Line(e, "ItemEvent");
      dc.sameLineO1(e.getSource(), "Source", scc);
   }

   public void d1(JButton c, Dctx dc) {
      dc.root1Line(c, "JButton");
      dc.appendVarWithSpace("text", c.getText());
      this.d1((JComponent) c, dc.newLevel1Line());
   }

   public void d1(JComponent c, Dctx dc) {
      dc.root(c, "JComponent");
      dc.appendVarWithSpace("x", c.getX());
      dc.appendVarWithSpace("y", c.getY());
      dc.appendVarWithSpace("w", c.getWidth());
      dc.appendVarWithSpace("h", c.getHeight());
      dc.appendVarWithSpace("pw", c.getPreferredSize().getWidth());
      dc.appendVarWithSpace("ph", c.getPreferredSize().getHeight());
      dc.append(" Located at " + c.getLocation().x + "," + c.getLocation().y);
      dc.appendVarWithSpace("ComponentCount", c.getComponentCount());
   }

   public void d1(JLabel c, Dctx dc) {
      dc.root1Line(c, "JLabel");
      dc.appendVarWithSpace("text", c.getText());
      this.d1((JComponent) c, dc.newLevel1Line());
   }

   public void d1(JLayeredPane c, Dctx dc) {
      dc.root1Line(c, "JLayeredPane");
      dc.appendVarWithSpace("lowestLayer", c.lowestLayer());
      dc.appendVarWithSpace("highestLayer", c.highestLayer());
      this.d((JComponent) c, dc.newLevel1Line());
   }

   public void d1(JPopupMenu e, Dctx dc) {
      dc.root1Line(e, "JPopupMenu");
   }

   public String d1(KeyEvent e) {
      Dctx dc = new Dctx(getUC());
      d1(e, dc);
      return dc.toString();
   }

   public void d1(KeyEvent e, Dctx dc) {
      dc.root1Line(e, "KeyEvent");
      dc.appendVarWithSpace("code", e.getKeyCode());
      dc.appendVarWithSpace("char", e.getKeyChar());
   }

   public String d1(ListSelectionEvent e) {
      Dctx dc = new Dctx(getUC());
      d1(e, dc);
      return dc.toString();
   }

   public void d1(ListSelectionEvent e, Dctx dc) {
      dc.root1Line(e, "ListSelectionEvent"); //null check
      dc.append("index=[" + e.getFirstIndex() + " " + e.getLastIndex() + " ]");
      dc.appendVarWithSpace("isAdjusting", e.getValueIsAdjusting());
   }

   public String d1(MouseEvent e) {
      Dctx dc = new Dctx(getUC());
      d1(e, dc);
      return dc.toString();
   }

   public void d1(MouseEvent e, Dctx dc) {
      dc.root1Line(e, "MouseEvent");
      dc.append(" " + ToStringStaticSwingCore.toStringMouseEventID(e));
      dc.append('[');
      dc.append(e.getX());
      dc.append(',');
      dc.append(e.getY());
      dc.append(' ');
      dc.append(" but=");
      dc.append(e.getButton());
      dc.append(" mods=");
      dc.append(MouseEvent.getMouseModifiersText(e.getModifiers()));
      dc.append(']');
      dc.appendVarWithSpace("isConsumed", e.isConsumed());

   }

   public String d1(PropertyChangeEvent e) {
      Dctx dc = new Dctx(getUC());
      d1(e, dc);
      return dc.toString();
   }

   public void d1(PropertyChangeEvent e, Dctx dc) {
      dc.root1Line(e, "PropertyChangeEvent");
      dc.appendVarWithSpace("name", e.getPropertyName());
      dc.appendVarWithSpace("New", e.getNewValue());
      dc.appendVarWithSpace("Old", e.getOldValue());
      if (e.getPropagationId() != null) {
         dc.appendVarWithSpace("id", e.getPropagationId());
      }
      dc.sameLineO1(e.getSource(), " Source", scc);
   }

   public void d1(Rectangle b, Dctx dc) {
      dc.root1Line(b, "Rectangle"); //null check
      dc.appendVarWithSpace("x", b.x);
      dc.appendVarWithSpace("y", b.y);
      dc.appendVarWithSpace("w", b.width);
      dc.appendVarWithSpace("h", b.height);
   }

   public String d1(Shape s) {
      Dctx dc = new Dctx(getUC());
      d1(s, dc);
      return dc.toString();
   }

   public void d1(Shape e, Dctx dc) {
      dc.root1Line(e, "Shape");
      dc.append("Bounds=");
      d1(e.getBounds(), dc);
   }

   public String d1(WindowEvent e) {
      Dctx dc = new Dctx(getUC());
      d1(e, dc);
      return dc.toString();
   }

   public void d1(WindowEvent e, Dctx dc) {
      dc.root1Line(e, "WindowEvent");
      dc.appendVarWithSpace("oldState", ToStringStaticSwingCore.toStringStateWindow(e.getOldState()));
      dc.appendVarWithSpace("newState", ToStringStaticSwingCore.toStringStateWindow(e.getNewState()));
      dc.sameLineO1(e.getWindow(), "Window", scc);
   }

   public void dFind(Component c, Dctx dc) {
      if (c instanceof JComponent) {
         dFind((JComponent) c, dc);
      } else if (c instanceof Container) {
         d((Container) c, dc);
      } else {
         d((Component) c, dc.newLevel());
      }
   }

   /**
    * 
    * @param c
    * @param dc
    */
   public void dFind(JComponent c, Dctx dc) {
      if (c instanceof AbstractButton) {
         d((AbstractButton) c, dc);
      } else if (c instanceof JTextPane) {
         d((JTextPane) c, dc);
      } else if (c instanceof JScrollPane) {
         d((JScrollPane) c, dc);
      } else if (c instanceof JScrollBar) {
         d((JScrollBar) c, dc);
      } else if (c instanceof JViewport) {
         d((JViewport) c, dc);
      } else if (c instanceof JRootPane) {
         d((JRootPane) c, dc);
      } else if (c instanceof JLayeredPane) {
         d((JLayeredPane) c, dc);
      } else if (c instanceof JPanel) {
         d((JPanel) c, dc);
      } else if (c instanceof JViewport) {
         d((JViewport) c, dc);
      } else {
         dc.append("Could not find the debug method for " + c.getClass().getName());
         d((JComponent) c, dc.newLevel());
      }
   }

   public void dFind1(JComponent c, Dctx dc) {
      if (c instanceof AbstractButton) {
         d1((AbstractButton) c, dc);
      } else if (c instanceof JLabel) {
         d1((JLabel) c, dc);
      } else if (c instanceof JTextPane) {
         d1((JTextPane) c, dc);
      } else if (c instanceof JScrollPane) {
         d1((JScrollPane) c, dc);
      } else if (c instanceof JScrollBar) {
         d1((JScrollBar) c, dc);
      } else if (c instanceof JViewport) {
         d1((JViewport) c, dc);
      } else if (c instanceof JRootPane) {
         d1((JRootPane) c, dc);
      } else if (c instanceof JLayeredPane) {
         d1((JLayeredPane) c, dc);
      } else if (c instanceof JPanel) {
         d1((JPanel) c, dc);
      } else if (c instanceof JViewport) {
         d1((JViewport) c, dc);
      } else {
         dc.appendWithSpace("Could not find the 1line debug method for " + c.getClass().getName());
         d1((JComponent) c, dc.newLevel1Line());
      }
   }

   public void dFontsAll(Dctx dc) {
      Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
      dc.root(fonts, "Swing Fonts");

      dc.appendVarWithSpace("Num#", fonts.length);

      List monoFonts1 = new ArrayList();

      IntToObjects monos = new IntToObjects(scc.getUC());
      IntToObjects monosNo = new IntToObjects(scc.getUC());

      //FontRenderContext frc = new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
      FontRenderContext frc = new FontRenderContext(null, false, false);

      //size of m for fontpoint of 12
      for (Font font : fonts) {
         //
         Font font12 = font.deriveFont(12f);
         Rectangle2D iBounds = font12.getStringBounds("i", frc);
         Rectangle2D mBounds = font12.getStringBounds("m", frc);

         double width = mBounds.getWidth();
         if (iBounds.getWidth() == width) {
            monos.add(font12, (int) width);
         } else {
            monosNo.add(font12, (int) width);
         }
      }
      dc.line();
      dc.appendVarWithSpace("Num Monospace Fonts#", monos.getSize());
      dc.tab();
      for (int index = 0; index < monos.getLength(); index++) {
         Font f = (Font) monos.getObjectAtIndex(index);
         dc.line();
         dc.append(index + 1);
         dc.append("\t");
         dc.append("m=");
         dc.append(monos.getInt(index));
         dc.append("\t");
         dc.append(f.getFontName());
         dc.append("\t");
         dc.append("\t -> Family='");
         dc.append(f.getFamily());
         dc.append("'");
      }
      dc.tabRemove();

      dc.line();
      dc.appendVarWithSpace("Num Proportional Fonts#", monos.getSize());
      dc.tab();
      for (int index = 0; index < monosNo.getLength(); index++) {
         Font f = (Font) monosNo.getObjectAtIndex(index);
         dc.line();
         dc.append(index + 1);
         dc.append("\t");
         dc.append("m=");
         dc.append(monosNo.getInt(index));
         dc.append("\t name='");
         dc.append(f.getFontName());
         dc.append("'\t");
         dc.append("\t -> Family='");
         dc.append(f.getFamily());
         dc.append("'");
      }
      dc.tabRemove();

      String[] ar = new String[monoFonts1.size()];
      for (int i = 0; i < ar.length; i++) {
         ar[i] = ((Font) monoFonts1.get(i)).getFontName();
      }

      String[] fontFamilies = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
      dc.appendVarWithSpace("Number of Font Families #", fontFamilies.length);
      for (int i = 0; i < fontFamilies.length; i++) {
         dc.line();
         dc.append(i + 1);
         dc.append("\t");
         dc.append(fontFamilies[i]);
      }

   }

   public UCtx getUC() {
      return scc.getUC();
   }

   public boolean toString(Object o, Dctx dc) {
      if (o instanceof WindowEvent) {
         d((WindowEvent) o, dc);
         return true;
      }
      return false;
   }

   /**
    * Utiliy method for debugging a {@link JFrame}
    * @param f
    * @param dc
    */
   public void toStringFrame(JFrame f, Dctx dc) {
      dc.root(f, "JFrame");
      dc.appendVarWithSpace("Title", f.getTitle());
      dc.append(' ');
      dc.append('[');
      dc.append(f.getX());
      dc.append(',');
      dc.append(f.getY());
      dc.append('-');
      dc.append(f.getWidth());
      dc.append(',');
      dc.append(f.getHeight());
      dc.append(']');
      dc.nl();
      dc.appendVarWithSpace("isActive", f.isActive());
      dc.appendVarWithSpace("isAlwaysOnTop", f.isAlwaysOnTop());
      dc.appendVarWithSpace("isAlwaysOnTopSupported", f.isAlwaysOnTopSupported());
      dc.appendVarWithSpace("isAutoRequestFocus", f.isAutoRequestFocus());
      dc.appendVarWithSpace("isCursorSet", f.isCursorSet());
      dc.nl();
      dc.appendVarWithSpace("isDisplayable", f.isDisplayable());
      dc.appendVarWithSpace("isDoubleBuffered", f.isDoubleBuffered());
      dc.appendVarWithSpace("isEnabled", f.isEnabled());
      dc.nl();
      dc.appendVarWithSpace("isFocusable", f.isFocusable());
      dc.appendVarWithSpace("isFocusableWindow", f.isFocusableWindow());
      dc.appendVarWithSpace("isFocusOwner", f.isFocusOwner());
      dc.appendVarWithSpace("isFocusCycleRoot", f.isFocusCycleRoot());
      dc.appendVarWithSpace("isFocused", f.isFocused());
      dc.appendVarWithSpace("isFocused", f.isFocusTraversalPolicySet());
      dc.nl();
      dc.appendVarWithSpace("isFontSet", f.isFontSet());
      dc.appendVarWithSpace("isForegroundSet", f.isForegroundSet());
      dc.appendVarWithSpace("isVisible", f.isVisible());
      dc.appendVarWithSpace("isResizable", f.isResizable());
      dc.appendVarWithSpace("isPreferredSizeSet", f.isPreferredSizeSet());
      dc.appendVarWithSpace("isFocusOwner", f.isFocusOwner());
      dc.nl();
      dc.appendVarWithSpace("isLightweight", f.isLightweight());
      dc.appendVarWithSpace("isLocationByPlatform", f.isLocationByPlatform());
      dc.appendVarWithSpace("isOpaque", f.isOpaque());
      dc.appendVarWithSpace("isUndecorated", f.isUndecorated());
      dc.appendVarWithSpace("isValid", f.isValid());
      dc.appendVarWithSpace("isValidateRoot", f.isValidateRoot());
      dc.appendVarWithSpace("isVisible", f.isVisible());
   }

   public void toStringFrame1Line(JFrame f, Dctx dc) {
      dc.root1Line(f, "JFrame");
      dc.appendVarWithSpace("Title", f.getTitle());
      dc.append(' ');
      dc.append('[');
      dc.append(f.getX());
      dc.append(',');
      dc.append(f.getY());
      dc.append('-');
      dc.append(f.getWidth());
      dc.append(',');
      dc.append(f.getHeight());
      dc.append(']');
   }

   public void toStringFrameNl(JFrame f, Dctx dc) {
      dc.nl();
      toStringFrame(f, dc);
   }

   public void toStringLocale(Locale loc, Dctx dc) {
      dc.root(loc, "Locale");
      dc.appendVarWithSpace("country", loc.getCountry());
      dc.appendVarWithSpace("language", loc.getLanguage());
      dc.appendVarWithSpace("DisplayCountry", loc.getDisplayCountry());
      dc.appendVarWithSpace("DisplayLanguage", loc.getDisplayLanguage());
   }

   //#enddebug
}
