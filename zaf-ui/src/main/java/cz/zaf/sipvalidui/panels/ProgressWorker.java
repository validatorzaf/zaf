/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.panels;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import static cz.zaf.sipvalidui.panels.JFmain.progresbar_index;
import static cz.zaf.sipvalidui.panels.JFmain.progresbar_pocet;
import static cz.zaf.sipvalidui.panels.JFmain.progresbar_textforlabel;
/**
 *
 * @author m000xz006159
 */
public class ProgressWorker extends SwingWorker<Void, Integer> {

    private final JProgressBar progress;
    private final JLabel label;
    public static int percentage_real_index;

        public ProgressWorker(JProgressBar progress, JLabel label) {
            this.progress = progress;
            this.label = label;
        }
        
        private static int get_ral_percentage(int index, int pocet){
            if(pocet == 0){
                return 0;
            }
            percentage_real_index = 100 * index / pocet;
            return percentage_real_index;
        }

        @Override
        protected Void doInBackground() throws Exception {
//            for (long i = pocet; i > 0; i--) {
//                final int progr = (int) ((100L * (100 - i)) / 100);
                publish(get_ral_percentage(progresbar_index, progresbar_pocet));
//            }
            return null;
        }

        @Override
        protected void process(List<Integer> chunks) {
            label.setText(progresbar_textforlabel);
            progress.setValue(chunks.get(chunks.size() - 1));
            super.process(chunks);
        }

        @Override
        protected void done() {
            label.setText("HOTOVO");
            progress.setValue(100);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProgressWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    
}
