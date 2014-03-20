
//gets the list of groups of user and displays it
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

//gets the carts in the selected group and displays
function showGroup(groupId,groupName){
	console.log(groupId+':'+groupName);
	$("#groupdetails [data-role='header'] h1").text(groupName);
	$("#groupdetails [data-role=content]").html('');
	$.getJSON('datamodel/groupcarts.json',{groupId : groupId})
	.done(function(data){
		console.log(data);
		$.each(data,function(key,val){
			var rightDivContent = '';
			
			if(val.cartType=='shared'){
				rightDivContent = '<input type="checkbox" onchange="claimCart('+val.cartId+',\''+val.ownerId.firstname+'\')" data-role="flipswitch" id="buycart" data-on-text="Yes" data-off-text="No" data-mini="true">';
			}
			else{
				rightDivContent = '<a href="#" class="ui-btn-right ui-btn ui-btn-inline ui-btn-icon-notext ui-mini ui-corner-all ui-icon-edit" style="margin:0;">edit</a>';
			}
			
			var groupcart = '<div class="ui-corner-all custom-corners" id="groupcart_'+val.cartId+'">'
				+'<div class="ui-bar ui-bar-a">'
				+'<div style="float:left;padding:5px 0 0 0;">'
				+'<h2>'+val.ownerId.firstname+'</h2>'
				+'</div>'
				+'<div style="float:right;" class="custom-label-flipswitch">'
				+rightDivContent
				+'</div>'
				+'</div>'
				+'<div class="ui-body ui-body-a">'
				+'<ul data-role="listview" data-theme="b" data-count-theme="a">';
			$.each(val.productList,function(key,product){
				groupcart += '<li>'
				+'<p>'+product.Description+'</p>'
				+'<span class="ui-li-count">'+product.quantity+'</span>'
				+'</li>';
			});
			
			groupcart += '</ul>'
			+'</div>'
			+'</div>';
			
			$("#groupdetails [data-role=content]").append(groupcart);
		   
			
		});
		
		 $("#groupdetails [data-role=content]").trigger("create");
		
	});
}

//Claims a cart from group to personal cart
function claimCart(cartId, ownerName){
	
	var userResponse= confirm('Are you willing to shop for '+ownerName+'?<br>Once accepted cannot be undone');
	
	var groupCartId = '#groupcart_'+cartId;
	console.log(groupCartId);

	if(userResponse){
		$.ajax({
			url: 'claimcart',
			data: {cartId: cartId},
			type: 'post'
		});
		$(groupCartId).remove();
	}
	
}



//Creating a new group
function addMember(){
	var name = $('#memberName').val();
	
	var addtoList='<li><a href="#"><h2>'+name+'</h2></a><a href="#" class="deleteitem" onclick="removeMember(\'' +name+ '\')">Delete</a></li>';
	$('#newMemberList').append(addtoList).listview('refresh');
	console.log($('#newMemberList').html());

	/*if ( $('#newMemberList').hasClass('ui-listview')) {
	    console.log('refresh');
	    $('#newMemberList').listview('refresh');
     } 
	else {
		console.log('create');
    	$('#newMemberList').trigger('create');
     }*/

	
	$('#memberName').val('');
	
}

function removeMember(name){
	$('#newMemberList li').filter(function() { return $.text([this]) === name; }).remove();
	$("newMemberList").listview("refresh");	
}


function getSelectGroupList(){
	$.getJSON('datamodel/groups.json', function(grouplist) {
		
		var group = '';
		$.each(grouplist,function(key,item){
			
			var groupId = item.groupId;
			var groupName = item.groupName;
			var lastCartAddedTime = '';
			
			if(item.lastCartAddedTime&&lastCartAddedTime!=null){
				lastCartAddedTime = 'last cart added : ' + item.lastCartAddedTime;
			}			
			
			group += '<li onclick="shareToGroup(' + groupId + ')">' + groupName + '</li>';	
		});
		
		$('#selectgrouplist').html(group);
		$("#selectgrouplist").listview("refresh");		
		
	});
}

function shareToGroup(groupId){
	
}