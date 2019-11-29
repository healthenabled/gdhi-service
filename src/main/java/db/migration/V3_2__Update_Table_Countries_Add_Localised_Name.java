package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V3_2__Update_Table_Countries_Add_Localised_Name extends BaseMigration {
    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update(
            "UPDATE master.countries set name_spanish = 'Afganistán', name_french = 'Afghanistan', name_portuguese = 'Afeganistão', name_arabic = 'أفغانستان' where id = 'AFG';" +
                "UPDATE master.countries set name_spanish = 'Bangladesh', name_french = 'Bangladesh', name_portuguese = 'Bangladesh', name_arabic = 'بنغلاديش' where id = 'BGD';" +
                "UPDATE master.countries set name_spanish = 'Benín', name_french = 'Bénin', name_portuguese = 'Benim', name_arabic = 'بنين' where id = 'BEN';" +
                "UPDATE master.countries set name_spanish = 'Chile', name_french = 'Chili', name_portuguese = 'Chile', name_arabic = 'تشيلي' where id = 'CHL';" +
                "UPDATE master.countries set name_spanish = 'Etiopía', name_french = 'Éthiopie', name_portuguese = 'Etiópia', name_arabic = 'أثيوبيا' where id = 'ETH';" +
                "UPDATE master.countries set name_spanish = 'Indonesia', name_french = 'Indonésie', name_portuguese = 'Indonésia', name_arabic = 'إندونيسيا' where id = 'IDN';" +
                "UPDATE master.countries set name_spanish = 'Jordania', name_french = 'Jordanie', name_portuguese = 'Jordânia', name_arabic = 'الأردن' where id = 'JOR';" +
                "UPDATE master.countries set name_spanish = 'Kuwait', name_french = 'Koweït', name_portuguese = 'Kuwait', name_arabic = 'الكويت' where id = 'KWT';" +
                "UPDATE master.countries set name_spanish = 'República Democrática Popular Lao', name_french = 'République démocratique populaire lao', name_portuguese = 'República Democrática Popular do Laos', " +"name_arabic = 'جمهورية لاو الديمقراطية الشعبية' where id = 'LAO';" +
                "UPDATE master.countries set name_spanish = 'Malasia', name_french = 'Malaisie', name_portuguese = 'Malásia', name_arabic = 'ماليزيا' where id = 'MYS';" +
                "UPDATE master.countries set name_spanish = 'Malí', name_french = 'Mali', name_portuguese = 'Mali', name_arabic = 'مالي' where id = 'MLI';" +
                "UPDATE master.countries set name_spanish = 'Mongolia', name_french = 'Mongolie', name_portuguese = 'Mongólia', name_arabic = 'منغوليا' where id = 'MNG';" +
                "UPDATE master.countries set name_spanish = 'Nueva Zelanda', name_french = 'Nouvelle-Zélande', name_portuguese = 'Nova Zelândia', name_arabic = 'نيوزيلاندا' where id = 'NZL';" +
                "UPDATE master.countries set name_spanish = 'Nigeria', name_french = 'Nigéria', name_portuguese = 'Nigéria', name_arabic = 'نيجيريا' where id = 'NGA';" +
                "UPDATE master.countries set name_spanish = 'Pakistán', name_french = 'Pakistan', name_portuguese = 'Paquistão', name_arabic = 'باكستان' where id = 'PAK';" +
                "UPDATE master.countries set name_spanish = 'Perú', name_french = 'Pérou', name_portuguese = 'Peru', name_arabic = 'بيرو' where id = 'PER';" +
                "UPDATE master.countries set name_spanish = 'Filipinas', name_french = 'Philippines', name_portuguese = 'Filipinas', name_arabic = 'الفلبين' where id = 'PHL';" +
                "UPDATE master.countries set name_spanish = 'Portugal', name_french = 'Portugal', name_portuguese = 'Portugal', name_arabic = 'البرتغال' where id = 'PRT';" +
                "UPDATE master.countries set name_spanish = 'Sierra Leona', name_french = 'Sierra Leone', name_portuguese = 'Serra Leoa', name_arabic = 'سيرا ليون' where id = 'SLE';" +
                "UPDATE master.countries set name_spanish = 'Sri Lanka', name_french = 'Sri Lanka', name_portuguese = 'Sri Lanka', name_arabic = 'سيريلانكا' where id = 'LKA';" +
                "UPDATE master.countries set name_spanish = 'Tailandia', name_french = 'Thaïlande', name_portuguese = 'Tailândia', name_arabic = 'تايلاند' where id = 'THA';" +
                "UPDATE master.countries set name_spanish = 'Uganda', name_french = 'Ouganda', name_portuguese = 'Uganda', name_arabic = 'أوغندا' where id = 'UGA';");
    }
}



