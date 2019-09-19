/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.frames.panels;

import javax.swing.JComponent;
import javax.swing.MenuSelectionManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;

/**
 *
 * @author m000xz006159
 */
public class StayOpenCheckBoxMenuItemUI extends BasicCheckBoxMenuItemUI {

   @Override
   protected void doClick(MenuSelectionManager msm) {
      menuItem.doClick(0);
   }

   public static ComponentUI createUI(JComponent c) {
      return new StayOpenCheckBoxMenuItemUI();
   }
}
