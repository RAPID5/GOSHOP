function getGroupList(){
	$.getJSON('datamodel/groups.json', function(grouplist) {
		
		var group = '';
		$.each(grouplist,function(key,item){
			
			var groupId = item.groupId;
			var groupName = item.groupName;
			var lastCartAddedTime = '';
			
			if(item.lastCartAddedTime&&lastCartAddedTime!=null){
				lastCartAddedTime = 'last cart added : ' + item.lastCartAddedTime;
			}			
			
			group += '<li><a href="#groupdetails" onclick="showGroup(' + groupId + ',\'' + groupName + '\')" data-transition="slide">'				        
		    +'<h2>' + groupName + '</h2>'
		    +'<p>' + lastCartAddedTime + '</p>'
		    +'</a>'
		    +'</li>';	
		});
		
		console.log(group);
		$('#grouplist').html(group);
		$("#grouplist").listview("refresh");		
		
	});
}

function showGroup(groupId,groupName){
	console.log(groupId+':'+groupName);
	$("#groupdetails [data-role='header'] h1").text(groupName);
	
}