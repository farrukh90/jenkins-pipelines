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
	stage("Stage4"){
		echo "hello"
}
	stage("Notify"){
		slackSend channel: 'general', message: 'Job has failed or completed'
	}
}