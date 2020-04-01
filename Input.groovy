node {
	stage("Stage1"){
		echo "hello"
	}
	stage("Stage2"){
		echo "hello"
	}
	stage("Stage3"){
		echo "hello"
	}
	stage("Ask for Input"){
		input 'Should I proceed?'
	}
		stage("Stage5"){
	}
}
