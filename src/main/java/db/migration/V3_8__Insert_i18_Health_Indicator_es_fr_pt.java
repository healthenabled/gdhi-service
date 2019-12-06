package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V3_8__Insert_i18_Health_Indicator_es_fr_pt extends BaseMigration {
    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update(
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(1,  'es', 'Priorización de la salud digital a nivel nacional a través de organismos y mecanismos dedicados a la gobernanza.', '¿Tiene el país un departamento / agencia / grupo de trabajo nacional separado para la salud digital?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(2,  'es', 'Priorización de la salud digital a nivel nacional a través de la planificación', '¿Se incluye y presupuesta la salud digital en las estrategias y/o planes nacionales de salud o en las estrategias y/o planes nacionales pertinentes? Nota: Este indicador se centra en la inclusión de la salud digital o la salud electrónica en la estrategia nacional de salud.'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(3,  'es', 'Marco o estrategia nacional de salud electrónica y salud digital', '¿Dispone el país de una estrategia o marco de salud digital o eHealth y de un plan de salud digital presupuestado?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(4,  'es', 'Financiación pública para la salud digital', '¿Cuál es el porcentaje estimado (%) del gasto público anual en salud comprometido con la salud digital?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(5,  'es', 'Marco Legal para la Protección de Datos (Seguridad)', '¿Existe una ley sobre seguridad de datos (almacenamiento, transmisión, uso) que sea relevante para la salud digital?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(6,  'es', 'Leyes o reglamentos para la privacidad, confidencialidad y acceso a la información de salud (Privacidad)', '¿Existe una ley que proteja la privacidad de las personas, que regule la propiedad, el acceso y el intercambio de datos sanitarios digitales identificables individualmente?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(7,  'es', 'Protocolo para la regulación o certificación de dispositivos y/o servicios sanitarios digitales', '¿Existen protocolos, políticas, marcos o procesos aceptados que rijan el uso clínico y de atención al paciente de dispositivos médicos y servicios de salud digitales conectados (por ejemplo, telemedicina, aplicaciones), especialmente en relación con la seguridad, la integridad de los datos y la calidad de la atención?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(8,  'es', 'Seguridad e intercambio transfronterizo de datos', '¿Existen protocolos, políticas, marcos o procesos aceptados para apoyar el intercambio y almacenamiento seguro de datos transfronterizos? Nota: Esto incluye datos relacionados con la salud que llegan a un país, salen de un país, y/o son utilizados en un país relacionado con un individuo de otro país.'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(9,  'es', 'Integración de la salud digital en la formación previa a la prestación de servicios de salud y la formación profesional conexa (antes de su despliegue). ', '¿Es la salud digital parte del plan de estudios de los profesionales de la salud y de los profesionales de apoyo relacionados con la formación en salud, en general?       '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(10, 'es', 'Integración de la salud digital en la formación previa a la prestación de servicios de salud y la formación profesional conexa (antes de su despliegue). ', 'Específicamente, ¿la salud digital forma parte del plan de estudios de los médicos en formación médica?                                                 '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(11, 'es', 'Integración de la salud digital en la formación previa a la prestación de servicios de salud y la formación profesional conexa (antes de su despliegue). ', 'Específicamente, ¿la salud digital forma parte del plan de estudios de las enfermeras en formación previa al servicio?  '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(12, 'es', 'Integración de la salud digital en la formación previa a la prestación de servicios de salud y la formación profesional conexa (antes de su despliegue). ', 'Específicamente, ¿la salud digital forma parte de los planes de estudio de los profesionales de la salud y de los profesionales de los servicios de apoyo relacionados con la salud en la formación de los trabajadores de la salud de la comunidad?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(13, 'es', 'Integración de la salud digital en la formación previa a la prestación de servicios de salud y la formación profesional conexa (antes de su despliegue). ', 'Específicamente, ¿la salud digital forma parte del plan de estudios de los profesionales de la salud y de los profesionales de apoyo relacionados con la salud de la fuerza laboral en general? [Definidos como trabajadores comunitarios de salud, enfermeras, doctores, personal de salud aliado, gerentes/administradores de salud y tecnólogos]'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(14, 'es', '', 'Específicamente, ¿la salud digital forma parte del plan de estudios de los médicos de la fuerza laboral?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(15, 'es', '', 'Específicamente, ¿la salud digital forma parte del plan de estudios de las enfermeras de la fuerza laboral?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(16, 'es', '', 'Específicamente, ¿la salud digital forma parte del plan de estudios de los trabajadores de salud comunitarios de la fuerza laboral?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(17, 'es', 'Formación de personal sanitario digital', 'En general, ¿la capacitación en salud digital / informática de la salud / sistemas de información sanitaria / informática biomédica (en instituciones públicas o privadas) está produciendo trabajadores de salud digital capacitados'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(18, 'es', 'Formación de personal sanitario digital', 'Específicamente, ¿la capacitación en salud y/o informática biomédica (ya sea en instituciones públicas o privadas) está produciendo informáticos capacitados o especialistas en sistemas de información de salud? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(19, 'es', 'Madurez de las carreras de los profesionales de la salud digital del sector público', '¿Existen títulos profesionales y trayectorias profesionales en el sector público en el ámbito de la salud digital?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(20, 'es', 'Arquitectura sanitaria digital nacional y/o intercambio de información sanitaria', '¿Existe un marco arquitectónico nacional de salud digital (eHealth) y/o un intercambio de información sanitaria (HIE)?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(21, 'es', 'Estándares de información sanitaria', '¿Existen normas digitales de salud/información sanitaria para el intercambio de datos, la transmisión, la mensajería, la seguridad, la privacidad y el hardware?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(22, 'es', 'Preparación de la red', 'Extraer la puntuación del Índice de Preparación para la Red del FEM'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(23, 'es', 'Planificación y apoyo para el mantenimiento continuo de la infraestructura de salud digital', '¿Existe un plan articulado para apoyar la provisión y el mantenimiento de la infraestructura de salud digital (incluidos los equipos: computadoras, tabletas, teléfonos, suministros, programas informáticos, dispositivos, etc.)?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(24, 'es', 'Sistemas de salud digitales a escala nacional', 'Las prioridades de salud del sector público están respaldadas por sistemas de salud digitales a escala nacional. (Utilice una hoja de trabajo separada para enumerar las áreas prioritarias especificadas del país, si existen sistemas digitales y si esos sistemas son de escala nacional). [eg. El país X elige 4 áreas prioritarias, utiliza sistemas digitales para abordar 2 de las 4, y sólo 1 de ellas está a escala nacional, recibe una puntuación del 25%].'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(25, 'es', 'Gestión de la identidad digital de los proveedores de servicios, administradores e instalaciones para la salud digital, incluidos los datos de localización para la cartografía del SIG.', '¿Están disponibles, accesibles y actualizados los registros del sistema de salud de proveedores, administradores y establecimientos públicos (y privados, si procede) identificables de manera única? ¿Están los datos geoetiquetados para permitir el mapeo GIS?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(26, 'es', 'Gestión de la identidad digital de las personas para la salud', '¿Están disponibles, accesibles y actualizados para su uso con fines relacionados con la salud registros seguros o un índice maestro de pacientes de personas identificables de manera única? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(27, 'es', 'Gestión de la identidad digital de las personas para la salud', 'Específicamente, ¿existe un índice seguro de pacientes principales de individuos singularmente identificables disponibles, accesibles y actuales para su uso con fines relacionados con la salud? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(28, 'es', 'Gestión de la identidad digital de las personas para la salud', 'Específicamente, ¿existe un registro de nacimientos seguro de personas identificables de manera única, disponible, accesible y actual para su uso con fines relacionados con la salud? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(29, 'es', 'Gestión de la identidad digital de las personas para la salud', 'Específicamente, ¿existe un registro de defunciones seguro de personas identificables de manera única, disponible, accesible y actual para su uso con fines relacionados con la salud? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(30, 'es', 'Gestión de la identidad digital de las personas para la salud', 'Específicamente, ¿existe un registro de inmunización seguro de personas identificables de manera única, disponible, accesible y actual para su uso con fines relacionados con la salud?'); " +

                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(1,  'fr', 'Priorité accordée à la santé numérique au niveau national par l''intermédiaire d''organes et de mécanismes de gouvernance dédiés', 'Le pays dispose-t-il d''un ministère, d''un organisme ou d''un groupe de travail national distinct pour la santé numérique ?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(2,  'fr', 'Priorité accordée à la santé numérique à l''échelle nationale par le biais de la planification', 'Le pays dispose-t-il d''un ministère, d''un organisme ou d''un groupe de travail national distinct pour la santé numérique ?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(3,  'fr', 'Stratégie ou cadre national en matière de cybersanté et de santé numérique', 'Le pays dispose-t-il d''une stratégie ou d''un cadre pour la cybersanté ou la santé numérique et d''un plan de santé numérique chiffré ?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(4,  'fr', 'Financement public de la santé numérique', 'Quel est le pourcentage estimé (%) des dépenses publiques annuelles en santé consacrées à la santé numérique ?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(5,  'fr', 'Cadre juridique pour la protection des données (sécurité)', 'Existe-t-il une loi sur la sécurité des données (stockage, transmission, utilisation) qui concerne la santé numérique ?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(6,  'fr', 'Lois ou règlements sur la protection de la vie privée, la confidentialité et l''accès à l''information sur la santé (vie privée)', 'Existe-t-il une loi pour protéger la vie privée des personnes, régissant la propriété, l''accès et le partage des données  numériques sur la santé qui sont identifiables individuellement ?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(7,  'fr', 'Protocole pour la réglementation ou la certification de dispositifs et/ou de services de santé numériques', 'Existe-t-il des protocoles, des politiques, des cadres ou des processus acceptés régissant l''utilisation clinique et les soins aux patients des dispositifs médicaux et des services de santé numériques connectés (p. ex. télémédecine, applications), particulièrement en ce qui concerne la sécurité, l''intégrité des données et la qualité des soins ?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(8,  'fr', 'Sécurité et partage transfrontaliers des données', 'Existe-t-il des protocoles, des politiques, des cadres ou des processus acceptés pour soutenir l''échange et le stockage transfrontaliers sécurisés des données ? Note : Ceci inclut les données relatives à la santé entrant dans un pays, sortant d''un pays, et/ou étant utilisées dans un pays lié à un individu d''un autre pays.'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(9,  'fr', 'Intégration de la santé numérique dans la formation initiale des professionnels de la santé et dans la formation professionnelle connexe (avant le déploiement)   ', 'Un registre des décès sécurisé existe, est disponible et utilisé activement et comprend plus de 75 % de la population concernée. Les données sont disponibles, utilisées et conservées.'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(10, 'fr', 'Intégration de la santé numérique dans la formation initiale des professionnels de la santé et dans la formation professionnelle connexe (avant le déploiement)  ', 'Plus précisément, la santé numérique fait-elle partie du programme d''études des docteurs/médecins en formation médicale ?   '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(11, 'fr', 'Intégration de la santé numérique dans la formation initiale des professionnels de la santé et dans la formation professionnelle connexe (avant le déploiement)  ', 'Plus précisément, la santé numérique fait-elle partie du programme d''études des infirmières en formation initiale ?  '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(12, 'fr', 'Intégration de la santé numérique dans la formation initiale des professionnels de la santé et dans la formation professionnelle connexe (avant le déploiement)  ', 'Plus précisément, la santé numérique fait-elle partie du programme d''études des professionnels de la santé et des professionnels de soutien à la santé en formation pour les agents de santé communautaires ? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(13, 'fr', 'Intégration de la santé numérique dans la formation initiale des professionnels de la santé et dans la formation professionnelle connexe (avant le déploiement)  ', 'Plus précisément, la santé numérique fait-elle partie du programme d''études des professionnels de la santé et des personnels de soutien en santé en général ? [Définie comme étant les travailleurs de la santé communautaire, les infirmières, les médecins, les professionnels paramédicaux, les gestionnaires/administrateurs de la santé et les technologues.]'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(14, 'fr', '', 'Plus précisément, la santé numérique fait-elle partie du programme d''études des docteurs/médecins en milieu de travail ?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(15, 'fr', '', 'Plus précisément, la santé numérique fait-elle partie du programme d''études des infirmières en milieu de travail ?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(16, 'fr', '', 'Plus précisément, la santé numérique fait-elle partie du programme d''études des travailleurs de la santé communautaire sur le marché du travail ?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(17, 'fr', 'Formation du personnel de santé numérique', 'En général, la formation en santé numérique / informatique de la santé / systèmes d''information sur la santé / systèmes d''information sur la santé / programmes de diplôme en informatique biomédicale (dans des établissements publics ou privés) forme-t-elle des travailleurs en santé numérique ? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(18, 'fr', 'Formation du personnel de santé numérique', 'Plus précisément, la formation en informatique sanitaire et/ou biomédicale (dans des établissements publics ou privés) produit-elle des informaticiens ou des spécialistes des systèmes d''information sanitaire formés ? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(19, 'fr', 'Maturité des carrières professionnelles en santé numérique dans le secteur public', 'Existe-t-il des titres professionnels et des cheminements de carrière dans le secteur public en santé numérique ?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(20, 'fr', 'Cadre national d''architecture de santé numérique et/ou échange d''information sur la santé', 'Existe-t-il un cadre national d''architecture de santé numérique (cybersanté) et/ou d''échange d''information sur la santé ?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(21, 'fr', 'Normes d''information sur la santé', 'Existe-t-il des normes relatives à l''échange, à la transmission, à la messagerie, à la sécurité, à la confidentialité et au matériel d''échange de données et d''information sur la santé numérique ?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(22, 'fr', 'Disponibilité du réseau', 'Extraire le score de l''indice de préparation du réseau WEF'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(23, 'fr', 'Planification et soutien de l''entretien continu de l''infrastructure pour la santé numérique', 'Existe-t-il un plan articulé pour soutenir la fourniture et l''entretien de l''infrastructure de santé numérique (y compris l''équipement - ordinateurs, tablettes, téléphones, fournitures, logiciels, appareils, etc.'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(24, 'fr', 'Systèmes de santé numériques à l''échelle nationale', 'Les priorités du secteur public en matière de santé sont appuyées par des systèmes de santé numériques à l''échelle nationale. (Utilisez une feuille de travail distincte pour énumérer les domaines prioritaires spécifiés du pays, indiquer si des systèmes numériques sont en place et si ces systèmes sont à l''échelle nationale.) [ex. Le pays X choisit 4 domaines prioritaires, utilise des systèmes numériques pour traiter 2 des 4, dont 1 seulement à l''échelle nationale, reçoit un score de 25%].'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(25, 'fr', 'Gestion de l''identité numérique des fournisseurs de services, des administrateurs et des installations pour la santé numérique, y compris les données de localisation pour la cartographie SIG ', 'Les registres du système de santé: des prestataires, des administrateurs et des établissements publics (et privés s''il y a lieu) identifiables de façon unique sont-ils disponibles, accessibles et à jour ? Les données sont-elles géolocalisées pour permettre la cartographie SIG ?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(26, 'fr', 'Gestion de l''identité  des individus pour la santé numérique', 'Existe-t-il des registres sécurisés ou un fichier maître des patients contenant les noms de personnes identifiables de façon unique, accessibles et à jour pour utilisation à des fins liées à la santé ? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(27, 'fr', 'Gestion de l''identité  des individus pour la santé numérique', 'Plus précisément, existe-t-il un fichier index de référence sécurisé des patients contenant les noms de personnes identifiables de façon unique, accessible et à jour, qui peut être utilisé à des fins médicales ? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(28, 'fr', 'Gestion de l''identité  des individus pour la santé numérique', 'Plus précisément, existe-t-il un registre des naissances sûr, accessible et à jour, contenant les noms de personnes identifiables de façon unique, qui peut être utilisé à des fins médicales ? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(29, 'fr', 'Gestion de l''identité  des individus pour la santé numérique', 'Plus précisément, existe-t-il un registre sûr des décès de personnes identifiables de façon unique, accessible et à jour, qui peut être utilisé à des fins médicales ? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(30, 'fr', 'Gestion de l''identité  des individus pour la santé numérique', 'Plus précisément, existe-t-il un registre d''immunisation sûr, accessible et à jour, où l''on peut inscrire des personnes identifiables de façon unique, et qui peut être utilisé à des fins de promotion de la santé ? '); " +

                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(1,  'pt', 'Saúde digital priorizada a nível nacional através de órgãos/mecanismos dedicados à governação', 'O país tem um departamento / agência / grupo de trabalho nacional separado para a saúde digital?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(2,  'pt', 'Saúde Digital priorizada em nível nacional através do planeamento', 'A saúde digital está incluída e orçamentada na saúde nacional ou nas estratégias e/ou planos nacionais relevantes? Nota: Este indicador centra-se na inclusão da saúde digital ou da eSaúde na estratégia nacional de saúde.'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(3,  'pt', 'Estratégia ou quadro nacional de eSaúde/Estratégia ou quadro de saúde digital', 'O país dispõe de uma estratégia ou estrutura de saúde digital ou de eSaúde e de um plano de saúde digital orçamentado?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(4,  'pt', 'Financiamento público da saúde digital', 'Qual é a percentagem estimada (%) da despesa pública anual com a saúde comprometida com a saúde digital?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(5,  'pt', 'Quadro jurídico da protecção de dados (segurança)', 'Existe uma lei sobre a segurança dos dados (armazenamento, transmissão, utilização) que seja relevante para a saúde digital?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(6,  'pt', 'Leis ou Regulamentos de privacidade, confidencialidade e acesso a informações de saúde (Privacidade)', 'Existe uma lei para proteger a privacidade individual, que rege a propriedade, o acesso e a partilha de dados de saúde digitais identificáveis individualmente?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(7,  'pt', 'Protocolo para a regulamentação ou certificação de dispositivos e/ou serviços de saúde digitais', 'Existem protocolos, políticas, estruturas ou processos aceites que regem a utilização de dispositivos médicos conectados e serviços digitais de saúde (por exemplo, telemedicina, aplicações), particularmente em relação à segurança, integridade dos dados e qualidade dos cuidados?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(8,  'pt', 'Segurança e partilha de dados transfronteiriços', 'Existem protocolos, políticas, quadros ou processos aceites para apoiar a troca e armazenamento de dados transfronteiriços seguros? Nota: Isto inclui dados relacionados com a saúde que entram num país, saem de um país e/ou são utilizados num país relacionado com um indivíduo de outro país.'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(9,  'pt', 'Saúde digital integrada na saúde e na formação profissional conexa antes da implementação (antes da implementação)   ', 'A saúde digital faz parte do currículo dos profissionais de saúde e de apoio à saúde em formação, em geral?                                                                                                                                                                                                         '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(10, 'pt', 'Saúde digital integrada na saúde e na formação profissional conexa antes da implementação (antes da implementação)   ', 'Especificamente, a saúde digital faz parte do currículo para médicos/físicos em formação médica?                                                                                                                                                                                                         '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(11, 'pt', 'Saúde digital integrada na saúde e na formação profissional conexa antes da implementação (antes da implementação)   ', 'Especificamente, a saúde digital faz parte do currículo dos enfermeiros em formação pré-serviço?  '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(12, 'pt', 'Saúde digital integrada na saúde e na formação profissional conexa antes da implementação (antes da implementação)   ', 'Especificamente, a saúde digital faz parte do currículo dos profissionais de saúde e de apoio relacionado com a saúde na formação dos agentes comunitários de saúde?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(13, 'pt', 'Saúde digital integrada na saúde e na formação profissional conexa antes da implementação (antes da implementação)   ', 'Especificamente, a saúde digital faz parte do currículo dos profissionais de saúde e de apoio à saúde na força de trabalho em geral? [Definido como trabalhadores comunitários de saúde, enfermeiros, médicos, aliados da saúde, gestores/administradores de saúde e técnicos]'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(14, 'pt', '', 'Especificamente, a saúde digital faz parte do currículo para médicos/físicos na força de trabalho?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(15, 'pt', '', 'Especificamente, a saúde digital faz parte do currículo dos enfermeiros da força de trabalho?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(16, 'pt', '', 'Especificamente, a saúde digital faz parte do currículo dos profissionais de saúde comunitários na força de trabalho?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(17, 'pt', 'Formação dos profissionais de saúde digitais', 'Em geral, a formação em saúde digital / informática em saúde / sistemas de informação em saúde / programas de graduação em informática biomédica (em instituições públicas ou privadas) produz trabalhadores de saúde digitais treinados? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(18, 'pt', 'Formação dos profissionais de saúde digitais', 'Especificamente, o treino em saúde e/ou informática biomédica (em instituições públicas ou privadas) está a produzir informáticos treinados ou especialistas em sistemas de informação em saúde? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(19, 'pt', 'Maturidade das carreiras profissionais digitais de saúde no setor público', 'Existem títulos profissionais do setor público e planos de carreira em saúde digital?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(20, 'pt', 'Arquitectura digital nacional em matéria de saúde e/ou intercâmbio de informações sobre saúde', 'Existe um quadro de arquitetura nacional para a saúde digital (eSaúde) e/ou intercâmbio de informações de saúde (HIE) estabelecido?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(21, 'pt', 'Normas de informação sanitária', 'Existem padrões digitais de informação sobre saúde/saúde para troca de dados, transmissão, mensagens, segurança, privacidade e hardware?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(22, 'pt', 'Prontidão da rede', 'Extraia a pontuação do Índice de Prontidão de Rede do WEF'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(23, 'pt', 'Planeamento e suporte para a manutenção contínua da infraestrutura de saúde digital', 'Existe um plano articulado de apoio à infraestrutura de saúde digital (incluindo equipamentos - computadores/tablets/telefones, materiais, software, dispositivos, etc.), provisão e manutenção?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(24, 'pt', 'Sistemas de saúde digitais com escala nacional', 'As prioridades do sector público (por exemplo, 14 domínios incluídos na ISO TR 14639) são apoiadas por sistemas de saúde digitais à escala nacional. (Use uma folha de trabalho separada para determinar as áreas prioritárias especificadas do país, se os sistemas digitais estão instalados e se esses sistemas são à escala nacional). [por exemplo. O país X escolhe 4 áreas prioritárias, usa sistemas digitais para abordar 2 das 4, com apenas 1 a ser à escala nacional, recebe uma pontuação de 25%.]'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(25, 'pt', 'Gestão da identidade digital dos prestadores de serviços, administradores e instalações para a saúde digital, incluindo dados de localização para mapeamento SIG ', 'Os registros de sistemas de saúde de prestadores, administradores e instalações públicas (e privadas, se aplicável) identificáveis de forma única estão disponíveis, acessíveis e atualizados? A georreferenciação dos dados permite o mapeamento GIS?'); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(26, 'pt', 'Gestão da identidade digital dos indivíduos para a saúde', 'Os registros seguros ou um índice mestre de pacientes de indivíduos exclusivamente identificáveis estão disponíveis, acessíveis e atualizados para uso para fins relacionados à saúde? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(27, 'pt', 'Gestão da identidade digital dos indivíduos para a saúde', 'Especificamente, existe um índice mestre de pacientes seguro de indivíduos identificáveis de forma única, disponível, acessível e atual para uso para fins relacionados à saúde? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(28, 'pt', 'Gestão da identidade digital dos indivíduos para a saúde', 'Especificamente, existe um registo de nascimento seguro de indivíduos identificáveis de forma única, disponível, acessível e atual para uso para fins relacionados com a saúde? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(29, 'pt', 'Gestão da identidade digital dos indivíduos para a saúde', 'Especificamente, existe um registro seguro de óbitos de indivíduos identificáveis de forma única disponível, acessível e atual para uso com fins relacionados à saúde? '); " +
                "INSERT INTO i18n.health_indicator(indicator_id, language_id, name, definition) VALUES(30, 'pt', 'Gestão da identidade digital dos indivíduos para a saúde', 'Especificamente, existe um registro seguro de imunização de indivíduos com identificação única disponível, acessível e atual para uso para fins relacionados à saúde? '); "
        );
    }
}
