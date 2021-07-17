node {
    properties([pipelineTriggers([cron('*/5 * * * *')])])
	stage("Clone a Repo"){
		git 'https://github.com/farrukh90/jenkins-class-packer.git'
}
	stage("Run Script"){
		sh '''#!/bin/bash
			if [ ! -d /tmp/foo ]; 
			then
				echo "Folder not found!"
				echo "Creating a folder"
				mkdir -p "/tmp/foo"
            fi'''
}
	stage("Wait"){
		sleep 5
}
	stage("Production Deployment Stage"){
		// input 'Should I proceed with Producation Deployment?'
        echo "Hello"
}
	stage("Notify on Slack"){
		slackSend channel: 'general', message: 'Job has failed or completed'
	}
    stage("Email"){
		mail bcc: '', body: '''Hi, 
                The job completed or failed''', cc: '', from: '', replyTo: '', subject: 'Jenkins Job PipelineFromScratch', to: 'farrukhsadykov@gmail.com'
	}
}