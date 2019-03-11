pipeline {
	agent any
	
	stages {
	  stage('Build') {
		steps{
			echo "Hello Building",
			javac OilUsageTracker.java,
			jar -cvf OilTracker.jar OilUsageTracker.class	
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


