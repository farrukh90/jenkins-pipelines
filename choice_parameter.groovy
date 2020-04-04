node {
properties(
	[parameters(
		[choice(choices: 
			['v0.1', 'v0.2', 'v0.3', 'v0.4', 'v0.5'], 
		description: 'Which version should we deploy?', 
		name: 'Version')
        ])
    ]) 
	stage("Stage2"){
		git 'https://github.com/farrukh90/packer.git'
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
	stage("Stage5"){
		node {
		// some block
		} 
	}
}
