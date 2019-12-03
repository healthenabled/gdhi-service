package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V3_6__Insert_i18_Category extends BaseMigration {
    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update(
                "INSERT INTO i18n.category(category_id, language_id, name) VALUES(1, 'es', 'En general');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(2, 'es', 'Liderazgo y gobernanza');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(3, 'es', 'Estrategia e inversión');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(4, 'es', 'Legislación, políticas y cumplimiento');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(5, 'es', 'Recursos humanos');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(6, 'es', 'Estándares e interoperabilidad');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(7, 'es', 'Infraestructura');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(1, \"fr\", \"Dans l'ensemble\");" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(2, 'fr', 'Leadership et gouvernance');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(3, 'fr', 'Stratégie et investissement');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(4, 'fr', 'Lois, politiques et conformité');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(5, 'fr', 'Ressources Humaines');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(6, 'fr', 'Normes et interopérabilité');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(7, 'fr', 'Infrastructure');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(1, 'pt', 'Total');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(2, 'pt', 'Liderança e Governação');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(3, 'pt', 'Estratégia e Investimento');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(4, 'pt', 'Legislação, Política e Conformidade');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(5, 'pt', 'Recursos Humanos');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(6, 'pt', 'Normas e Interoperabilidade');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(7, 'pt', 'Infraestrutura');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(1, 'ar', 'المؤشر العام');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(2, 'ar', 'القيادة والحوكمة');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(3, 'ar', 'الاستراتيجية والاستثمار');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(4, 'ar', 'التشريعات والسياسات والامتثال');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(5, 'ar', 'الأيدي العاملة');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(6, 'ar', 'المعايير وقابلية التشغيل البيني');" +
                    "INSERT INTO i18n.category(category_id, language_id, name) VALUES(7, 'ar', 'البنية التحتية');");
    }
}
