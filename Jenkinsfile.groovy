properties([
    buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), 
    parameters([choice(choices: ['1.0.0', '2.0.0', '3.0.0'], description: 'Please choose version to deploy', name: 'APP_VERSION'), 
    string(defaultValue: 'PLEASE_ENTER_IP', description: 'Please provide IP to deploy', name: 'ENVIR', trim: false)]), 
    pipelineTriggers([cron('*/5 * * * *')])])
      
      
      
node {
	stage("Clone a Repo"){
		timestamps {
            checkout([$class: 'GitSCM', branches: [[name: '${APP_VERSION}']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/farrukh90/artemis.git']]])
        }
    }
	stage("Install Prerequisites of Artemis"){
        timestamps {
            sshagent(['ec2-user']) {
                sh '''
                    ssh -o StrictHostKeyChecking=no ec2-user@${ENVIR} sudo amazon-linux-extras install epel -y
                    ssh -o StrictHostKeyChecking=no ec2-user@${ENVIR} sudo yum install python-pip -y
                    ssh -o StrictHostKeyChecking=no ec2-user@${ENVIR} sudo pip install Flask
                '''
            }
        }
    }
    stage("Copy Artemis"){
        timestamps {
		   sshagent(['ec2-user']) {
                sh '''
                    scp -r * ec2-user@${ENVIR}:/tmp
                '''
            }
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