package cz.zaf.earkvalidator.layers.dat.dat00_09;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipLoader;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.AipLoader.AipSrcType;

public class Rule02 extends AipRule {
	public static final String CODE = "dat02";
	public static final String RULE_TEXT = "Pokud je jednotlivý balíček předáván v zabalené/komprimované podobě, MUSÍ v tomto kontejneru (TAR nebo ZIP) být jedna složka nejvyšší úrovně. "
			+ "Detekovaný mimetype kontejneru MUSÍ být application/zip nebo application/x-tar";
	public static final String RULE_ERROR = "Soubor s balíčkem nemá rozpoznanou podobu.";
	public static final String RULE_SOURCE = "CZDAX-PSP0103"; 

	public Rule02() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		AipLoader loader = ctx.getLoader();
		if(loader.getAipSrcType()!=AipSrcType.FILE) {
			return;
		}
		// Kontrola, zda byl soubor rozbalen
		switch(loader.getLoadStatus()) {
		case OK:
			break;
		case ERR_ZIP_INCORRECT_STRUCTURE:
			String fileName = loader.getAipSrcPath().getFileName().toString();
			int pos = fileName.lastIndexOf('.');
			String expectedDir = pos>0?fileName.substring(0, pos):fileName;
			throw new ZafException(BaseCode.CHYBA, "Kontejner musí obsahovat jedinou složku nejvyšší úrovně pojmenovanou shodně se jménem souboru: "
					+ expectedDir);
		case ERR_UNZIP_FAILED:
		case ERR_UNKNOWN:
		default:
			throw new ZafException(BaseCode.CHYBA, "Soubor s balíčkem není platný ZIP soubor.");
		}
		// Kontrola zda soubor existuje
		Path srcPath = loader.getAipPath();
		if(srcPath == null || !Files.exists(srcPath)) {
			return;
		}
		// Kontrola zda slozka obsahuje datové soubory
		try(DirectoryStream<Path> stream = Files.newDirectoryStream(srcPath)) {
			// Kontrola, zda složka není prázdná
			Iterator<Path> it = stream.iterator();
			if(!it.hasNext()) {
				throw new ZafException(BaseCode.CHYBA, "Složka je prázdná: "+srcPath.toString());
			}
			it.next();
		} catch (IOException e) {
			throw new ZafException(BaseCode.CHYBA, "Složku nelze otevřít: "+srcPath.toString(), e);
		}
		
	}
}
