#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET=$(cat bucket-name.txt)
TEMPLATE=template-mvn.yml

ODM_INSTALL_2=/Users/pierrefeillet/IBM/ODM8103
basedir=.

mvn install:install-file -Dfile=${ODM_INSTALL_2}/executionserver/lib/jrules-res-8.10.3.0-execution.jar -DgroupId=com.ilog.rules.executionserver -DartifactId=jrules-res-execution -Dversion=8.10.3 -Dpackaging=jar
mvn install:install-file -Dfile=${ODM_INSTALL_2}/executionserver/lib/jrules-engine-8.10.3.0.jar -DgroupId=com.ilog.rules.executionserver -DartifactId=jrules-engine -Dversion=8.10.3 -Dpackaging=jar
mvn install:install-file -Dfile=${ODM_INSTALL_2}/executionserver/lib/j2ee_connector-1_5-fr.jar -DgroupId=com.ilog.rules.executionserver -DartifactId=jconnector-api -Dversion=1.5 -Dpackaging=jar
		
mvn install:install-file -Dfile=./ruleapps/miniloan_ruleapp.jar -DgroupId=com.ibm.decisions.faas.examples.miniloan -DartifactId=miniloan-ruleapp -Dversion=1.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=./libs/miniloan-xom.jar -DgroupId=com.ibm.decisions.faas.examples.miniloan -DartifactId=miniloan-xom -Dversion=1.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=./libs/miniloan-runner.jar -DgroupId=com.ibm.decisions.faas.examples.miniloan -DartifactId=miniloan-runner -Dversion=1.0.0 -Dpackaging=jar

# ToDo remove hardcoded path WARNING 
mvn package -Dodm.install=/Users/pierrefeillet/IBM/ODM8103

aws cloudformation package --template-file $TEMPLATE --s3-bucket $ARTIFACT_BUCKET --output-template-file out.yml
aws cloudformation deploy --template-file out.yml --stack-name java-basic --capabilities CAPABILITY_NAMED_IAM
