package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V3_27__Update_i18n_Score_Definition_es extends BaseMigration {
    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existe un plan de estudios de salud digital para médicos como parte de los requisitos de capacitación previa al servicio.' " +
                "WHERE indicator_id=20 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se ha propuesto y se está revisando un plan de estudios de salud digital como parte de los requisitos de formación previa al servicio para los médicos' " +
                "WHERE indicator_id=20 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se está implementando un plan de estudios de salud digital que cubre un estimado de 0-25% de médicos en formación previa al servicio.' " +
                "WHERE indicator_id=20 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='La salud digital se imparte en las instituciones pertinentes y se estima que entre el 50 y el 75% de los médicos reciben formación previa al empleo.' " +
                "WHERE indicator_id=20 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='La salud digital se enseña en las instituciones pertinentes con más del 75% de los médicos que reciben formación previa al empleo. ' " +
                "WHERE indicator_id=20 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existe un plan de estudios de salud digital para enfermeras como parte de los requisitos de capacitación previa al servicio' " +
                "WHERE indicator_id=21 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se ha propuesto y se está revisando un plan de estudios de salud digital como parte de los requisitos de formación previa al servicio para enfermeras.' " +
                "WHERE indicator_id=21 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se está llevando a cabo la implementación de un plan de estudios de salud digital que cubre un estimado del 0-25% de los profesionales de la salud en formación previa al servicio' " +
                "WHERE indicator_id=21 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='La salud digital se imparte en las instituciones pertinentes y se estima que entre el 50 y el 75% de los enfermeros reciben formación previa al empleo.' " +
                "WHERE indicator_id=21 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='La salud digital se imparte en las instituciones pertinentes, con más del 75% de los enfermeros que reciben formación previa al empleo' " +
                "WHERE indicator_id=21 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existe un plan de estudios de salud digital para los profesionales de la salud como parte de los requisitos de capacitación previa al servicio para los trabajadores de salud de la comunidad.' " +
                "WHERE indicator_id=22 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se ha propuesto y se está revisando un plan de estudios de salud digital como parte de los requisitos de formación previa al servicio para los trabajadores de la salud de la comunidad.' " +
                "WHERE indicator_id=22 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se está llevando a cabo la implementación de un plan de estudios de salud digital que cubre aproximadamente el 0-25% de los trabajadores comunitarios de la salud en formación previa al servicio.' " +
                "WHERE indicator_id=22 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='La salud digital se imparte en las instituciones pertinentes y se estima que entre el 50 y el 75% de los trabajadores sanitarios de la comunidad reciben formación previa al empleo.' " +
                "WHERE indicator_id=22 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='La salud digital se imparte en las instituciones pertinentes y más del 75% de los trabajadores comunitarios de la salud reciben formación previa al empleo. ' " +
                "WHERE indicator_id=22 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existe un plan de estudios de salud digital como parte de la capacitación en el servicio (educación continua) para los profesionales de la salud de la fuerza laboral.' " +
                "WHERE indicator_id=10 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se ha propuesto y se está revisando un plan de estudios de salud digital como parte de la formación en el servicio (educación continua) para los profesionales de la salud de la fuerza laboral.' " +
                "WHERE indicator_id=10 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='El plan de estudios de salud digital se implementa como parte de la formación en servicio (educación continua) para profesionales de la salud de 0-25% de la fuerza laboral.' " +
                "WHERE indicator_id=10 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='El currículo de salud digital se implementa como parte de la capacitación en servicio (educación continua) para el 50-75% de los profesionales de la salud en la fuerza laboral. ' " +
                "WHERE indicator_id=10 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='El currículo de salud digital se implementa como parte de la capacitación en servicio (educación continua) para profesionales de la salud de más del 75% de la fuerza laboral. ' " +
                "WHERE indicator_id=10 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existe un plan de estudios de salud digital como parte de la capacitación en el servicio (educación continua) para los médicos de la fuerza laboral.' " +
                "WHERE indicator_id=23 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se ha propuesto y se está revisando un plan de estudios de salud digital como parte de la formación en el servicio (educación continua) de los médicos y médicos de la fuerza laboral.' " +
                "WHERE indicator_id=23 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='El plan de estudios de salud digital se implementa como parte de la formación en servicio (educación continua) para el 0-25% de los médicos de la fuerza laboral.' " +
                "WHERE indicator_id=23 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='El plan de estudios de salud digital se implementa como parte de la capacitación en el servicio (educación continua) para el 50-75% de los doctores/médicos de la fuerza laboral. ' " +
                "WHERE indicator_id=23 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='El plan de estudios de salud digital se implementa como parte de la formación en servicio (educación continua) para más del 75% de los médicos y médicos de la fuerza laboral.' " +
                "WHERE indicator_id=23 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existe un plan de estudios de salud digital como parte de la capacitación en el servicio (educación continua) para enfermeras de la fuerza laboral.' " +
                "WHERE indicator_id=24 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se ha propuesto y se está revisando un plan de estudios de salud digital como parte de la formación en el servicio (educación continua) de los enfermeros de la fuerza laboral.' " +
                "WHERE indicator_id=24 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='El currículo de salud digital se implementa como parte de la capacitación en servicio (educación continua) para el 0-25% de los enfermeros de la fuerza laboral' " +
                "WHERE indicator_id=24 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='El currículo de salud digital se implementa como parte de la capacitación en servicio (educación continua) para el 50-75% de los enfermeros de la fuerza laboral. ' " +
                "WHERE indicator_id=24 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='El currículo de salud digital se implementa como parte de la capacitación en servicio (educación continua) para más del 75% de los enfermeros de la fuerza laboral.' " +
                "WHERE indicator_id=24 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existe un plan de estudios de salud digital como parte de la capacitación en el servicio (educación continua) para los trabajadores de la salud de comunitarios de la fuerza laboral.' " +
                "WHERE indicator_id=25 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se propuso y se está revisando un plan de estudios de salud digital como parte de la capacitación en el servicio (educación continua) para los trabajadores de salud comunitarios de la fuerza laboral.' " +
                "WHERE indicator_id=25 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='El currículo de salud digital se implementa como parte de la capacitación en servicio (educación continua) para el 0-25% de los trabajadores de la salud comunitaria de la fuerza laboral.' " +
                "WHERE indicator_id=25 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='El currículo de salud digital se implementa como parte de la capacitación en servicio (educación continua) para el 50-75% de los trabajadores de salud comunitaria de la fuerza laboral. ' " +
                "WHERE indicator_id=25 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='El currículo de salud digital se implementa como parte de la capacitación en servicio (educación continua) para más del 75% de los trabajadores de salud comunitaria de la fuerza laboral.' " +
                "WHERE indicator_id=25 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No hay formación disponible para el personal sanitario digital en el país.' " +
                "WHERE indicator_id=11 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se evaluaron las necesidades de personal sanitario digital, se determinaron las deficiencias y se están elaborando opciones de capacitación.' " +
                "WHERE indicator_id=11 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='La formación profesional está disponible, pero los graduados aún no han sido desplegados.' " +
                "WHERE indicator_id=11 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se dispone y se despliega a profesionales de la salud digital capacitados, pero siguen existiendo lagunas esenciales en el personal.' " +
                "WHERE indicator_id=11 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Un número suficiente de profesionales de la salud digital capacitados disponibles para apoyar las necesidades nacionales de salud digital.' " +
                "WHERE indicator_id=11 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No hay formación disponible en informática ni en sistemas de información sanitaria en el país.' " +
                "WHERE indicator_id=26 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se evaluaron las necesidades de personal de informática de la salud, se determinaron las deficiencias y se están elaborando opciones de capacitación. ' " +
                "WHERE indicator_id=26 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='La formación profesional en informática de la salud está disponible, pero los graduados aún no han sido desplegados.' " +
                "WHERE indicator_id=26 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se dispone y se despliega a profesionales de la informática capacitados, pero siguen existiendo lagunas esenciales en materia de personal.' " +
                "WHERE indicator_id=26 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Un número suficiente de profesionales capacitados en informática de la salud disponibles para apoyar las necesidades de los sistemas nacionales de información de salud.' " +
                "WHERE indicator_id=26 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existe ninguna estrategia, política o guía de la fuerza laboral que reconozca la salud digital. La distribución del personal sanitario digital es ad hoc.' " +
                "WHERE indicator_id=12 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Una evaluación de las necesidades nacionales muestra el número y los tipos de habilidades necesarias para apoyar la salud digital, con un enfoque explícito en la formación de cuadros de trabajadores de la salud. ' " +
                "WHERE indicator_id=12 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Las funciones y responsabilidades del personal de salud digital se asignan a la fuerza de trabajo y a los planes de carrera del gobierno y al 25-50% de la fuerza de trabajo de salud digital del sector público que se necesita. ' " +
                "WHERE indicator_id=12 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe una política de recursos humanos y un plan estratégico que identifica las habilidades y funciones necesarias para apoyar la salud digital, con un enfoque explícito en la formación de cuadros de trabajadores de la salud y un estimado del 50-75% del personal de salud digital del sector público.' " +
                "WHERE indicator_id=12 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un plan a largo plazo para aumentar y mantener el personal con las habilidades necesarias para mantener la salud digital a nivel nacional y subnacional, con un enfoque explícito en la formación de cuadros de trabajadoras de la salud con un estimado de más del 75% de los puestos necesarios cubiertos. Se han establecido sistemas de gestión de la actuación profesional para garantizar el crecimiento y la sostenibilidad del personal sanitario digital con una oferta suficiente para satisfacer las necesidades sanitarias digitales y una escasa rotación de personal...' " +
                "WHERE indicator_id=12 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existe un marco arquitectónico nacional de salud digital (eHealth) ni un intercambio de información sanitaria (HIE). ' " +
                "WHERE indicator_id=13 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se ha propuesto una arquitectura de salud digital nacional y/o intercambio de información de salud (HIE), pero no se ha aprobado, incluyendo capas semánticas, sintácticas y organizativa' " +
                "WHERE indicator_id=13 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='La arquitectura digital nacional de salud y/o el intercambio de información sanitaria (HIE) son operativos y proporcionan funciones básicas, como la autenticación, la traducción, el almacenamiento y la función de almacenamiento, la guía sobre qué datos están disponibles y cómo acceder a ellos, y la interpretación de los datos.' " +
                "WHERE indicator_id=13 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='El gobierno dirige, administra y hace cumplir la implementación de la arquitectura nacional de salud digital y/o el intercambio de información de salud (HIE), los cuales se implementan completamente de acuerdo a los estándares de la industria.' " +
                "WHERE indicator_id=13 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='La arquitectura digital nacional de salud y/o el intercambio de información sanitaria (HIE) proporcionan funciones básicas de intercambio de datos y se revisa y actualiza periódicamente para satisfacer las necesidades de la cambiante arquitectura digital de salud. Hay aprendizaje continuo, innovación y control de calidad. Los datos se utilizan activamente para la planificación estratégica y la presupuestación de la salud nacional.' " +
                "WHERE indicator_id=13 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existen estándares digitales de salud/información sanitaria para el intercambio de datos, la transmisión, la mensajería, la seguridad, la privacidad y el hardware. ' " +
                "WHERE indicator_id=14 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existen algunas normas digitales de salud/información sanitaria para el intercambio de datos, la transmisión, la mensajería, la seguridad, la privacidad y el hardware que se han adoptado y/o se utilizan.' " +
                "WHERE indicator_id=14 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se han publicado y difundido en el país, bajo la dirección del gobierno, normas digitales de salud/información sanitaria para el intercambio de datos, la transmisión, la mensajería, la seguridad, la privacidad y el hardware.' " +
                "WHERE indicator_id=14 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='En la mayoría de las aplicaciones y sistemas se utilizan normas técnicas digitales basadas en la industria de la salud para el intercambio de datos, la transmisión, la mensajería, la seguridad, la privacidad y el hardware, a fin de garantizar la disponibilidad de datos de alta calidad. Las pruebas de conformidad se llevan a cabo rutinariamente para certificar a los implementadores.' " +
                "WHERE indicator_id=14 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Las normas de datos se actualizan periódicamente y los datos se utilizan activamente para supervisar y evaluar el sistema de salud y para la planificación estratégica y la presupuestación nacional de la salud' " +
                "WHERE indicator_id=14 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='1.0 - 3.3' " +
                "WHERE indicator_id=15 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='>3.3 - 4.0' " +
                "WHERE indicator_id=15 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='>4.0 - 5.0' " +
                "WHERE indicator_id=15 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='>5.0 - 5.4' " +
                "WHERE indicator_id=15 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='>5.4 - 7.0' " +
                "WHERE indicator_id=15 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existe un plan articulado de apoyo a la infraestructura de salud digital (incluidos los equipos: computadoras, tabletas, teléfonos, suministros, programas informáticos, dispositivos, etc.), ni a la prestación y el mantenimiento. ' " +
                "WHERE indicator_id=16 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se ha elaborado, pero no se ha aplicado, un plan de apoyo a la infraestructura sanitaria digital (incluidos los equipos: computadoras, tabletas, teléfonos, suministros, programas informáticos, dispositivos, etc.). ' " +
                "WHERE indicator_id=16 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se ha implementado parcialmente un plan para apoyar la provisión y el mantenimiento de la infraestructura de salud digital (incluyendo equipos, computadoras, tabletas, teléfonos, suministros, software, dispositivos, etc.), pero no de manera consistente con la estimación del 0-25% de la infraestructura de salud digital necesaria para el sector de servicios de salud pública disponible y en uso.' " +
                "WHERE indicator_id=16 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se ha puesto en marcha un plan de apoyo a la infraestructura sanitaria digital (incluidos los equipos: ordenadores, tabletas, teléfonos, suministros, programas informáticos, dispositivos, etc.), de forma parcial y coherente, con una estimación del 25-50% de la infraestructura sanitaria digital necesaria disponible y en uso en el sector de los servicios sanitarios públicos.' " +
                "WHERE indicator_id=16 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='La infraestructura sanitaria digital (incluidos los equipos: ordenadores, tabletas, teléfonos, suministros, programas informáticos, dispositivos, etc.) está disponible, en uso, y se mantiene y actualiza regularmente en más del 75% del sector de los servicios sanitarios públicos.' " +
                "WHERE indicator_id=16 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Las áreas prioritarias nacionales no cuentan con el apoyo de la salud digital a ninguna escala.' " +
                "WHERE indicator_id=17 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Pocas áreas nacionales prioritarias cuentan con el apoyo de la salud digital, y se ha iniciado la ejecución (< 25% de las áreas prioritarias). ' " +
                "WHERE indicator_id=17 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Algunas áreas prioritarias nacionales apoyadas por sistemas digitales de salud a escala (25-50% de las áreas prioritarias).' " +
                "WHERE indicator_id=17 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='La mayoría, pero no todas las áreas prioritarias nacionales (50-75% de las áreas prioritarias) apoyadas por sistemas digitales de salud a escala.)' " +
                "WHERE indicator_id=17 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Todas las áreas priorizadas a nivel nacional apoyadas por sistemas digitales de salud a escala nacional (>75%) con sistemas y resultados de monitoreo y evaluación.' " +
                "WHERE indicator_id=17 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Los registros del sistema de salud de proveedores, administradores y establecimientos públicos (y privados, si procede) identificables de manera única no están disponibles, son accesibles y están actualizados.' " +
                "WHERE indicator_id=18 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se están creando registros del sistema de salud de proveedores, administradores y establecimientos públicos (y privados, si procede) identificables de manera única, pero no están disponibles para su uso.' " +
                "WHERE indicator_id=18 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Los registros del sistema de salud de proveedores, administradores y establecimientos públicos (y privados si procede) identificables de manera única están disponibles para su uso, pero son incompletos, parcialmente disponibles, se utilizan de manera esporádica y se mantienen de manera irregular.' " +
                "WHERE indicator_id=18 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Los registros del sistema de salud de proveedores, administradores y establecimientos públicos (y privados, si procede) identificables de manera única están disponibles, se utilizan y se actualizan y mantienen regularmente. Los datos se etiquetan geográficamente para permitir el mapeo GIS.' " +
                "WHERE indicator_id=18 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Se dispone de registros del sistema de salud de proveedores, administradores y establecimientos públicos (y privados, si procede) identificables de manera única, actualizados con datos geoetiquetados y utilizados para la planificación estratégica y la presupuestación del sistema y los servicios de salud. ' " +
                "WHERE indicator_id=18 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existe un registro seguro ni un índice de pacientes principales. ' " +
                "WHERE indicator_id=19 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro seguro, pero está incompleto / parcialmente disponible, utilizado y mantenido de forma irregular. ' " +
                "WHERE indicator_id=19 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro seguro, disponible y en uso activo que incluye <25% de la población relevante.' " +
                "WHERE indicator_id=19 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro seguro, disponible y en uso activo que incluye entre el 25 y el 50% de la población pertinente. ' " +
                "WHERE indicator_id=19 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro seguro, disponible y en uso activo que incluye a más del 75% de la población pertinente. Los datos están disponibles, se utilizan y se curan.' " +
                "WHERE indicator_id=19 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existe un índice seguro de pacientes principales. ' " +
                "WHERE indicator_id=27 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un índice de paciente maestro, pero está incompleto / parcialmente disponible, utilizado y mantenido irregularmente.' " +
                "WHERE indicator_id=27 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un índice de paciente maestro, está disponible y en uso activo e incluye <25% de la población relevante.' " +
                "WHERE indicator_id=27 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un índice de pacientes principales, está disponible y en uso activo e incluye entre el 25 y el 50% de la población pertinente. ' " +
                "WHERE indicator_id=27 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un índice de pacientes principales, está disponible y en uso activo e incluye a más del 75% de la población relevante. Los datos están disponibles, se utilizan y se curan.' " +
                "WHERE indicator_id=27 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existe un registro de nacimiento seguro.' " +
                "WHERE indicator_id=28 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro de nacimiento seguro, pero está incompleto / parcialmente disponible, utilizado y mantenido irregularmente.' " +
                "WHERE indicator_id=28 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro de nacimientos seguro, disponible y en uso activo que incluye <25% de la población pertinente.' " +
                "WHERE indicator_id=28 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro de nacimientos seguro, disponible y en uso activo, que incluye entre el 25 y el 50% de la población pertinente. ' " +
                "WHERE indicator_id=28 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro de nacimientos seguro, disponible y en uso activo que incluye a más del 75% de la población pertinente. Los datos están disponibles, se utilizan y se curan.' " +
                "WHERE indicator_id=28 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existe un registro de defunción seguro.' " +
                "WHERE indicator_id=29 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro de defunción seguro, pero está incompleto / parcialmente disponible, utilizado y mantenido irregularmente. ' " +
                "WHERE indicator_id=29 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro de defunciones seguro, disponible y en uso activo que incluye <25% de la población relevante.' " +
                "WHERE indicator_id=29 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro de defunciones seguro, disponible y en uso activo que incluye entre el 25% y el 50% de la población pertinente. ' " +
                "WHERE indicator_id=29 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro de defunciones seguro, disponible y en uso activo que incluye a más del 75% de la población pertinente. Los datos están disponibles, se utilizan y se curan.' " +
                "WHERE indicator_id=29 AND score=5 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='No existe un registro de vacunación seguro. ' " +
                "WHERE indicator_id=30 AND score=1 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro de inmunización seguro, pero está incompleto / parcialmente disponible, utilizado y mantenido de forma irregular. ' " +
                "WHERE indicator_id=30 AND score=2 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro de inmunización seguro, disponible y en uso activo que incluye <25% de la población relevante.' " +
                "WHERE indicator_id=30 AND score=3 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro de inmunización seguro, disponible y en uso activo que incluye entre el 25% y el 50% de la población pertinente. ' " +
                "WHERE indicator_id=30 AND score=4 AND language_id='es'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='Existe un registro de inmunización seguro, disponible y en uso activo que incluye a más del 75% de la población pertinente. Los datos están disponibles, se utilizan y se curan.' " +
                "WHERE indicator_id=30 AND score=5 AND language_id='es'");
    }
}
