
# Library dash

![CI](https://github.com/enr/dash/workflows/CI/badge.svg)

[![](https://jitpack.io/v/enr/dash.svg)](https://jitpack.io/#enr/dash)

Java library


CI (test, check su formattazione, produzione documentazione...):

```
mvn install -Pci
```

Profilo fast (no test, checks, etc...):

```
mvn install -Pfast
```

Tag di sorgenti con modifiche versioni dei pom:

```
./.sdlc/release [RELEASE_VERSION] [NEXT_SNAPSHOT]
```

Esempio, portare la versione `0.0.1-SNAPSHOT` alla versione `0.0.1` e tornare a `0.0.2-SNAPSHOT`:

```
.sdlc/release 0.0.1 0.0.2
```

Profilo release (deployment di javadoc e sources):

```
mvn deploy -Prelease
```

Riparare formattazione (profilo fmt):

```
mvn -Pfmt
```

Test coverage:

```
mvn org.jacoco:jacoco-maven-plugin:0.8.11:prepare-agent install org.jacoco:jacoco-maven-plugin:0.8.11:report-aggregate -Daggregate=true
```

Generate Java docs:

```
mvn org.apache.maven.plugins:maven-javadoc-plugin:3.4.1:aggregate
```

Download sources e javadoc delle dipendenze:

```
mvn dependency:sources dependency:resolve -Dhttps.protocols=TLSv1.2
mvn dependency:sources dependency:resolve -Dclassifier=javadoc -Dhttps.protocols=TLSv1.2
```
