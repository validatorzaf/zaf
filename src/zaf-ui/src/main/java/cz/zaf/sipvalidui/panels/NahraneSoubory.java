package cz.zaf.sipvalidui.panels;

import java.util.ArrayList;
import java.util.List;

import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipLoader;

public class NahraneSoubory {
    public ArrayList<SipLoader> seznamNahranychSouboru = new ArrayList<>();

    public boolean isLoaded(String sipName) {
        for (SipLoader sl : seznamNahranychSouboru) {
            if (sipName.equals(sl.getSip().getName())) {
                return true;
            }
        }
        return false;
    }

    public void add(SipLoader sipLoader) {
        seznamNahranychSouboru.add(sipLoader);
    }

    public int size() {
        return seznamNahranychSouboru.size();
    }

    public SipLoader get(int i) {
        return seznamNahranychSouboru.get(i);
    }

    public void clear() {
        for (SipLoader sl : seznamNahranychSouboru) {
            try {
                sl.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        seznamNahranychSouboru.clear();
    }

    public List<SipInfo> getSipy(int start, int end) {
        ArrayList<SipInfo> result = new ArrayList<>(end - start);
        for (SipLoader sl : seznamNahranychSouboru.subList(start, end)) {
            result.add(sl.getSip());
        }
        return result;
    }
}
