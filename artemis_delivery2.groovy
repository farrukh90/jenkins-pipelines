node {
	properties(
		[parameters(
			[choice(choices: 
				[
				'0.1', 
				'0.2', 
				'0.3', 
				'0.4', 
				'0.5',
				'0.6',
				'0.7',
				'0.8',
				'0.9',
				'10',
			], 
		description: 'Which version of the app should I deploy? ', 
		name: 'Version')])])
		stage("Stage1"){
			timestamps {
				ws {
					checkout([$class: 'GitSCM', branches: [[name: '${Version}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/farrukh90/artemis.git']]]) }
			}
		}
		stage("Get Credentials"){
		timestamps {
			ws{
				sh '''
					aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 713287746880.dkr.ecr.us-east-1.amazonaws.com/artemis
					'''
				}
			}
		}
		stage("Build Docker Image"){
		timestamps {
			ws {
				sh '''
					docker build -t artemis:${Version} .
					'''
				}
			}
		}
		stage("Tag Image"){
			timestamps {
				ws {
					sh '''
						docker tag artemis:${Version} 713287746880.dkr.ecr.us-east-1.amazonaws.com/artemis:${Version}
					'''
					}
				}
			}
		stage("Push Image"){
			timestamps {
				ws {
					sh '''
						docker push 713287746880.dkr.ecr.us-east-1.amazonaws.com/artemis:${Version}
						'''
				}
			}
		}
		stage("Send slack notifications"){
			timestamps {
				ws {
					echo "Slack"
					//slackSend color: '#BADA55', message: 'Hello, World!'
				}
			}
		}
		stage("Authenticate"){
			timestamps {
				ws {
					sh '''
						ssh centos@dev1.acirrustech.com $(aws ecr get-login --no-include-email --region us-east-1)
						'''
				}
			}
		}
		stage("Clean Up"){
			timestamps {
				ws {
					try {
						sh '''
							#!/bin/bash
							IMAGES=$(ssh centos@dev1.acirrustech.com docker ps -aq) 
							for i in \$IMAGES; do
								ssh centos@dev1.acirrustech.com docker stop \$i
								ssh centos@dev1.acirrustech.com docker rm \$i
							done 
							'''
					} catch(e) {
						println("Script failed with error: ${e}")
						}
					}
				}
			}
		
	stage("Run Container"){
		timestamps {
			ws {
				sh '''
					ssh centos@dev1.acirrustech.com docker run -dti -p 5001:5000 713287746880.dkr.ecr.us-east-1.amazonaws.com/artemis:${Version}
					'''
            }
        }
    }
}