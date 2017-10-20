**Setup**

1. Create user and database
```
    CREATE USER gdhi WITH PASSWORD 'password';
    CREATE DATABASE gdhi OWNER gdhi;
```

2. Execute `sh ./utils/set-up-git-hooks.sh` from base folder to validate commit message format.

**To Run**

1. Run by executing
`./gradlew clean bootRun`