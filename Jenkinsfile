pipeline {
	agent any
	
	stages {
	  stage('Build') {
		steps{
			echo "Hello Building",
			sh 'javac OilUsageTracker.java'
			sh 'jar -cvf OilTracker.jar OilUsageTracker.class'	
		}
	  }
	  
	  stage('Test') {
		steps{
			echo "Hello Testing"
		}
	  }
	  stage('Deploy'){
		steps{
			echo "Hello Deploying"
		}
	  }
	}
}


