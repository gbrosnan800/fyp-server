//JQuery
$(document).ready(function() {
	 
	var currentSelectedCollection;
	var exerciseList = [];
	
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
		assignClicksToCollectionList();
	}	 
	 
	function assignClicksToCollectionList() {
		
	    $('.csv_area_collection_item').click(function(e){	
	    	
	    	currentSelectedCollection = $(this).html();
	    	var collection = $(this).html();
	    	var test = 'rest/data/' + collection + '/setsinfo';
	        $.ajax({
	            url: 'rest/data/' + collection + '/setsinfo',
	            type: 'GET',
	            success: function(response) {
	            	displaySetsList(response);
	            },
	            error: function(xhr, status, error) {
	                var err = eval("(" + xhr.responseText + ")");
	                alert(err.Message);
	            }
	        });
	    	
	    });
	}
	
	function displaySetsList(setsList) {
		$('#sets_list').html('');
		var htmlString = '';
		for(var item in setsList) {			
			htmlString += '' +				
						'<div class="csv_area_sets_item">' +    		
							'<div class="csv_area_sets_item_id">' + setsList[item].id + '</div>' +
							'<div class="csv_area_sets_item_name">' + setsList[item].exerciseName + '</div>' +
							'<div class="csv_area_sets_item_reps">' + setsList[item].repCount + '</div>' +  					
						'</div>';
		}
		$('#sets_list').html(htmlString);
		assignClicksToSetList();
	}
 	
	
	function assignClicksToSetList() {
		
	    $('.csv_area_sets_item').click(function(e){	
	    	if(!isSelected($(this))) {	    		
	    		$(this).css('background','#00a651');
	    		var setItemObj = createSetItemObject($(this));
	    		addItemToExerciseList(setItemObj);
	    	}
	    	else {
	    		$(this).css('background','#d3d3d3');
	    		var setItemObj = createSetItemObject($(this));
	    		removeItemToExerciseList(setItemObj);
	    		
	    		//else de-select it and remove from list
	    	}	    	
	    });
	}
	
	function isSelected(setItem) {

		if(setItem.css('background-color') == "rgb(128, 128, 128)") {
			return false;
		}
		else {
			return true;
		}
	}
	
	function createSetItemObject(selectedItem) {
		
		return {
				'collection' : currentSelectedCollection,
				'id' : selectedItem.children('.csv_area_sets_item_id').text(),
				'exerciseName' : selectedItem.children('.csv_area_sets_item_name').text(),
				'repSize' : selectedItem.children('.csv_area_sets_item_reps').text(),	    				
		}
	}

	function addItemToExerciseList(setItemObj) {
		
		exerciseList.push(setItemObj);
		
		console.log(exerciseList);
	}
	
	function removeItemToExerciseList(setItemObj) {
		
		for(item in exerciseList) {
			if(exerciseList[item].collection == setItemObj.collection && exerciseList[item].id == setItemObj.id) {
				exerciseList.splice(item, 1);
			}
		}
		
		console.log(exerciseList);
	}
	
	
 });