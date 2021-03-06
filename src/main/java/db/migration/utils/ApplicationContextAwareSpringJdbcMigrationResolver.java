package db.migration.utils;

import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationType;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.migration.MigrationChecksumProvider;
import org.flywaydb.core.api.migration.MigrationInfoProvider;
import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.flywaydb.core.api.resolver.MigrationResolver;
import org.flywaydb.core.api.resolver.ResolvedMigration;
import org.flywaydb.core.internal.resolver.MigrationInfoHelper;
import org.flywaydb.core.internal.resolver.ResolvedMigrationComparator;
import org.flywaydb.core.internal.resolver.ResolvedMigrationImpl;
import org.flywaydb.core.internal.resolver.spring.SpringJdbcMigrationExecutor;
import org.flywaydb.core.internal.util.ClassUtils;
import org.flywaydb.core.internal.util.Pair;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Component
public class ApplicationContextAwareSpringJdbcMigrationResolver implements MigrationResolver {

    @Autowired
    private ApplicationContext applicationContext;

    @SuppressWarnings("unchecked")
    @Override
    public Collection<ResolvedMigration> resolveMigrations() {
        System.out.println("--------------------------------------");
        System.out.println("ApplicationContextAwareSpringJdbcMigrationResolver");
        System.out.println("--------------------------------------");
        // get all beans of type SpringJdbcMigration from the application context
        Map<String, SpringJdbcMigration> springJdbcMigrationBeans =
                this.applicationContext.getBeansOfType(SpringJdbcMigration.class);

        ArrayList<ResolvedMigration> resolvedMigrations = new ArrayList<>();

        // resolve the db.migration and populate it with the db.migration info
        for (SpringJdbcMigration springJdbcMigrationBean : springJdbcMigrationBeans.values()) {
            ResolvedMigrationImpl resolvedMigration = extractMigrationInfo(springJdbcMigrationBean);
            resolvedMigration.setPhysicalLocation(ClassUtils.getLocationOnDisk(springJdbcMigrationBean.getClass()));
            resolvedMigration.setExecutor(new SpringJdbcMigrationExecutor(springJdbcMigrationBean));

            resolvedMigrations.add(resolvedMigration);
        }

        resolvedMigrations.sort(new ResolvedMigrationComparator());
        return resolvedMigrations;
    }

    ResolvedMigrationImpl extractMigrationInfo(SpringJdbcMigration springJdbcMigration) {
        Integer checksum = null;
        if (springJdbcMigration instanceof MigrationChecksumProvider) {
            MigrationChecksumProvider version = (MigrationChecksumProvider) springJdbcMigration;
            checksum = version.getChecksum();
        }

        String description;
        MigrationVersion version1;
        if (springJdbcMigration instanceof MigrationInfoProvider) {
            MigrationInfoProvider resolvedMigration = (MigrationInfoProvider) springJdbcMigration;
            version1 = resolvedMigration.getVersion();
            description = resolvedMigration.getDescription();
            if (!StringUtils.hasText(description)) {
                throw new FlywayException("Missing description for db.migration " + version1);
            }
        } else {
            String resolvedMigration1 = ClassUtils.getShortName(springJdbcMigration.getClass());
            if (!resolvedMigration1.startsWith("V") && !resolvedMigration1.startsWith("R")) {
                throw new FlywayException("Invalid Jdbc db.migration class name: " + springJdbcMigration.getClass()
                        .getName() + " => ensure it starts with V or R," + " " +
                        "or implement org.flywaydb.core.api.db.migration.MigrationInfoProvider for non-default naming");
            }

            String prefix = resolvedMigration1.substring(0, 1);
            Pair info = MigrationInfoHelper.extractVersionAndDescription(resolvedMigration1, prefix, "__", "", false);
            version1 = (MigrationVersion) info.getLeft();
            description = (String) info.getRight();
        }

        ResolvedMigrationImpl resolvedMigration2 = new ResolvedMigrationImpl();
        resolvedMigration2.setVersion(version1);
        resolvedMigration2.setDescription(description);
        resolvedMigration2.setScript(springJdbcMigration.getClass().getName());
        resolvedMigration2.setChecksum(checksum);
        resolvedMigration2.setType(MigrationType.SPRING_JDBC);
        return resolvedMigration2;
    }
}