$(document).ready(function() {


	/*creating dynamic list according to typed entry*/
	$( "#mylist" ).on( "filterablebeforefilter", function ( e, data ) {
		var $ul = $( this ),
		$input = $( data.input ),
		value = $input.val(),
		html = "";
		$ul.html( "" );
		if ( value && value.length > 2 ) {
			$ul.html( "<li><div class='ui-loader'><span class='ui-icon ui-icon-loading'></span></div></li>" );
			$ul.listview( "refresh" );
			$.ajax({
				url: "datamodel/fetchproducts.json",
				dataType: "json",
				//crossDomain: true,
				data: {
					name: $input.val()
				}
			})
			.then( function ( response ) {
				$.each( response.ProductDetails, function ( i, val ) {
					//html += "<li>" + val.Description + "</li>";
					$ul.append("<li>" + val.Description + "</li>");
				});
				//$ul.html( html );
				$ul.listview( "refresh" );
				$ul.trigger( "updatelayout");

				//binding click action over li 
				$( "#mylist li" ).bind( "click", function() {
					$( "#item" ).val( $( this ).text() );  
					$("#mylist li" ).addClass('ui-screen-hidden');
				});
			});
		}
	});

	//Adding headers to different page and sidepanel dynamically
	$(document).on("pageshow", "[data-role='page']", function() {		
		if ($($(this)).hasClass("panel_default")) {
			var sidepanel='<div data-role="panel" id="mypanel" data-theme="b">'
				+'<header data-role="header">'
				+'<h2>Menu</h2>'
				+'</header>'
				+'<div data-role="controlgroup" data-mini="true">'
				+'<a href="#home"	class="ui-btn ui-icon-edit ui-btn-icon-left ui-corner-all ui-btn-b">Home</a>'
				+'<a href="#shopping"	class="ui-btn ui-icon-edit ui-btn-icon-left ui-corner-all ui-btn-b ui-btn-active">Go Shopping</a>'
				+'<a href="#"	class="ui-btn ui-icon-video ui-btn-icon-left ui-corner-all ui-btn-b">Deals</a>'
				+'<a href="#mycart" class="ui-btn ui-icon-camera ui-btn-icon-left ui-corner-all ui-btn-b">My Cart</a>'
				+'<a href="#" class="ui-btn ui-icon-comment ui-btn-icon-left ui-corner-all ui-btn-b">Cart Pool</a>'
				+'<a href="#" class="ui-btn ui-icon-comment ui-btn-icon-left ui-corner-all ui-btn-b">Logout</a>'
				+'</div>'	    	
				+'</div>';			
			//$(sidepanel).prependTo( $(this) );
			
		}
		if ($($(this)).hasClass("header_default")) {
			console.log($(this).jqmData("sec"));
			var secondaryButton = '';

			if($(this).jqmData("sec")!=undefined){
				var button = $(this).jqmData("sec");
				console.log('button : '+ button);
				secondaryButton += '<a href="#' + button + '" class="ui-btn-right ui-btn ui-btn-inline ui-btn-icon-notext ui-mini ui-corner-all ui-icon-plus">Add'
			+'</a>';
			}

			

			var headerHTML = '<header data-theme="b" data-role="header">'
			+'<h1></h1>'
			+'<a href="#mypanel" class="ui-btn-left ui-btn ui-btn-inline ui-btn-icon-notext ui-mini ui-corner-all ui-icon-bars">Menu'
			+'</a>'
			+secondaryButton
			+'</header>';

			$(headerHTML)
			.prependTo( $(this) )
			.toolbar({ position: "fixed" });
			$("[data-role='header'] h1").text($(this).jqmData("title"));
		}		
	});
	
	

	/*add item*/
	/*<li><a href="#">Item 1</a><a href="#" class="deleteitem">Delete</a></li>*/
	
	//adding item to the list
	$('#additem').click(function(){
		var item=$('#item').val();
		var quantity=$('#quantity').val();
		if(!item == ''){
			var addtoList='<li><a href="#" class="itemname"><h2>'+item+'</h2><p>'+quantity+'</p></a><a href="#" class="deleteitem" onclick="javascript:deleteItem(\'' + item + quantity + '\')">Delete</a></li>';
			$('#itemlist').append(addtoList).listview('refresh');
			$('#item').val('');
			$('#quantity').val('');
		}

	});


	//submitting the list
	$('#addtocart').click(function(){
		var text='';
		$('#itemlist li > .itemname').each(function(){
			var item=$(this).children('h2').text();
			var quantity = $(this).children('p').text();
			text += item +';'+quantity+'||';
		});

		$('#items').val(text);
		console.log($('#items').val());

		if(!text=='')
			$('#finallist').submit();
	});
});

$( document ).on( "pagecreate", function() {

});
/*
//to populate mycart section
function getCartList(){

	$.getJSON('datamodel/cartlist.json', function(data) {
	    console.log(data);
	    var list='';
	    $.each(data,function(key,val){
	    	list += '<div class="ui-corner-all custom-corners">';	
	    	var sharebutton ='';
	    	if(val.shareflag==1){//user's own card which can be shared
	    		sharebutton = '<div style="float:right;">'
		    		+'<a href="#sharetogroup" class="ui-btn ui-btn-mini ui-icon-share ui-corner-all ui-btn-icon-notext" style="margin:0"></a>'
		    		+'</div>';
	    	}
	    	list += '<div class="ui-bar ui-bar-a">'
	    		+'<div style="float:left;padding:5px 0 0 0;">'
	    		+'<h2>' +val.date+ '</h2>'
	    		+'</div>'
	    		+ sharebutton
	    		+'</div>'
	    		+'<div class="ui-body ui-body-a">'
	    		+'<ul data-role="listview" data-theme="b" data-count-theme="a">';
	    		$.each(val.items,function(key,items){
	    			list += '<li>'
	    				+'<h2>'+items.name+'</h2>'
	    				+'<span class="ui-li-count">'+items.quantity+'</span>'
	    				+'</li>';
	    		});
	    		list +='</ul>'
	    		+'</div>'
	    		+'</div>'
	    		+'<a href="#storelist" class="ui-btn ui-icon-edit ui-btn-icon-left ui-corner-all ui-btn-b">Lets Go !</a>';
	    });
	    
	    $("#mycart [data-role=content]").html(list);
	    $("#mycart [data-role=content]").trigger("create");
	    
	    
	  });
}
*/
function showGroup(groupId,groupName){
	console.log(groupId+':'+groupName);
	$("#groupdetails [data-role='header'] h1").text(groupName);
}

function deleteItem(item){
	$('#itemlist li').filter(function() { return $.text([this]) === item; }).remove();
	$("itemlist").listview("refresh");	  
}




