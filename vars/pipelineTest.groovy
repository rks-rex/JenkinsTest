
def call() {
    @Library('pipeline_helper') import helperCollection.helperFunctions

    def helper = new helperFunctions()
    pipeline {
        agent {
            label 'docker-build'
        }
        options {
            timeout(time: 90, unit: 'MINUTES')
            disableConcurrentBuilds()
        }
        environment {
            MLFW_BRANCH_NAME = makeMLFWBranchName()
            MLFW_ENV = branchToMLFWEnv(MLFW_BRANCH_NAME)

            DOCKER_REGISTRY_HOST = 'tasktrack.telekom.at'
            DOCKER_TARGET_REPO = branchToDockerRepo(MLFW_BRANCH_NAME)
            DOCKER_BRANCH_TAG = makeDockerTagName(MLFW_BRANCH_NAME)

            JENKINS_DEPLOY_TOKEN = mlfwEnvToOsJenkinsDeployToken(MLFW_ENV)
            CREDENTIAL_ANEN_ID = mlfwEnvToAnenCredentialsId(MLFW_ENV)
            OS_SERVER = mlfwEnvToOSClusterURI(MLFW_ENV)
            OS_PROJECT = mlfwEnvToOSProject(MLFW_ENV)
            MODEL_NAME = gitRepositoryToModelName()
            SONAR_PROJECT_KEY = gitRepositoryToSonarProjectKey()
            FULL_TEST_POD_NAME = makePodName(MLFW_BRANCH_NAME, MODEL_NAME, 'fulltest')
            UNIT_TEST_POD_NAME = makePodName(MLFW_BRANCH_NAME, MODEL_NAME, 'unittest')

            def props = readProperties file: 'config.ini'
            COVERAGE_FILE = return_identic(props.COVERAGE_FILE)
            UNIT_EXECUTION_FILE_JENKINS = return_identic(props.UNIT_EXECUTION_FILE_JENKINS)

            /** Dev Resources - in Prod Resources are defined via Airflow **/
            CPU = "${cpuUnits}"
            RAM = "${ramGb}"

            /** Skip stages in Pipeline **/
            SKIP_UNIT_TESTS = "${skipUnitTests}"
            SKIP_FULL_TEST = "${skipFullTests}"

            /** run arguments for full test **/
            SKIP_UNIT_TESTS = "${skipUnitTests}"
            SKIP_FULL_TEST = "${skipFullTests}"
            arg1 = "${arg1}"
            arg2 = "${arg2}"
            arg3 = "${arg3}"
            arg4 = "${arg4}"
            arg5 = "${arg5}"
        }

        stages {
            stage('Print Jobinfo') {
                steps {
//          print "Name of the model:   : ${env.MODEL_NAME}"
//          print "Building branch      : ${env.MLFW_BRANCH_NAME}"
//          print "MLFW environment     : ${env.MLFW_ENV}"
//          print "Docker registry host : ${env.DOCKER_REGISTRY_HOST}"
//          print "Docker target repo   : ${env.DOCKER_TARGET_REPO}"
//          print "Docker tag           : ${env.DOCKER_BRANCH_TAG}"
//          print "OpenShift Server     : ${env.OS_SERVER}"
//          print "OpenShift Project    : ${env.OS_PROJECT}"
//          print "Jenkins Deploy Token : ${env.JENKINS_DEPLOY_TOKEN}"
//          print "Sonar Project Key    : ${env.SONAR_PROJECT_KEY}"
//          print "Name of the fulltest POD : ${env.FULL_TEST_POD_NAME}"
//          print "Name of the unittest POD : ${env.UNIT_TEST_POD_NAME}"
//          print '========================================================'
                    helper.printEnvVars()
                }
            }
        }
    }
}