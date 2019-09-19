/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.openFiles;

import java.util.List;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import static sipvalid.openFiles.SIP_Opener.sip_opener_index;
import static sipvalid.openFiles.SIP_Opener.sip_opener_index_fileName;
import static sipvalid.openFiles.SIP_Opener.sip_opener_pocet;
/**
 *
 * @author m000xz006159
 */
public class SIP_Opener_ProgressWorker extends SwingWorker<Void, Integer> {

    private final JProgressBar progress;
    private final JLabel label;
    public static int percentage_real_index;

        public SIP_Opener_ProgressWorker(JProgressBar progress, JLabel label) {
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
                publish(get_ral_percentage(sip_opener_index, sip_opener_pocet));
//            }
            return null;
        }

        @Override
        protected void process(List<Integer> chunks) {
            label.setText(sip_opener_index_fileName);
            progress.setValue(chunks.get(chunks.size() - 1));
            super.process(chunks);
        }

        @Override
        protected void done() {
            label.setText("HOTOVO");
            progress.setValue(100);
        }
        
    
}
