// JavaScript Document

    
 $(document).ready(function() {

	var setsList;
	 
	assignInitialClicks();
	
	function assignInitialClicks() {
	    $('#control_panel_button').click(function(e){	    	
	    	$('#control_panel').animate({
	    		right:'0'
	    	});
	    	
	    });
	    $('#control_panel_header img').click(function(e){	    		
	    	$('#control_panel').animate({
	    		right:'-500'
	    	});	    	
	    });	    
	    $('#choose_collection').click(function(e){	
	    	$('#control_panel_col_r_header').text('Choose Collection');
	    	getCollectionList();
	    });
/*
	    $(document).click(function(e){	
	    	$('#peak_detection_menu').hide();
	    });	*/

	    $('#average_data_menu').click(function(e){	
	//    	e.stopPropagation();
	    	$('#peak_detection_menu').toggle();
	    });	 
	    
	    $('#peak_detection_menu div').click(function(e){	
	    //	e.stopPropagation();
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
            }
        });
	}
	
	function displayCollectionList(collectionList) {
		
		$('#collect_set_list div').remove();
		var htmlString = '';
		for(var item in collectionList) {			
			if(!(collectionList[item] == 'exercises' || collectionList[item] == 'system.indexes')) {
				htmlString += '<div class="control_panel_col_r_col_item">' + collectionList[item]+ '</div>';
			}
		}
		$('#collection_list').html(htmlString);
		assignClicksToCollectionList();
	}
	
	function assignClicksToCollectionList() {

	    $('.control_panel_col_r_col_item').click(function(e){	
	    	$('.control_panel_col_r_col_item').remove();
	    	var collectionname = $(this).text();
	    	$('#control_panel_col_r_header').text('Collection: ' + collectionname);
	        
	    	$.ajax({
	            url: 'rest/data/' + collectionname + '/sets',
	            type: 'GET',
	            success: function(sets) {
	            	setsList = sets;
	            	displaySetList();
	            },
	            error: function(xhr, status, error) {
	                var err = eval("(" + xhr.responseText + ")");
	                alert(err.Message);
	                return null;
	            }
	        });	    	
	    });		
	}
	 
	function displaySetList() {
		
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
		
		assignClicksToSets();
	}
	
	function assignClicksToSets() {
		$('.collect_set_list_item').click(function(e){	
			
			$('.graph_area_chart').html('');
			$('.graphs_area_section_menu').fadeOut();

	    	var arrayNum = $(this).find('.collect_set_list_item_id').html();
	    	arrayNum--;
			$('#control_panel').animate({
	    		right:'-500',
	    	});	
			$('#graphs_area_section_menu_raw').fadeIn();
	    	plotGraph_RawData(setsList[arrayNum]);
	    	assignClickToRawGraphMenu(setsList[arrayNum]);
		});
	}
	
	function assignClickToRawGraphMenu(exericse) {
		$('#view_averages').click(function(e){			
			$('#graphs_area_section_average').show();
			var maximas = [];
			plotGraph_AverageData(exericse, maximas);
		});
	}
	
	function plotGraph_RawData(exericse) {
		
		var sampleList = exericse.sensorSampleList;
		var x = [], y = [], z = [];
			
		for(var sample in sampleList) {			
			x[sample] = sampleList[sample].x;
			y[sample] = sampleList[sample].y;
			z[sample] = sampleList[sample].z;
		}
		
		$('#graph_area_raw_chart').html('');
	
		$.jqplot('graph_area_raw_chart',  [x,y,z], { 	
			
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
						textColor: '#ffffff'
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
				background:'#000'
			}
		});
	}
	
	function plotGraph_AverageData(exericse, maximas) {
		var sampleList = exericse.sensorSampleList;
		var averages = [];
		var average = 0;
		var labelPoints = [];

		for(var point = 0 ; point < sampleList.length ; point++ ) {
			average = (sampleList[point].x + sampleList[point].y + sampleList[point].z) / 3;					
			averages[point] = average;
			if(maximas.indexOf(point) > -1) {
				labelPoints[point] = 'MAX';
			}
			else {
				labelPoints[point] = null;
			}
		}
		plotGraphWithLabelPoints(averages, labelPoints);
		assignClickToDiscoverMaxiams(averages);
	}

	function plotGraphWithLabelPoints(averages, labelPoints) {
				
		$('#graph_area_average_chart').html('');
	
		$.jqplot('graph_area_average_chart',  [averages], { 	
			
			title: false,
			animate: true,
			/*series: [{color:'#00a651'}]	,*/
			series: [{
				smooth:true,
				lineWidth: 4,
				color: '#00a651',
				showMarker:false,
				pointLabels: {
					show: true,		
					labels: labelPoints
				},
			}],
			axes: {
				xaxis: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					tickOptions: {
						angle:-45,
						fontSize:'10pt',
						textColor: '#ffffff'
					},
					pad:0,
				},
				yaxis: {
					pad:1.5,
					showTicks:false
				}
			},
			grid: {
				gridLineColor:'#333333',
				background:'#000'
			}
		});
				  
	}	
	/*
	
	-Three phases in Peak Detection Menu
		-Original = only Discover Maximas highlighted and clickable
		-Maximas Discovered = any time maximas are discovered - all other options highlighted and clickable except Rep Extraction
		-ALL - when user decides to click Discover Minimas, Rep extraction is now h + c
		
		-if filter options or Discover Maximas are re-clicked, Rep Extraction is hidden again until Minimas are re-discovered
	
		Discover Maxmis and Filter options can all use the same Ajax call and Object, just change variables and use URL variable
		It would be faster to use front-end in certain situations but its better to let back-end to do all to demonstrate it works
	 */
	function assignClickToDiscoverMaxiams(averages) {
			
		$('#discover_maximas_btn').click(function(e){				
			
			assignClicksToFiltersAndMinimas()
			
			var start = $('#discover_maximas_start').val();
			var range = $('#discover_maximas_range').val();			
			var averageList = {
					"averages" : averages,
					"start" : start,
					"range" : range,
			}
			
	        $.ajax({
	            url: 'rest/data/maximas',
	            type: 'POST',
	            contentType : 'application/json; charset=utf-8',
	            dataType : 'json',
	            data: JSON.stringify(averageList),
	            success: function(maximas) {		            	
	            	var labelPoints = [];
	            	for(var point = 0 ; point < averages.length ; point ++) {
	        			if(maximas.indexOf(point) > -1) {
	        				labelPoints[point] = 'MAX';
	        			}
	        			else {
	        				labelPoints[point] = null;
	        			}
	            	}
	            	
	            	
	            	plotGraphWithLabelPoints(averages, labelPoints);
	            },
	            error: function(xhr, status, error) {
	                var err = eval("(" + xhr.responseText + ")");
	                alert(err.Message);
	            }
	        });

			
			
		});

	}
	
	
	function assignClicksToFiltersAndMinimas() {			
		
		$('#peak_detection_menu_item_filterflat').css('opacity',"1");
		$('#peak_detection_menu_item_filterout').css('opacity',"1");
		$('#peak_detection_menu_item_minimas').css('opacity',"1");		
		$('#filter_flat_peaks_btn').css('cursor','pointer');
		$('#filter_outer_range_btn').css('cursor','pointer');
		$('#filter_outer_range_btn').css('cursor','pointer');
		$('#discover_minimas_btn').css('cursor','pointer');
		
		$('#extract_reps_btn').unbind('click'); 
		$('#extract_reps_btn').css('cursor','auto'); 
		$('#peak_detection_menu_item_reps').css('opacity',"0.2");
		
		
		$('#filter_flat_peaks_btn').click(function(e){ 
			
			//console.log()
			
		});
		$('#filter_outer_range_btn').click(function(e){ 
			
			
			
		});		
		$('#filter_outer_range_btn').click(function(e){ 
			
			
			
		});
		$('#discover_minimas_btn').click(function(e){ 
			
			$('#peak_detection_menu_item_reps').css('opacity',"1");
			$('#extract_reps_btn').css('cursor','pointer'); 
			$('#extract_reps_btn').click(function(e){ 
				
				

			});	
			
			
			
		});		
		
		
		
	}
	
	



  
}); // end ready 


