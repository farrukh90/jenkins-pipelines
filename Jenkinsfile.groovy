node {
    properties([pipelineTriggers([cron('*/5 * * * *')])])
	stage("Stage1"){
		echo "hello"
}
	stage("Stage2"){
		echo "hello"
}
	stage("Stage3"){
		echo "hello"
}
	stage("Production Deployment Stage"){
		input 'Should I proceed with Producation Deployment?'
}
	stage("Notify on Slack"){
		slackSend channel: 'general', message: 'Job has failed or completed'
	}
    stage("Email"){
		mail bcc: '', body: '''Hi, 
                The job completed or failed''', cc: '', from: '', replyTo: '', subject: 'Jenkins Job PipelineFromScratch', to: 'farrukhsadykov@gmail.com'
	}
}