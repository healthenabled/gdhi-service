**Setup**

1. Create user and database
```
    CREATE USER gdhi WITH PASSWORD 'password';
    CREATE DATABASE gdhi OWNER gdhi;
```
2. Install uuid extension

connect to DB gdhi
```
    create extension if not exists "uuid-ossp";
```

3. Execute `sh ./utils/set-up-git-hooks.sh` from base folder to validate commit message format.

**To Run**

1. Run by executing
`./gradlew clean bootRun`

**INTEGRATION TESTS**
1. Create Test user and database
```
    CREATE USER gdhi_test WITH PASSWORD 'testpassword';
    CREATE DATABASE gdhi_test OWNER gdhi_test;
```

2. Install uuid extension

connect to DB gdhi_test
```
    create extension if not exists "uuid-ossp";
```