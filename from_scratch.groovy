node {
	properties([
		// Below line sets "Discard Builds more than 5"
		buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), 
		
		// Below line triggers this job every minute
		pipelineTriggers([pollSCM('* * * * *')])
		])
	stage("Pull Repo"){
		git   'https://github.com/farrukh90/cool_website.git'
}
	stage("Install Prerequisites"){
		sh """
		ssh centos@jenkins_worker1.acirrustech.com         sudo yum install httpd -y
		scp -r *  centos@jenkins_worker1.acirrustech.com:/tmp
		ssh centos@jenkins_worker1.acirrustech.com         sudo cp -r /tmp/index.html /var/www/html/
		ssh centos@jenkins_worker1.acirrustech.com         sudo cp -r /tmp/style.css /var/www/html/
		ssh centos@jenkins_worker1.acirrustech.com         sudo systemctl start httpd 
		"""
}
	stage("Stage3"){
		echo "hello"
}
	stage("Stage4"){
		echo "hello"
}
	stage("Stage5"){
		echo "hello"
	}
}
