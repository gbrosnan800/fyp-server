// JavaScript Document

    
 $(document).ready(function() {

   //  function getTestData2(player_data) {
/*
         $.ajax({
             url: 'rest/data/collectionnames',
             type: 'GET',
             success: function(data) {
                 alert('data is ' + data);
             },
             error: function(xhr, status, error) {
                 var err = eval("(" + xhr.responseText + ")");
                 alert(err.Message);
             }
         });
  */
  //   }
	 //test change
	 
	
	assignInitialClicks();
	
	function assignInitialClicks() {
	    $('#choose_collection').click(function(e){
	    	
	    	var collectionList = getCollectionList();

	    	
	
	    });
	
	}
	
	function getCollectionList() {
        $.ajax({
            url: 'rest/data/collectionnames',
            type: 'GET',
            success: function(collectionList) {
            	displayCollectionList(collectionList);
            },
            error: function(xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
                alert(err.Message);
                return null;
            }
        });
	}
	
	function displayCollectionList(collectionList) {
		
		$('#collect_set_list div').remove();
		var htmlString = '';
		for(var item in collectionList) {
			htmlString += '<div class="control_panel_col_r_col_item">' + collectionList[item]+ '</div>';
		}
		$('#collection_list').html(htmlString);
		assignClicksToCollectionList();
	}
	
	function assignClicksToCollectionList() {

	    $('.control_panel_col_r_col_item').click(function(e){	
	    	$('.control_panel_col_r_col_item').remove();
	    	var collectionname = $(this).text();
	        
	    	$.ajax({
	            url: 'rest/data/' + collectionname + '/sets',
	            type: 'GET',
	            success: function(sets) {
	            	displaySetList(sets);
	            },
	            error: function(xhr, status, error) {
	                var err = eval("(" + xhr.responseText + ")");
	                alert(err.Message);
	                return null;
	            }
	        });	    	
	    });		
	}
	 
	function displaySetList(setsList) {
		
		var htmlString = '';
		htmlString += '' +				
		'<div id="collect_set_list_header">' +
			'<div id="collect_set_list_header_id">ID</div>' +
			'<div id="collect_set_list_header_name">NAME</div>' +
			'<div id="collect_set_list_header_reps">REPS</div>' +
		'</div>';
		
		for(var item in setsList) {
			htmlString += 
				'<div class="collect_set_list_item">' +
					'<div class="collect_set_list_item_id">' + setsList[item].id + '</div>' +
					'<div class="collect_set_list_item_name">' + setsList[item].exerciseName + '</div>' +
					'<div class="collect_set_list_item_reps">' + setsList[item].repCount + '</div>' +
				'</div>';
		}
		$('#collect_set_list').html(htmlString);	
	}
	 



  
}); // end ready 


