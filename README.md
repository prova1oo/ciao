
creazione PAT public-01 

da user profile -> token owner : "organization name"

il token deve avere permessi di:

- content
- workflow

settings:

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <activeProfiles>
    <activeProfile>github</activeProfile>
  </activeProfiles>

  <profiles>
    <profile>
      <id>github</id>
      <repositories>
        <repository>
          <id>central</id>
          <url>https://repo.maven.apache.org/maven2</url>
        </repository>
        <repository>
          <id>github</id>
          <!--
          Ensure that the repository URL adheres to the format: 
          https://maven.pkg.github.com/OWNER/REPO. 
          You have the flexibility to use a wildcard (*) in place 
          of the OWNER, allowing Maven to install dependencies from 
          the repository's owner dynamically.
          -->
          <url>https://maven.pkg.github.com/OWNER/*</url>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
      </repositories>
    </profile>
  </profiles>

  <servers>
    <server>
      <id>github</id>
      <!-- the name of the personal account or organization that owns the repository -->
      <username>OWNER</username>
      <password>${env.GITHUB_TOKEN}</password>
    </server>
  </servers>
</settings>
```

publishing

`altDeploymentRepository`	Specifies an alternative repository to which the project artifacts should be deployed (other than those specified in `distributionManagement`).
Format: `id::url`
- id: The id can be used to pick up the correct credentials from the settings.xml
- url: The location of the repository

`altReleaseDeploymentRepository`: 	The alternative repository to use when the project has a final version.

`altSnapshotDeploymentRepository`:	String	2.8	The alternative repository to use when the project has a snapshot version

```sh
mvn --no-transfer-progress --batch-mode --activate-profiles "$mvn_profiles" \
    -s "$mvn_settings_path" deploy \
<<<<<<< HEAD
    -Dhttps.protocols=TLSv1.2 \
    "-DaltDeploymentRepository=nexusa::http://nexus.a.it:8081/repository/maven-snapshots" \
    "-DaltReleaseDeploymentRepository=nexusa::http://nexus.a.it:8081/repository/maven-releases"
=======
    "-DaltDeploymentRepository=github::https://maven.pkg.github.com/enr/messages" \
    "-DaltReleaseDeploymentRepository=github::https://maven.pkg.github.com/enr/messages"
>>>>>>> 89b377e (wip)
```

con `alt*Deploymentrepository` eviti di dover scrivere nel pom questo:

```xml
<distributionManagement>
  <repository>
   <id>github</id>
   <name>Github</name>
   <url>https://maven.pkg.github.com/cal0610/medium-auth-commons</url>
  </repository>
  <snapshotRepository>
   <id>github</id>
   <url>https://maven.pkg.github.com/cal0610/medium-auth-commons</url>
  </snapshotRepository>
 </distributionManagement>
```

GH action per publisher .github/workflows/maven-publish.yml

```yaml
name: Publish Package
on:
  push:
    branches:
      - '*'
    tags:
      - '*'
jobs:
  build:
    runs-on: ubuntu-latest
    permissions:
      contents: read
      packages: write
    steps:
    - uses: actions/checkout@v4
    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'zulu'
    - name: Publish package
      run: ./mvnw deploy
      env:
        GITHUB_TOKEN: ${{ github.token }}
```

GH action per consumer

Create .github/workflows/build.yml in the root directory

Press enter or click to view image in full size

```yaml
name: Maven Package
on:
  push:
    branches:
      - '*'
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'zulu'
    # - name: Replace Maven Credentials
    #   run: |
    #     sed -i 's/USERNAME/${{ secrets.USERNAME }}/g' .github/settings.xml
    #     sed -i 's/PASSWORD/${{ secrets.PASSWORD }}/g' .github/settings.xml
    - name: Install
      run: ./mvnw clean install -s .github/settings.xml
      env:
        GITHUB_TOKEN: ${{ secrets.github.token }}
```

RRF

```sh
$cat .mvn/rrf/groupId-github.txt
com.github.atoito
```



