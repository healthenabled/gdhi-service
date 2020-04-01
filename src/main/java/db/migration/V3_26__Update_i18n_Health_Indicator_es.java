package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V3_26__Update_i18n_Health_Indicator_es extends BaseMigration {
    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Integración de la salud digital en la formación previa a la prestación de servicios de salud y la formación profesional conexa (antes de su despliegue).', " +
                "definition='Específicamente, ¿la salud digital forma parte del plan de estudios de los médicos en formación médica?' " +
                "WHERE indicator_id=20 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Integración de la salud digital en la formación previa a la prestación de servicios de salud y la formación profesional conexa (antes de su despliegue).', " +
                "definition='Específicamente, ¿la salud digital forma parte del plan de estudios de las enfermeras en formación previa al servicio?' " +
                "WHERE indicator_id=21 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Integración de la salud digital en la formación previa a la prestación de servicios de salud y la formación profesional conexa (antes de su despliegue).', " +
                "definition='Específicamente, ¿la salud digital forma parte de los planes de estudio de los profesionales de la salud y de los profesionales de los servicios de apoyo relacionados con la salud en la formación de los trabajadores de la salud de la comunidad?' " +
                "WHERE indicator_id=22 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Integración de la salud digital en la formación continua de los profesionales de la salud y la formación profesional conexa (después del despliegue).', " +
                "definition='Específicamente, ¿la salud digital forma parte del plan de estudios de los profesionales de la salud y de los profesionales de apoyo relacionados con la salud de la fuerza laboral en general? [Definidos como trabajadores comunitarios de salud, enfermeras, doctores, personal de salud aliado, gerentes/administradores de salud y tecnólogos]' " +
                "WHERE indicator_id=10 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Integración de la salud digital en la formación continua de los profesionales de la salud y la formación profesional conexa (después del despliegue).', " +
                "definition='Específicamente, ¿la salud digital forma parte del plan de estudios de los médicos de la fuerza laboral?' " +
                "WHERE indicator_id=23 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Integración de la salud digital en la formación continua de los profesionales de la salud y la formación profesional conexa (después del despliegue).', " +
                "definition='Específicamente, ¿la salud digital forma parte del plan de estudios de las enfermeras de la fuerza laboral?' " +
                "WHERE indicator_id=24 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Integración de la salud digital en la formación continua de los profesionales de la salud y la formación profesional conexa (después del despliegue).', " +
                "definition='Específicamente, ¿la salud digital forma parte del plan de estudios de los trabajadores de salud comunitarios de la fuerza laboral?' " +
                "WHERE indicator_id=25 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Formación de personal sanitario digital', " +
                "definition='En general, ¿la capacitación en salud digital / informática de la salud / sistemas de información sanitaria / informática biomédica (en instituciones públicas o privadas) está produciendo trabajadores de salud digital capacitados' " +
                "WHERE indicator_id=11 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Formación de personal sanitario digital', " +
                "definition='Específicamente, ¿la capacitación en salud y/o informática biomédica (ya sea en instituciones públicas o privadas) está produciendo informáticos capacitados o especialistas en sistemas de información de salud? ' " +
                "WHERE indicator_id=26 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Madurez de las carreras de los profesionales de la salud digital del sector público', " +
                "definition='¿Existen títulos profesionales y trayectorias profesionales en el sector público en el ámbito de la salud digital?' " +
                "WHERE indicator_id=12 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Arquitectura sanitaria digital nacional y/o intercambio de información sanitaria', " +
                "definition='¿Existe un marco arquitectónico nacional de salud digital (eHealth) y/o un intercambio de información sanitaria (HIE)?' " +
                "WHERE indicator_id=13 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Estándares de información sanitaria', " +
                "definition='¿Existen normas digitales de salud/información sanitaria para el intercambio de datos, la transmisión, la mensajería, la seguridad, la privacidad y el hardware?' " +
                "WHERE indicator_id=14 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Preparación de la red', " +
                "definition='Extraer la puntuación del Índice de Preparación para la Red del FEM' " +
                "WHERE indicator_id=15 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Planificación y apoyo para el mantenimiento continuo de la infraestructura de salud digital', " +
                "definition='¿Existe un plan articulado para apoyar la provisión y el mantenimiento de la infraestructura de salud digital (incluidos los equipos: computadoras, tabletas, teléfonos, suministros, programas informáticos, dispositivos, etc.)?' " +
                "WHERE indicator_id=16 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Sistemas de salud digitales a escala nacional', " +
                "definition='Las prioridades de salud del sector público están respaldadas por sistemas de salud digitales a escala nacional. (Utilice una hoja de trabajo separada para enumerar las áreas prioritarias especificadas del país, si existen sistemas digitales y si esos sistemas son de escala nacional). [eg. El país X elige 4 áreas prioritarias, utiliza sistemas digitales para abordar 2 de las 4, y sólo 1 de ellas está a escala nacional, recibe una puntuación del 25%].' " +
                "WHERE indicator_id=17 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Gestión de la identidad digital de los proveedores de servicios, administradores e instalaciones para la salud digital, incluidos los datos de localización para la cartografía del SIG. ', " +
                "definition='¿Están disponibles, accesibles y actualizados los registros del sistema de salud de proveedores, administradores y establecimientos públicos (y privados, si procede) identificables de manera única? ¿Están los datos geoetiquetados para permitir el mapeo GIS?' " +
                "WHERE indicator_id=18 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Gestión de la identidad digital de las personas para la salud', " +
                "definition='¿Están disponibles, accesibles y actualizados para su uso con fines relacionados con la salud registros seguros o un índice maestro de pacientes de personas identificables de manera única? ' " +
                "WHERE indicator_id=19 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Gestión de la identidad digital de las personas para la salud', " +
                "definition='Específicamente, ¿existe un índice seguro de pacientes principales de individuos singularmente identificables disponibles, accesibles y actuales para su uso con fines relacionados con la salud? ' " +
                "WHERE indicator_id=27 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Gestión de la identidad digital de las personas para la salud', " +
                "definition='Específicamente, ¿existe un registro de nacimientos seguro de personas identificables de manera única, disponible, accesible y actual para su uso con fines relacionados con la salud? ' " +
                "WHERE indicator_id=28 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Gestión de la identidad digital de las personas para la salud', " +
                "definition='Específicamente, ¿existe un registro de defunciones seguro de personas identificables de manera única, disponible, accesible y actual para su uso con fines relacionados con la salud? ' " +
                "WHERE indicator_id=29 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.health_indicator SET " +
                "name='Gestión de la identidad digital de las personas para la salud', " +
                "definition='Específicamente, ¿existe un registro de inmunización seguro de personas identificables de manera única, disponible, accesible y actual para su uso con fines relacionados con la salud?' " +
                "WHERE indicator_id=30 AND language_id='es'");
    }
}
