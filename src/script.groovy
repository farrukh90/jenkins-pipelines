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
	stage("Stage4"){
		echo "hello"
}
	stage("Script"){
		sh label: '', script: 
		'''#!/bin/bash
			if [ ! -d /tmp/foo.txt ]; 
			then
				echo "Folder not found!"
				echo "Creating a folder"
				mkdir "/tmp/foo.txt"
				ls -l /tmp
			fi
		'''
	}
}
