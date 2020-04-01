package db.migration;

import org.springframework.jdbc.core.JdbcTemplate;

public class V3_21__Update_i18n_Score_Definition_ar extends BaseMigration {
    @Override
    public void doMigrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("UPDATE i18n.score_definition SET definition = " +
                "'تتمتع هيكلية الإدارة وأي مجموعات عمل ذات صلة بنطاق عمل (SOW) وتعقد اجتماعات و/ أو مشاورات منتظمة بمشاركة أصحاب المصلحة.'" +
                "WHERE indicator_id=1 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET definition = " +
                "'يتم تطبيق الصحة الرقمية وتقييمها وتحسينها بشكل دوري في إطار الخطط الوطنية للصحة  أو الاستراتيجيات و/ أو الخطط الوطنية الأخرى ذات الصلة.'" +
                "WHERE indicator_id=2 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا يوجد منهاج للصحة الرقمية للأطباء كجزء من متطلبات التدريب قبل الخدمة.' " +
                "WHERE indicator_id=20 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تم اقتراح مناهج الصحة الرقمية وهي قيد المراجعة كجزء من متطلبات التدريب قبل الخدمة للأطباء.' " +
                "WHERE indicator_id=20 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تنفيذ مناهج الصحة الرقمية ويتلقى ما بين 0-25 ٪ من الأطباء تدريبا قبل الخدمة.' " +
                "WHERE indicator_id=20 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تدريس الصحة الرقمية في المؤسسات ذات الصلة ويتلقى ما بين 50-75 ٪ من الأطباء تدريبا قبل الخدمة.' " +
                "WHERE indicator_id=20 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تدريس الصحة الرقمية في المؤسسات ذات الصلة مع تلقي أكثر من 75٪ من الأطباء تدريباً قبل الخدمة.' " +
                "WHERE indicator_id=20 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا يوجد منهاج للصحة الرقمية لكادر التمريض كجزء من متطلبات التدريب قبل الخدمة.' " +
                "WHERE indicator_id=21 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تم اقتراح مناهج الصحة الرقمية وهي قيد المراجعة كجزء من متطلبات التدريب قبل الخدمة لكادر التمريض.' " +
                "WHERE indicator_id=21 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يجري تنفيذ مناهج الصحة الرقمية ويتلقى ما بين 0-25 ٪ من المهنيين الصحيين تدريبا قبل الخدمة.' " +
                "WHERE indicator_id=21 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تدريس الصحة الرقمية في المؤسسات ذات الصلة ويتلقى ما بين 50-75 ٪ من كادر التمريض تدريبا قبل الخدمة.' " +
                "WHERE indicator_id=21 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='الصحة الرقمية تدرس في المؤسسات ذات الصلة مع أكثر من 75 ٪ من الممرضات يتلقون التدريب قبل الخدمة.' " +
                "WHERE indicator_id=21 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا يوجد منهاج للصحة الرقمية للمهنيين الصحيين كجزء من متطلبات التدريب قبل الخدمة للعاملين في مجال الصحة المجتمعية.' " +
                "WHERE indicator_id=22 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تم اقتراح مناهج الصحة الرقمية وهي قيد المراجعة كجزء من متطلبات التدريب قبل الخدمة للعاملين في مجال الصحة المجتمعية.' " +
                "WHERE indicator_id=22 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تنفيذ المناهج الصحية الرقمية ويتلقى ما بين 0-25 ٪ من العاملين في مجال الصحة المجتمعية تدريبا قبل الخدمة.' " +
                "WHERE indicator_id=22 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تدريس الصحة الرقمية في المؤسسات ذات الصلة ويتلقى ما يتراوح بين 50-75 ٪ من العاملين في مجال الصحة المجتمعية تدريباً قبل الخدمة.' " +
                "WHERE indicator_id=22 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تدريس الصحة الرقمية في المؤسسات ذات الصلة حيث يتلقى 75٪ من العاملين في مجال الصحة المجتمعية تدريباً قبل الخدمة.' " +
                "WHERE indicator_id=22 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا يوجد منهج للصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) للمهنيين الصحيين في القوى العاملة.' " +
                "WHERE indicator_id=10 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تم اقتراح مناهج الصحة الرقمية وهي قيد المراجعة كجزء من التدريب أثناء الخدمة (التعليم المستمر) للمهنيين الصحيين في القوى العاملة.' " +
                "WHERE indicator_id=10 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تطبيق منهج الصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) للمهنيين الصحيين لـ 0-25 ٪ من القوى العاملة.' " +
                "WHERE indicator_id=10 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تطبيق منهج الصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) للمهنيين الصحيين لـ 50-75 ٪ من القوى العاملة.' " +
                "WHERE indicator_id=10 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تطبيق منهج الصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) لأكثر من 75٪ من المهنيين الصحيين في القوى العاملة.' " +
                "WHERE indicator_id=10 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا يوجد منهج للصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) للأطباء.' " +
                "WHERE indicator_id=23 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تم اقتراح مناهج الصحة الرقمية وهي قيد المراجعة كجزء من التدريب أثناء الخدمة (التعليم المستمر) للأطباء.' " +
                "WHERE indicator_id=23 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تطبيق منهج الصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) لـ 0-25 ٪ من الأطباء.' " +
                "WHERE indicator_id=23 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تطبيق منهج الصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) لـ50-75 ٪ من الأطباء.' " +
                "WHERE indicator_id=23 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تطبيق منهج الصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) لأكثر من 75 ٪ من الأطباء.' " +
                "WHERE indicator_id=23 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا يوجد منهج للصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) للممرضين في القوى العاملة.' " +
                "WHERE indicator_id=24 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تم اقتراح مناهج الصحة الرقمية وهي قيد المراجعة كجزء من التدريب أثناء الخدمة (التعليم المستمر) للممرضين في القوى العاملة.' " +
                "WHERE indicator_id=24 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تطبيق منهج الصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) لـ 0-25٪ من الممرضين في القوى العاملة.' " +
                "WHERE indicator_id=24 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تطبيق منهج الصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) لـ50-75 ٪ من الممرضين في القوى العاملة.' " +
                "WHERE indicator_id=24 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تطبيق منهج الصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) لأكثر من 75 ٪ من الممرضين في القوى العاملة.' " +
                "WHERE indicator_id=24 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا يوجد منهج للصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) للعاملين في مجال الصحة المجتمعية في القوى العاملة.' " +
                "WHERE indicator_id=25 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تم اقتراح مناهج الصحة الرقمية وهي قيد المراجعة كجزء من التدريب أثناء الخدمة (التعليم المستمر) للعاملين في مجال الصحة المجتمعية في القوى العاملة.' " +
                "WHERE indicator_id=25 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تطبيق منهج الصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) لـ 0-25 ٪ من العاملين في مجال الصحة المجتمعية في القوى العاملة.' " +
                "WHERE indicator_id=25 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تطبيق منهج الصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) لـ50-75 ٪ من العاملين في مجال الصحة المجتمعية في القوى العاملة.' " +
                "WHERE indicator_id=25 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تطبيق منهج الصحة الرقمية كجزء من التدريب أثناء الخدمة (التعليم المستمر) لأكثر من 75 ٪ من العاملين في مجال الصحة المجتمعية في القوى العاملة.' " +
                "WHERE indicator_id=25 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا يوجد تدريب متاح للعاملين في مجال الصحة الرقمية في البلاد.' " +
                "WHERE indicator_id=11 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تم تقييم احتياجات العاملين في مجال الصحة الرقمية وتحديد الثغرات ويجري اعداد خيارات التدريب.' " +
                "WHERE indicator_id=11 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='التدريب الاحترافي متاح، لكن لم يتم نشر الخريجين بعد.' " +
                "WHERE indicator_id=11 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='المهنيين المدربين في مجال الصحة الرقمية متوفرين وتم نشرهم، لكن لا تزال هناك ثغرات أساسية في الموظفين.' " +
                "WHERE indicator_id=11 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='توفر أعداد كافية من المتخصصين في مجال الصحة الرقمية المدربين لدعم الاحتياجات في مجال الصحة الرقمية على المستوى الوطني.' " +
                "WHERE indicator_id=11 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا يوجد تدريب متاح في مجال المعلوماتية أو نظم المعلومات الصحية المتاحة في البلاد.' " +
                "WHERE indicator_id=26 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تم تقييم احتياجات القوى العاملة في مجال المعلوماتية الصحية وتحديد الثغرات وخيارات التدريب قيد التطوير.' " +
                "WHERE indicator_id=26 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='التدريب المهني في مجال المعلوماتية الصحية متاح، لكن لم يتم نشر الخريجين بعد.' " +
                "WHERE indicator_id=26 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='مهنيو المعلوماتية المدربون متوفرون وتم نشرهم، لكن لا تزال هناك فجوات أساسية في الموظفين.' " +
                "WHERE indicator_id=26 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='توفر أعداد كافية من المتخصصين في المعلوماتية الصحية المدربين لدعم احتياجات نظام المعلومات الصحية الوطني.' " +
                "WHERE indicator_id=26 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا توجد إستراتيجية أو سياسة أو دليل للقوى العاملة تعترف بالصحة الرقمية. توزيع القوى العاملة الصحية الرقمية مخصص.' " +
                "WHERE indicator_id=12 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يُظهر تقييم الاحتياجات الوطنية عدد وأنواع المهارات اللازمة لدعم الصحة الرقمية مع التركيز بشكل واضح على كوادر تدريب العاملات الصحيات.' " +
                "WHERE indicator_id=12 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تعيين أدوار ومسؤوليات موظفي الصحة الرقمية إلى القوى العاملة وخطط العمل الحكومية و 25-50 ٪ من القوى العاملة الصحية الرقمية المطلوبة في القطاع العام.' " +
                "WHERE indicator_id=12 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='توجد سياسة للموارد البشرية وخطة استراتيجية تحدد المهارات والوظائف اللازمة لدعم الصحة الرقمية مع التركيز بشكل واضح على كوادر تدريب العاملات الصحيات وما يقدر بنحو 50-75 ٪ من القوى العاملة الصحية الرقمية في القطاع العام.' " +
                "WHERE indicator_id=12 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='توجد خطة طويلة الأجل لتنمية ودعم الموظفين الذين يتمتعون بالمهارات اللازمة للحفاظ على الصحة الرقمية على المستويين الوطني ودون الوطني مع التركيز بشكل واضح على كوادر تدريب العاملات في المجال الصحي مع شغل ما يقدر بـ 75٪ من الوظائف المطلوبة. توجد أنظمة لإدارة الأداء لضمان نمو واستدامة القوى العاملة الصحية الرقمية مع توفير ما يكفي لتلبية الاحتياجات الصحية الرقمية وقلة معدل دوران الموظفين.' " +
                "WHERE indicator_id=12 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا يوجد إطار هيكلي وطني للصحة الرقمية (الصحة الإلكترونية) و / أو تبادل المعلومات الصحية (HIE).' " +
                "WHERE indicator_id=13 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تم اقتراح بنية الصحة الرقمية الوطنية و / أو تبادل المعلومات الصحية (HIE) ، ولكن لم تتم الموافقة عليها بما في ذلك الطبقات الدلالية والنحوية والتنظيمية.' " +
                "WHERE indicator_id=13 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='البنية الصحية الرقمية الوطنية و / أو تبادل المعلومات الصحية (HIE) قابلة للتشغيل وتوفر الوظائف الأساسية، مثل المصادقة والترجمة والتخزين، ودليل يظهر البيانات المتاحة وكيفية الوصول إليها، وتفسير البيانات.' " +
                "WHERE indicator_id=13 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تقوم الحكومة بإدارة وتنفيذ البنية الصحية الرقمية الوطنية و / أو تبادل المعلومات الصحية (HIE)، والتي يتم تنفيذها بالكامل وفقًا للمعايير المعتمدة في هذا القطاع.' " +
                "WHERE indicator_id=13 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='توفر البنية الصحية الرقمية الوطنية و / أو تبادل المعلومات الصحية (HIE) وظائف تبادل البيانات الأساسية وتتم مراجعتها وتحديثها بشكل دوري لتلبية احتياجات بنية الصحة الرقمية المتغيرة. يتوفر التعلم المستمر والابتكار ومراقبة الجودة. يتم استخدام البيانات بنشاط للتخطيط الاستراتيجي الصحي الوطني واعداد الميزانية.' " +
                "WHERE indicator_id=13 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا توجد معايير للصحة الرقمية / المعلومات الصحية لتبادل البيانات ونقلها والرسائل والأمن والخصوصية والأجهزة.' " +
                "WHERE indicator_id=14 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='توجد بعض معايير المعلومات الصحية / الصحة الرقمية لتبادل البيانات ونقلها والرسائل والأمن والخصوصية والأجهزة التي تم اعتمادها و / أو استخدامها.' " +
                "WHERE indicator_id=14 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='نُشرت معايير المعلومات الصحية / الصحة الرقمية لتبادل البيانات ونقلها والرسائل والأمن والخصوصية والأجهزة في البلاد تحت قيادة الحكومة.' " +
                "WHERE indicator_id=14 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تستخدم المعايير التقنية القائمة على قطاع المعلومات الصحية / الصحية الرقمية لتبادل البيانات ونقلها والرسائل والأمن والخصوصية والأجهزة في غالبية التطبيقات والأنظمة لضمان توافر البيانات عالية الجودة. يتم إجراء اختبار المطابقة بشكل روتيني للمصادقة على المنفذين.' " +
                "WHERE indicator_id=14 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يتم تحديث معايير البيانات بشكل روتيني وتستخدم البيانات بنشاط لرصد وتقييم النظام الصحي والتخطيط الاستراتيجي للصحة الوطنية ووضع الميزانية.' " +
                "WHERE indicator_id=14 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='3.3 - 1.0' " +
                "WHERE indicator_id=15 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='4.0 - 3.3<' " +
                "WHERE indicator_id=15 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='5.0 - 4.0<' " +
                "WHERE indicator_id=15 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='5.4 - 5.0<' " +
                "WHERE indicator_id=15 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='>5.4 - 7.0' " +
                "WHERE indicator_id=15 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا توجد خطة واضحة لدعم البنية التحتية للصحة الرقمية (بما في ذلك المعدات - أجهزة الكمبيوتر / الأجهزة اللوحية / الهواتف ، اللوازم ، البرامج ، الأجهزة ، إلخ) وتوفيرها وصيانتها.' " +
                "WHERE indicator_id=16 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تم تطوير خطة لدعم البنية التحتية للصحة الرقمية (بما في ذلك المعدات - أجهزة الكمبيوتر / الأجهزة اللوحية / الهواتف ، اللوازم ، البرامج ، الأجهزة ، إلخ) ، ولكنها لم تنفذ.' " +
                "WHERE indicator_id=16 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تم تنفيذ خطة لدعم البنية الأساسية للصحة الرقمية (بما في ذلك المعدات - أجهزة الكمبيوتر / الأجهزة اللوحية / الهواتف ، اللوازم ، البرامج ، الأجهزة ، إلخ) جزئياً ، ولكن هناك 0-25٪ من البنية التحتية الصحية الرقمية اللازمة في قطاع خدمات الرعاية الصحية العامة متاح وقيد الاستخدام.' " +
                "WHERE indicator_id=16 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تم تنفيذ خطة لدعم البنية التحتية للصحة الرقمية (بما في ذلك المعدات - أجهزة الكمبيوتر / الأجهزة اللوحية / الهواتف ، اللوازم ، البرامج ، الأجهزة ، إلخ) بشكل جزئي ومتسق وهناك 25-50٪ من البنية التحتية الصحية الرقمية اللازمة في الرعاية الصحية العامة قطاع الخدمات متاح وقيد الاستخدام.' " +
                "WHERE indicator_id=16 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='البنية التحتية الصحية الرقمية (بما في ذلك المعدات - أجهزة الكمبيوتر / الأجهزة اللوحية / الهواتف ، اللوازم ، البرامج ، الأجهزة ، وما إلى ذلك) متوفرة ، قيد الاستخدام ، ويتم تحديثها بانتظام في أكثر من 75٪ من قطاع خدمات الرعاية الصحية العامة.' " +
                "WHERE indicator_id=16 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='مجالات الأولوية الوطنية غير مدعومة بالصحة الرقمية على أي نطاق.' " +
                "WHERE indicator_id=17 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='هناك القليل من مجالات الأولوية الوطنية مدعومة بالصحة الرقمية ، وبدأ التنفيذ (أقل من 25٪ من مجالات الأولوية).' " +
                "WHERE indicator_id=17 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='توجد بعض المجالات ذات الأولوية الوطنية التي تدعمها أنظمة الصحة الرقمية الموسعة (25-50 ٪ من مجالات الأولوية).' " +
                "WHERE indicator_id=17 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='غالبية المجالات ذات الأولوية الوطنية (50-75٪ من مجالات الأولوية) ، وليس جميعها ، مدعومة بأنظمة صحية رقمية موسعة.' " +
                "WHERE indicator_id=17 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='جميع المجالات ذات الأولوية الوطنية التي تدعمها أنظمة الصحة الرقمية على المستوى الوطني (> 75 ٪) مع أنظمة الرصد والتقييم والنتائج.' " +
                "WHERE indicator_id=17 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='سجلات النظام الصحي لمقدمي الخدمات ، والمسؤولين ، والمرافق العامة التي يمكن تحديدها بشكل فريد (والخاصة إذا كان ذلك ممكنًا) غير متوفرة ومتاحة وحديثة.' " +
                "WHERE indicator_id=18 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يجري تطوير سجلات النظام الصحي للموردين والمسؤولين والمرافق العامة التي يمكن تحديدها بشكل فريد (والخاصة إذا كان ذلك ممكنًا) ولكنها غير متاحة للاستخدام.' " +
                "WHERE indicator_id=18 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='سجلات النظام الصحي لمقدمي الخدمات ، والمسؤولين ، والمرافق العامة التي يمكن التعرف عليها بشكل فريد (والخاصة إذا كان ذلك ممكنًا) متاحة للاستخدام ، ولكنها غير مكتملة ومتاحة جزئيًا ، وتستخدم بشكل متقطع وغير منتظم.' " +
                "WHERE indicator_id=18 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='سجلات النظام الصحي لمقدمي الخدمات ، والمسؤولين ، والمرافق العامة التي يمكن تحديدها بشكل فريد (والخاصة إذا كان ذلك ممكنًا) متاحة ، وتستخدم ، ويتم تحديثها وصيانتها بانتظام. البيانات ذات علامة جغرافية لتمكين تعيين GIS.' " +
                "WHERE indicator_id=18 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='تتوفر سجلات النظام الصحي لمقدمي الخدمات ، والمسؤولين ، والمرافق العامة التي يمكن تحديدها بشكل فريد (والخاصة إذا كان ذلك قابلاً للتطبيق) ، محدثة مع البيانات ذات العلامات الجغرافية ، وتستخدم في النظام الصحي والتخطيط الاستراتيجي للخدمة والميزانية.' " +
                "WHERE indicator_id=18 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا يوجد سجل آمن أو فهرس المريض الرئيسي موجود.' " +
                "WHERE indicator_id=19 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل آمن ، لكنه غير مكتمل / جزئيًا ، واستخدامه ، وصيانته بشكل غير منتظم.' " +
                "WHERE indicator_id=19 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل آمن ومتوفر وقيد الاستخدام ويشمل <25٪ من السكان المعنيين.' " +
                "WHERE indicator_id=19 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل آمن ومتوفر وقيد الاستخدام ويشمل 25-50٪ من السكان المعنيين.' " +
                "WHERE indicator_id=19 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل آمن ومتوفر وقيد الاستخدام ويشمل 75٪ من السكان المعنيين. البيانات متاحة ، وتستخدم ، وبرعاية.' " +
                "WHERE indicator_id=19 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا يوجد مؤشر رئيسي آمن للمريض.' " +
                "WHERE indicator_id=27 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد مؤشر رئيسي للمريض ، ولكنه غير مكتمل / جزئيًا ، مستخدم ، وغير منتظم.' " +
                "WHERE indicator_id=27 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد مؤشر رئيسي للمريض ، متوفر ومتاح ، ويشمل <25٪ من السكان المعنيين.' " +
                "WHERE indicator_id=27 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد مؤشر رئيسي للمريض ، متوفر ومتاح ، ويشمل 25-50٪ من السكان المعنيين.' " +
                "WHERE indicator_id=27 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد مؤشر رئيسي للمريض ، وهو متاح ويعمل بشكل نشط ويشمل> 75٪ من السكان المعنيين. البيانات متاحة ، وتستخدم ، وبرعاية.' " +
                "WHERE indicator_id=27 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا يوجد سجل ولادة آمن.' " +
                "WHERE indicator_id=28 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل ولادة آمن ، لكنه غير مكتمل / متاح جزئيًا ، واستخدامه ، وصيانته بشكل غير منتظم.' " +
                "WHERE indicator_id=28 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل ولادة آمن ومتوفر وقيد الاستخدام ويشمل <25٪ من السكان المعنيين.' " +
                "WHERE indicator_id=28 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل ولادة آمن ، وهو متاح وقيد الاستخدام ويشمل 25-50 ٪ من السكان المعنيين.' " +
                "WHERE indicator_id=28 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل ولادة آمن ، وهو متاح وقيد الاستخدام ، ويشمل> 75٪ من السكان المعنيين. البيانات متاحة ، وتستخدم ، وبرعاية.' " +
                "WHERE indicator_id=28 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا يوجد سجل وفاة آمن.' " +
                "WHERE indicator_id=29 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل وفاة آمن ، لكنه غير مكتمل / جزئيًا ، ومستخدم ، وصيانته بشكل غير منتظم.' " +
                "WHERE indicator_id=29 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل آمن للوفاة ، وهو متاح وقيد الاستخدام ، ويشمل <25٪ من السكان المعنيين.' " +
                "WHERE indicator_id=29 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل آمن للوفاة ، وهو متاح وقيد الاستخدام ويشمل 25-50 ٪ من السكان المعنيين.' " +
                "WHERE indicator_id=29 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل آمن للوفاة ، وهو متاح وقيد الاستخدام ، ويشمل> 75٪ من السكان المعنيين. البيانات متاحة ، وتستخدم ، وبعناية.' " +
                "WHERE indicator_id=29 AND score=5 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='لا يوجد سجل تحصين آمن.' " +
                "WHERE indicator_id=30 AND score=1 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل آمن للتحصين ، لكنه غير مكتمل / متاح جزئيًا ، واستخدامه ، وصيانته بشكل غير منتظم.' " +
                "WHERE indicator_id=30 AND score=2 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل آمن للتحصين ، وهو متاح وقيد الاستخدام ، ويشمل <25٪ من السكان المعنيين.' " +
                "WHERE indicator_id=30 AND score=3 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل آمن للتحصين ، وهو متاح وقيد الاستخدام ، ويشمل 25-50 ٪ من السكان المعنيين.' " +
                "WHERE indicator_id=30 AND score=4 AND language_id='ar'");
        jdbcTemplate.update("UPDATE i18n.score_definition SET " +
                "definition='يوجد سجل آمن للتحصين ، وهو متاح وقيد الاستخدام ، ويشمل أكثر من 75٪ من السكان المعنيين. البيانات متاحة ، وتستخدم ، وبرعاية.' " +
                "WHERE indicator_id=30 AND score=5 AND language_id='ar'");
    }
}

