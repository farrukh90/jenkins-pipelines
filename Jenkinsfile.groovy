node {
    properties([pipelineTriggers([cron('*/5 * * * *')])])
	stage("Clone a Repo"){
		timestamps {
            git 'https://github.com/farrukh90/jenkins-class-packer.git'
        }
    }
	stage("Run Script"){
        timestamps {
		sh '''#!/bin/bash
			if [ ! -d /tmp/foo ]; 
			then
				echo "Folder not found!"
				echo "Creating a folder"
				mkdir -p "/tmp/foo"
            fi'''
        }
    }
	stage("Wait"){
        timestamps {
		    sleep 5
        }
    }
	stage("Production Deployment Stage"){
        timestamps {
		// input 'Should I proceed with Producation Deployment?'
        echo "Hello"
        }
    }
    stage("Build ASG"){
        timestamps {
		    build 'ASGBuilder'
        }
    }
	stage("Notify on Slack"){
        timestamps {
		    slackSend channel: 'general', message: 'Job has failed or completed'
        }
	}
    stage("Email"){
        timestamps {
		mail bcc: '', body: '''Hi, 
                The job completed or failed''', cc: '', from: '', replyTo: '', subject: 'Jenkins Job PipelineFromScratch', to: 'farrukhsadykov@gmail.com'
        }
	}
}