package cz.zaf.sipvalidator.nsesss2017.profily;

public final class ProfilyValidace {
    // Specialni seznam pro vyvoj a overovani implementace    
    private static int[] seznamVyvoj = { 1, 2, 3, 4, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 22, 23, 24, 25,
            26, 27, 28, 29, 30, 31, 33, 34, 35, 36, 37, 38, 39, 40, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 41, 55,
            56, 57, 58, 59, 60, 61, 42, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81,
            82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 32, 94, 95, 96, 97, 98, 99 };

    // 1
    private static int[] seznam_Prazdny = { 1, 2, 4, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 33, 34, 35, 36, 37, 38, 39, 54, 41, 57, 58, 59, 61, 42, 62, 63, 64, 65, 66, 67, 68, 69,
            70, 71, 72, 73, 74, 75, 76, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 32, 94, 95, 96, 97, 98, 99 };
    // 2
    private static int[] seznam_Plny = { 1, 2, 4, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 22, 23, 24, 25, 26, 27,
            28, 29, 30, 31, 33, 34, 35, 36, 37, 38, 39, 40, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 41, 55, 56, 57,
            58, 59, 60, 61, 42, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 81, 82, 83, 84, 85, 86, 87,
            88, 89, 90, 91, 92, 93, 32, 94, 95, 96, 97, 98, 99 };
    // 3
    private static int[] seznam_Prejimka = { 1, 3, 4, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 33, 34, 35, 36, 37, 38, 39, 40, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 41, 55, 56,
            57, 58, 59, 60, 61, 42, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82,
            83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 32, 94, 95, 96, 97, 98, 99 };

    static public final ProfilValidace DEVEL = new ZakladniProfilValidace(
            "vývojářská validace",
            seznamVyvoj);
    static public final ProfilValidace SKARTACE_METADATA = new ZakladniProfilValidace(
            "skartační řízení (jen metadata)",
            seznam_Prazdny);
    static public final ProfilValidace SKARTACE_UPLNY = new ZakladniProfilValidace(
            "skartační řízení (s komponentami)",
            seznam_Plny);
    static public final ProfilValidace PREJIMKA = new ZakladniProfilValidace(
            "přejímka",
            seznam_Prejimka);
}
