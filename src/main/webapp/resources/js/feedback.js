// JavaScript Document
    
 $(document).ready(function() {
	 
	 var setsList, maximasList;
	 
	 assignInitialClicks();
	
	 function assignInitialClicks() {		 
		 $('#control_panel_button').click(function(e){	
	    	e.stopPropagation();
	    	$('#peak_detection_menu').hide();
	    	$('#control_panel').animate({
	    		right:'0'
	    	});
	    	
	    });
	    $('#control_panel_header img').click(function(e){	 
	    	e.stopPropagation();
	    	$('#control_panel').animate({
	    		right:'-500'
	    	});	    	
	    });	    
	    $('#choose_collection').click(function(e){	
	    	e.stopPropagation();
	    	$('#control_panel_col_r_header').text('Choose Collection');
	    	getCollectionList();
	    });
	
	    $(document).click(function(e){	
	    	$('#peak_detection_menu').hide();
	    	$('#control_panel').animate({
	    		right:'-500'
	    	});	
	    });		    	   
	    /*
	    $('#average_data_menu').click(function(e){	
	    	e.stopPropagation();
	    	$('#peak_detection_menu').toggle();
	    });	 
	    
	    $('#peak_detection_menu div').click(function(e){	
	    	e.stopPropagation();
	    });	
	    */
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
		
		$('#collect_set_list div').remove();
		var htmlString = '';
		for(var item in collectionList) {			
			if(isRoutine(collectionList[item])) {
				htmlString += '<div class="control_panel_col_r_col_item">' + collectionList[item]+ '</div>';
			}
		}
		$('#collection_list').html(htmlString);
		assignClicksToCollectionList();
	}
	
	function isRoutine(collectionName) {
		
		if(collectionName.length >= 7) {
			if(collectionName.substring(0,7) == 'routine') {
				return true;
			}
		}
		return false;		
	}
	
	function assignClicksToCollectionList() {
	
	    $('.control_panel_col_r_col_item').click(function(e){	
	    	e.stopPropagation();
	    	var collectionname = $(this).text();
	    	$('#control_panel').animate({
	    		right:'-500'
	    	});	 
	    	
	    	$.ajax({
	            url: 'rest/data/routine/' + collectionname,
	            type: 'GET',
	            success: function(response) {
	            	console.log(response);
	            	renderCollection(response, collectionname);
	            	assignClicksToCollection(response);
	            },
	            error: function(xhr, status, error) {
	                var err = eval("(" + xhr.responseText + ")");
	                alert(err.Message);
	                return null;
	            }
	        });	         
	    });		
	}
	 
	function renderCollection(routineList, collectionname) {
		
		var htmlString = '';
		$('#routine_render').html('');
		$('#current_collection_name').text(collectionname);
		for(var routine in routineList ) {
			
			htmlString += '' +
				'<div class="routine" id="routine_' + (parseInt(routine) + 1) + '">' +
					'<div class="routine_header" id="routine_num_' + (parseInt(routine) + 1) + '">' +
						'<div class="routine_info_header_item">' +
							'<div class="routine_info_header_label">No:</div>' +
							'<div class="routine_info_leader_info">' + routineList[routine].id + '</div>'	+				
						'</div>' +		
						'<div class="routine_info_header_item">' +
							'<div class="routine_info_header_label">Username:</div>' +
							'<div class="routine_info_leader_info">' + routineList[routine].username + '</div>'	+				
						'</div>' +	
						'<div class="routine_info_header_item">' +
							'<div class="routine_info_header_label">Size:</div>' +
							'<div class="routine_info_leader_info">' + routineList.length + '</div>'	+				
						'</div>' +	
						'<div class="routine_info_header_item">' +
							'<div class="routine_info_header_label">Fail Rep Time:</div>' +
							'<div class="routine_info_leader_info">' + routineList[routine].timeOfLastRep + '</div>'	+				
						'</div>' +				
					'</div>' +
					'<div class="routine_info">' +
						'<div class="routine_info_line">' +
							'<div class="routine_info_label">Exercise Dectected:</div>' +
							'<div class="routine_info_info">' + routineList[routine].processedExercise.exerciseDetected + '</div>' +					
						'</div><br>' +
						'<div class="routine_info_line">' +
							'<div class="routine_info_label">Reps Dectected:</div>' +
							'<div class="routine_info_info">' + routineList[routine].processedExercise.extractedReps.length + '</div>' +					
						'</div><br>' +
						'<div class="routine_info_line">' +
							'<div class="routine_info_label">Weight lifted:</div>' +
							'<div class="routine_info_info">' + routineList[routine].processedExercise.weight + 'kg</div>' +					
						'</div><br>' +
						'<div class="routine_info_line">' +
							'<div class="routine_info_label">Date:</div>' +
							'<div class="routine_info_info">' + routineList[routine].processedExercise.date + '</div>' +					
						'</div><br>' +
						'<div class="routine_info_line">' +
							'<div class="routine_info_label">RM:</div>' +
							'<div class="routine_info_info">' + routineList[routine].current1RM + 'kg</div>' +					
						'</div><br>' +
						'<div class="routine_info_line">' +
							'<div class="routine_info_label">Next Weight:</div>' +
							'<div class="routine_info_info">' + routineList[routine].nextWeight + 'kg</div>' +					
						'</div><br>' +	
						'<div class="routine_info_chart" id="routine_info_' + (parseInt(routine) + 1) + '_chart"></div>' +
					'</div>' +
				'</div>';								
		}
		$('#routine_render').html(htmlString);
		/*
		for(var routine in routineList ) {
			var element = 'routine_info_' + (parseInt(routine) + 1) + '_chart';
			plotGraph_RawData(routineList[routine].processedExercise, element );
		}
		*/
	}
	
	function assignClicksToCollection(routineList) {
	
		$('.routine_header').unbind();
		$('.routine_header').click(function(e) {
			var id = $(this).attr('id');
			var idSplit = id.split('_');
			var idNum = idSplit[2];

			
			$(this).parent().children('.routine_info').slideToggle('slow', function() {
				var element = 'routine_info_' + idNum + '_chart';
				plotGraph_RawData(routineList[idNum-1].processedExercise, element );
			});
			
			
			
		});
		
	}
	
	function plotGraph_RawData(exericse, element) {
		
		var sampleList = exericse.rawSensorSamples;
		var x = [], y = [], z = [];
			
		for(var sample in sampleList) {			
			x[sample] = sampleList[sample].x;
			y[sample] = sampleList[sample].y;
			z[sample] = sampleList[sample].z;
		}
		console.log(element);
		$('#' + element).html('');
		$('#' + element).width('auto');

		$.jqplot(element,  [x,y,z], { 	
			
			title: false,
			animate: true,
			series: [{color:'#00a651'}]	,
			seriesDefaults: {
				smooth:true,
				lineWidth: 4,
				showMarker:false,
			},
			axes: {
				xaxis: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					tickOptions: {
						angle:-45,
						fontSize:'10pt',
						textColor: '#000'
					},
					pad:0
				},
				yaxis: {
					pad:0,
					showTicks:false
				}
			},
			grid: {
				gridLineColor:'#333333',
				background:'#fff'
			}
		});
		//$('#' + element).parent().hide();
	}
	 
	 
	 
	 
	 
 });