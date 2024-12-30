def call(String SonarQubeAPI, String Projectname, String ProjectKey) {
    withSonarQubeEnv("${SonarQubeAPI}") {  // Use SonarQube environment variables from Jenkins
        sh """
        $SONAR_HOME/bin/sonar-scanner \
        -Dsonar.projectName=${Projectname} \
        -Dsonar.projectKey=${ProjectKey} \
        -Dsonar.java.binaries=. \
        -Dsonar.exclusions=**/*.java \
        -X
        """
    }
}
