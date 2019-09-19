/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.validator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Třída s funkcemi pro zjišťování MD5 souborů.
 * @author standa
 */
public class ChecksumMD5 
{
      public String udelejMD5(String vstup) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        
        String kodovani = "ASCII";
        byte[] vstupVBajtech = vstup.getBytes(kodovani);
        MessageDigest md = MessageDigest.getInstance("MD5");        
       
        byte[] md5Bajty = md.digest(vstupVBajtech);
        String vysledek = new String(md5Bajty, kodovani);
        
        return vysledek;
    }
      
    public String udelejMD5(byte [] vstup) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        
        String kodovani = "UTF-8";
        String r = new String(vstup, kodovani);        
        MessageDigest md = MessageDigest.getInstance("MD5");        
       
        byte[] md5Bajty = md.digest(r.getBytes());      
        String vysledek = new String(md5Bajty, kodovani);
        
        return vysledek;
    }
    
    public String md52(String text) throws FileNotFoundException, NoSuchAlgorithmException, IOException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes("UTF-8"));
        
        byte byteData[] = md.digest();
 
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
        
        String ret = hexString.toString();
        return ret;
    }
    
      public String md53(String cesta) throws FileNotFoundException, NoSuchAlgorithmException, IOException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        FileInputStream fis = new FileInputStream(cesta);
        
        byte[] dataBytes = new byte[1024];
     
        int nread = 0; 
        while ((nread = fis.read(dataBytes)) != -1) {
          md.update(dataBytes, 0, nread);
        }
        byte[] mdbytes = md.digest();
     
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        String ret = sb.toString();
        return ret;
    }
}
