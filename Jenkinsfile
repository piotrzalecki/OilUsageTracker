pipeline {
	agent{
	  docker{
		image 'openjdk:latest'
	  }	
	}
	
	stages {
	  stage('Build') {
		steps{
			
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


