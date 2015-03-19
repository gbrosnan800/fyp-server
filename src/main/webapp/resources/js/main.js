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

		var htmlString = '';
		for(var item in collectionList) {
			htmlString += '<div class="control_panel_col_r_col_item">' + collectionList[item]+ '</div>';
		}
		$('#collection_list').html(htmlString);
		assignClicksToCollectionList();
	}
	
	function assignClicksToCollectionList() {

	    $('.control_panel_col_r_col_item').click(function(e){	    	
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
	 
	function displaySetList(sets) {
		
		$('.control_panel_col_r_col_item').remove();
		$()
		console.log(sets[0].exerciseName);
		
	}
	 



  
}); // end ready 


