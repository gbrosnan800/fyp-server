//JQuery
$(document).ready(function() {
	 
	getCollectionList();
	
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
            }
        });
	}
	
	function displayCollectionList(collectionList) {
		
		$('#collection_list').html('');
		var htmlString = '';
		for(var item in collectionList) {			
			if(!(collectionList[item] == 'exercises' || collectionList[item] == 'system.indexes')) {
				htmlString += '<div class="csv_area_collection_item">' + collectionList[item]+ '</div>';
			}
		}
		$('#collection_list').html(htmlString);
		//assignClicksToCollectionList();
	}	 
	 
	 
 });