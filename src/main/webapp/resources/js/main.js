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
	}
	
	
	 
	 



  
}); // end ready 


