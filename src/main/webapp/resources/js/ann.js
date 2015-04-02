//JQuery
$(document).ready(function() {
	 
	var currentSelectedCollection;
	var exerciseList = [];
	
	
	$('.ann_menu_item').click(function(evt) {
		
		var selected = $(this).text();
		if(selected == "CREATE CSV") {
			$('#ann_area').hide();
			$('#csv_area').fadeIn(1000);
		} 
		else {
			$('#csv_area').hide();
			$('#ann_area').fadeIn(1000);
		}
			
	}); 
	
	
	
	getCollectionList();
	
    $('#clear_list').click(function(e){	
    	exerciseList.length = 0;
    	$('#current_list').html('No Exercise Selected');
    	$('.csv_area_sets_item').css('background', '#d3d3d3');
    	
    });
    
    assignClickToCreateCSVButton();
    function assignClickToCreateCSVButton() { 
	    $('#create_csv_button').click(function(e){	
	    	
	    	$(this).unbind();
	    	$(this).css('background', 'grey');   	
	    	var csvRequest = createRequestList();

	        $.ajax({
	            url: 'rest/csv/create',
	            type: 'POST',
	            data: JSON.stringify(csvRequest),
	            dataType: 'json',
	            contentType : 'application/json; charset=utf-8',
	            success: function(response) {            	
	            	$('#csv_status_msg').html(response.message);
	            },
	            error: function(xhr, status, error) {
	                var err = eval("(" + xhr.responseText + ")");
	                alert(err.Message);
	            }
	        });
	        	        
	        // ASSIGN BACK CLICK TO BUTTON WHEN CSV FILE CREATION IS COMPLETE
		    var ev = $._data(this, 'events');
	        if(!(ev && ev.click)) {
	        	assignClickToCreateCSVButton();
	        	$(this).css('background', '#00a651');
	        } 
	    	
	    });
    }
    
    function createRequestList() {
    	var requestList = [];
    	for(var item in exerciseList) {
    		requestItem = {
    				'collectionName' : exerciseList[item].collection,
    				'id' : exerciseList[item].id 				
    		}
    		requestList.push(requestItem);
    	}
    	
    	var csvRequest = {
    			'csvRequestList' : requestList,
    			'flatPeakHeight' : $('#filter_flat_peaks_height').val(),
    			'flatPeakRange' : $('#filter_flat_peaks_range').val(),
    			'maximaRange' : $('#discover_maximas_range').val(),
    			'maximaStart' : $('#discover_maximas_start').val(),
    			'outerRangeHeight' : $('#filter_outer_range_height').val(),
    			'fileName' : $('#fileName').val()
    	}    	
    	return csvRequest;
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
		setItemsInCurrentListSelected();
		assignClicksToSetList();
	}
	
	function setItemsInCurrentListSelected() {	
		for(var item in exerciseList) {		
			if(exerciseList[item].collection == currentSelectedCollection) {
				$('.csv_area_sets_item').each(function(i) {
					if ($(this).children('.csv_area_sets_item_id').text() == exerciseList[item].id) {
						$(this).css('background', '#00a651');
					}
				});
			}
		}
	}
 		
	function assignClicksToSetList() {
		
	    $('.csv_area_sets_item').click(function(e){	
	    	if(!isSelected($(this))) {	    	
	    		
	    		$(this).css('background','#00a651');
	    		var setItemObj = createSetItemObject($(this));
	    		addItemToExerciseList(setItemObj);
	    	}
	    	else {
	    		
	    		$(this).css('background-color','#d3d3d3');
	    		var setItemObj = createSetItemObject($(this));
	    		removeItemToExerciseList(setItemObj);
	    	}	    	
	    });
	}
	
	function isSelected(setItem) {
	
		if(setItem.css('background-color') == "rgb(211, 211, 211)" || setItem.css('background-color') == "rgb(128, 128, 128)") {
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
		updateCurrentList();
	}
	
	function removeItemToExerciseList(setItemObj) {
		
		for(item in exerciseList) {
			if(exerciseList[item].collection == setItemObj.collection && exerciseList[item].id == setItemObj.id) {
				exerciseList.splice(item, 1);
			}
		}
		updateCurrentList();
	}
	
	function updateCurrentList() {
		
		var summarizedList = summarize(exerciseList);
		$('#current_list').html('');
		var htmlString = '';
		for(var item in summarizedList) {
			htmlString += '' +
				'<div class="csv_exercise_list_item">' +
					'<div class="csv_exercise_list_item_output">' + summarizedList[item].output + '</div>' +
					'<div class="csv_exercise_list_item_exercise">' + summarizedList[item].exercise + '</div>' +
					'<div class="csv_exercise_list_item_reps">' + summarizedList[item].reps + '</div>' +
				'</div>';
		}
		$('#current_list').html(htmlString);
		if(summarizedList.length == 0 ) {
			$('#current_list').html('No Exercise Selected');
		}
	}
	
	
	function summarize(exerciseList) {
		
		var bicep_curl=0, lat_raise=0, shoulder_shrug=0, back_fly=0, one_arm_row=0, kick_back=0, front_raise=0, seated_tricep=0, shoulder_press=0;

		for(item in exerciseList) {
			
			if(exerciseList[item].exerciseName == 'bicep_curl') {
				bicep_curl += parseInt(exerciseList[item].repSize);
			}
			else if(exerciseList[item].exerciseName == 'lat_raise') {
				lat_raise += parseInt(exerciseList[item].repSize);
			}
			else if(exerciseList[item].exerciseName == 'shoulder_shrug') {
				shoulder_shrug += parseInt(exerciseList[item].repSize);
			}
			else if(exerciseList[item].exerciseName == 'back_fly') {
				back_fly += parseInt(exerciseList[item].repSize);
			}
			else if(exerciseList[item].exerciseName == 'one_arm_row') {
				one_arm_row += parseInt(exerciseList[item].repSize);
			}
			else if(exerciseList[item].exerciseName == 'kick_back') {
				kick_back += parseInt(exerciseList[item].repSize);
			}
			else if(exerciseList[item].exerciseName == 'front_raise') {
				front_raise += parseInt(exerciseList[item].repSize);
			}
			else if(exerciseList[item].exerciseName == 'seated_tricep') {
				seated_tricep += parseInt(exerciseList[item].repSize);
			}
			else if(exerciseList[item].exerciseName == 'shoulder_press') {
				shoulder_press += parseInt(exerciseList[item].repSize);
			}			
		}
		var summarizedItem;
		var summarizedList = [];
		if( bicep_curl > 0 ) {
			summarizedItem = {
				'output' : 1,
				'exercise' : 'bicep_curl',
				'reps' : bicep_curl
				}
			summarizedList.push(summarizedItem);
		}
		if( lat_raise > 0 ) {
			summarizedItem = {
				'output' : 10,
				'exercise' : 'lat_raise',
				'reps' : lat_raise
				}
			summarizedList.push(summarizedItem);
		}
		if( shoulder_shrug > 0 ) {
			summarizedItem = {
				'output' : 111,
				'exercise' : 'shoulder_shrug',
				'reps' : shoulder_shrug
				}
			summarizedList.push(summarizedItem);
		}
		if( back_fly > 0 ) {
			summarizedItem = {
				'output' : 1000,
				'exercise' : 'back_fly',
				'reps' : back_fly
				}
			summarizedList.push(summarizedItem);
		}
		if( one_arm_row > 0 ) {
			summarizedItem = {
				'output' : 101,
				'exercise' : 'one_arm_row',
				'reps' : one_arm_row
				}
			summarizedList.push(summarizedItem);
		}
		if( kick_back > 0 ) {
			summarizedItem = {
				'output' : 110,
				'exercise' : 'kick_back',
				'reps' : kick_back
				}
			summarizedList.push(summarizedItem);
		}
		if( front_raise > 0 ) {
			summarizedItem = {
				'output' : 111,
				'exercise' : 'front_raise',
				'reps' : front_raise
				}
			summarizedList.push(summarizedItem);
		}
		if( seated_tricep > 0 ) {
			summarizedItem = {
				'output' : 1000,
				'exercise' : 'seated_tricep',
				'reps' : seated_tricep
				}
			summarizedList.push(summarizedItem);
		}
		if( shoulder_press > 0 ) {
			summarizedItem = {
				'output' : 1001,
				'exercise' : 'shoulder_press',
				'reps' : shoulder_press
				}
			summarizedList.push(summarizedItem);
		}
		
		return summarizedList;		
	}

	
	/* TRAIN NETWORK   */	
	prepareCSVfilesAndNetworkStatus();
	function prepareCSVfilesAndNetworkStatus() {
		
    	$.ajax({
            url: 'rest/csv/filenamelist',
            type: 'GET',
            dataType : 'json',
            success: function(response) {            	
            	populateDropDowns(response);
            	assignClicksToTrainNetworkArea();
            },
            error: function(xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
                alert(err.Message);
            }
        });	
    	
    	$.ajax({
            url: 'rest/ann/status',
            type: 'GET',
            dataType : 'json',
            success: function(response) {            	
            	if(response.status == 'trained') {
        	    	$('#ann_status_message').text('Network is trained');
        	    	$('#ann_status_message').css('color', '#00a651');
        	    	$('#train_button').show();
        	    	$('#results_button').show();
        	    	$('#results_button').show();
        	    	getResults();
            	}
            },
            error: function(xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
                alert(err.Message);
            }
        });	
	}
	
	function getResults() {
		$.ajax({
	        url: 'rest/ann/results',
	        type: 'GET',
	        dataType : 'json',
	        success: function(response) {            	
	        	renderResults(response);
	        },
	        error: function(xhr, status, error) {
	            var err = eval("(" + xhr.responseText + ")");
	            alert(err.Message);
	        }
	    });	
	}
	
	function populateDropDowns(fileList) {
		
		var htmlString = '';
		
		for(var file in fileList) {			
			htmlString += '<div class="ann_file_menu_item">' + fileList[file] + '</div>';					
		}
		$('#dropdown_trainfile_selected').html(fileList[0]);
		$('#dropdown_testfile_selected').html(fileList[0]);
		$('#dropdown_menu_trainfile').html(htmlString);
		$('#dropdown_menu_testfile').html(htmlString);
	}

	function assignClicksToTrainNetworkArea() {
		
	    $('#dropdown_arrow_trainfile').click(function(e){	
	    	$('#dropdown_menu_testfile').hide();
	    	$('#dropdown_menu_trainfile').slideToggle(250);
	    	
	    });
	    
	    $('#dropdown_arrow_testfile').click(function(e){
	    	$('#dropdown_menu_trainfile').hide();
	    	$('#dropdown_menu_testfile').slideToggle(250);
	    });	   
	    
	    $('.ann_file_menu_item').click(function(e){
	    	var dropdown = $(this).parent().parent();
	    	
	    	if(dropdown.attr('id') == 'dropdown_trainfile') {
	    		$('#dropdown_menu_trainfile').hide();
	    		$('#dropdown_trainfile_selected').text($(this).text());
	    	}
	    	else {
	    		$('#dropdown_menu_testfile').hide();
	    		$('#dropdown_testfile_selected').text($(this).text());
	    	}    		    	
	    });
	    
	    $('#train_button').click(function(e){	
	    	trainNetwork();
	    	$(this).hide();
	    	$('#results_button').hide();
	    	$('#ann_status_message').show();
	    	$('#ann_results').hide(); 
	    	
	    	$('#ann_status_message').text('Training network...');
	    	$('#ann_status_message').css('color', 'orange');
	    });
	}
	
    function trainNetwork() {
        
    	var annConfigData = {
    			
    			'trainingFileName' : $('#dropdown_trainfile_selected').text(),
    			'testingFileName' : $('#dropdown_testfile_selected').text(),
    			'inputPerceptrons' : $('#input_perceptrons').val(),
    			'hiddenPerceptrons' : $('#hidden_perceptrons').val(), 
    			'outputPerceptrons' : $('#output_perceptrons').val(),
    			'learningRate' : $('#learning_rate').val(),
    			'momentum' : $('#momentum').val(),
    			'maxIterations' : $('#max_iterations').val()	    			
    	}
    	
    	$.ajax({
            url: 'rest/ann/train',
            type: 'POST',
            data: JSON.stringify(annConfigData),          
            contentType : 'application/json; charset=utf-8',
            dataType : 'json',
            success: function(response) {
    	    	$('#ann_status_message').text('Network is trained');
    	    	$('#ann_status_message').css('color', '#00a651');
    	    	$('#train_button').show();
    	    	$('#results_button').show();
    	    	$('#results_button').show();
    	    	renderResults(response);
            },
            error: function(xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
                alert(err.Message);
            }
        });
    }
    
    $('#results_button').click(function(evt) {
    	
    	$(this).hide();
    	$('#ann_status_message').hide();
    	$('#ann_results').show();    		
    });
    
    $('#ann_results_close').click(function(evt) {
    	
    	$('#ann_status_message').show();
    	$('#results_button').show();
    	$('#ann_results').hide();
    	
    });
	
    function renderResults(results) {
    	
    	var htmlString = '';
    	var color = 'red';
    	var matches = 0;
    	for(var result in results) {
    		
    		if(results[result][0] == results[result][1]) {
    			color = '#00a651';
    			matches ++;
    		}
    		else {
    			color = "red";
    		}
    		
    		htmlString += '' +  
    				'<div class="ann_results_items_row" style="color:' + color + '">' +
						'<div class="ann_results_num">' + (parseInt(result) + 1) + '</div>' +
						'<div class="ann_results_expected">' + results[result][0] + '</div>' +
						'<div class="ann_results_actual">' + results[result][1]  + '</div>' + 
					'<div>';
    	}   	
    	var precentage = 100 / results.length * matches;
    	
    	
    	$('#ann_results_precent').text(Math.round(precentage) + '%');
    	$('#ann_results_render').html(htmlString);
    }
	
	
	
 });